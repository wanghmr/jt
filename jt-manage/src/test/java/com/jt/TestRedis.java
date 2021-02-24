package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;



/**
 * 测试类，
 * 
 * @author 15583
 *
 */
public class TestRedis {
	@Test
	public void testRedis() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		// 1.向redis中保存数据
//	       jedis.set("1905", "学习redis");
		jedis.setex("1905", 20, "学习redis");
		// 2.从redis中获取数据
		String value = jedis.get("2004");
		System.out.println(value);

	}

	@Test
	public void test02() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		// 1.判断redis中是否存在key
		if (jedis.exists("2004")) {
			// 2.如果存在则设定超时时间
			jedis.expire("2004", 100);
			// 3.线程暂停2秒
			try {
				Thread.sleep(2000);
				// 4.获取剩余的存活时间
				Long time = jedis.ttl("2004");
				System.out.println("还能活:" + time);
				// 5.撤销删除操作
				jedis.persist("2004");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 需求说明: 1.redis.set操作,后面的操作会将之前的value覆盖 要求:如果key已经存在,则不允许赋值. 注意环境:
	 * 检查redis中是否有改数据.
	 */
	@Test
	public void test03() {
		// A 潘石屹 B 马云
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		jedis.flushAll(); // 清空redis缓存
		// 如果key存在则不执行赋值操作.
		jedis.setnx("boss", "潘石屹");
		jedis.setnx("boss", "马云");
		System.out.println(jedis.get("boss"));
	}

	/**
	 * 为数据添加超时时间.保证原子性操作 原子性: 一起完成一起回滚 锁机制: 保证原子性 死锁!!!! 小结: setnx 如果key存在则不赋值 setex
	 * 保证原子性操作,并且添加超时时间. *
	 */
	@Test
	public void test04() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		jedis.setex("aaa", 20, "xxxxx"); // 满足原子性需求.
	}

	/**
	 * 需求: 1.要求赋值操作时,如果数据已经存在则不允许赋值. 2.同时要求添加超时时间 并且满足原子性要求. private static final
	 * String XX = "xx"; 只有key存在时,才能赋值 private static final String NX = "nx";
	 * 只有key不存在时,赋值 private static final String PX = "px"; 毫秒 private static final
	 * String EX = "ex"; 秒
	 */
//	   @Test
//	   public void test05() {
//	       Jedis jedis = new Jedis("192.168.88.129", 6379);
//	       SetParams setParams = new SetParams();
//	       setParams.nx().ex(20);
//	       jedis.set("AAA", "CCCCC", setParams); //原子性要求
//	       System.out.println(jedis.get("AAA"));
//	  }

	/* 测试hash数据类型 */
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		jedis.hset("person", "name", "tomcat");
		jedis.hset("person", "age", "100");
		Map<String, String> map = jedis.hgetAll("person");
		System.out.println(map);
	}

	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		jedis.lpush("list", "1", "2", "3", "4");
		String value = jedis.rpop("list");
		System.out.println(value);
	}

	/**
	 * 实现Redis 事务控制
	 */
	@Test
	public void testMulti() {
		Jedis jedis = new Jedis("192.168.88.129", 6379);
		Transaction transaction = jedis.multi();
		try {
			transaction.set("AAA", "XXX");
			transaction.set("BBB", "XXX");
			transaction.exec(); // 提交事务
		} catch (Exception exception) {
			exception.printStackTrace();
			transaction.discard();// 回滚事务
		}

	}

	/**
	 *	 实现redis分片测试 6379/6380/6381
	 */
	@Test
	public void testShards() {
		String host = "192.168.88.129";
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host, 6379));
		shards.add(new JedisShardInfo(host, 6380));
		shards.add(new JedisShardInfo(host, 6381));
		ShardedJedis  jedis = new ShardedJedis(shards);
		jedis.set("1905", "分片学习,为集群做准备!!!");
		System.out.println(jedis.get("1905"));
	}
	
	/**
	 * 	哨兵测试
	 */
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.88.129:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1905", "哨兵搭建完成!!!!!");
		System.out.println(jedis.get("1905"));

	}
	
	/**
	 * 	redis集群
	 */
	@Test
	public void testCluster() {
		String host = "192.168.88.129";
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host,7000));
		nodes.add(new HostAndPort(host,7001));
		nodes.add(new HostAndPort(host,7002));
		nodes.add(new HostAndPort(host,7003));
		nodes.add(new HostAndPort(host,7004));
		nodes.add(new HostAndPort(host,7005));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("1905", "你好redis集群");
		System.out.println(jedisCluster.get("1905"));
	}


}
