package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解主要实现查询操作. 
 * 有缓存查询缓存,没缓存查询数据库
 * @author Administrator
 * 
 * 操作规范:
 * 	key: 
 * 		1.用户没有赋值
 * 			如果key为"",表示用户使用自动生成的key
 * 			key:包名.类名.方法名.拼接第一个参数
 * 		2.如果用户赋值
 * 			key:使用用户的数据
 *  seconds:
 *  	如果时间不为0,表示用户需要设定超时时间
 */

@Target({ElementType.METHOD}) //对方法生效
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache_Find {
	String key() default "";
	int seconds() default 0;
}
