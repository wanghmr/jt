package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.override.PageMapperMethod.MethodSignature;
import com.jt.anno.Cache_Find;
import com.jt.utils.ObjectMapperUtil;
import redis.clients.jedis.Jedis;

@Aspect //标识切面
@Component //交给spring容器管理
public class CacheAspect {

    @Autowired
    private Jedis jedis;

    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {
        Object data = null;
        String key = getKey(joinPoint, cacheFind);
        //1.从redis中获取数据
        String result = jedis.get(key);
        //2.判断缓存中是否有数据
        try {
            if (StringUtils.isEmpty(result)) {
                //2.1缓存中没有数据
                // 目标方法执行
                data = joinPoint.proceed();
                //2.2将返回值结果,转化为JSON
                String json = ObjectMapperUtil.toJSON(data);
                //2.3判断用户是否编辑时间
                //如果有时间,必须设定超时时间.
                if (cacheFind.seconds() > 0) {
                    int seconds = cacheFind.seconds();
                    jedis.setex(key, seconds, json);
                } else {
                    jedis.set(key, json);
                }

                System.out.println("AOP查询数据库!!!!!");
            } else {
                //表示缓存数据不为空,将缓存数据转化为对象
                Class returnClass = getReturnClass(joinPoint);
                data = ObjectMapperUtil.toObject(result, returnClass);
                System.out.println("AOP查询缓存!!!!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return data;
    }

    private Class getReturnClass(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }

    /**
     * 动态获取key
     *
     * @param joinPoint 连接点
     * @param cacheFind 自定义注解
     * @return Redis的key
     */
    private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {
        String key = cacheFind.key();
        if (StringUtils.isEmpty(key)) {
            //key自动生成
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            if (joinPoint.getArgs().length > 0) {
                //拼接第一个参数
                key = className + "." + methodName + "::" + joinPoint.getArgs()[0];
            } else {
                key = className + "." + methodName;
            }
        }
        return key;
    }
}
