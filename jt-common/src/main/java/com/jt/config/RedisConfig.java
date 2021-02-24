package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;



@Configuration //表示配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;

	@Bean
	@Lazy
	public Jedis jedis() {
		return new Jedis(host, port);
	}
	
	/**
	 * Redis分片
	 */
	@Value("${redis.nodes}")
	private String nodes;
	
//	@Bean
//	public ShardedJedis shardedJedis() {
//		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//		String[] arrayNodes = nodes.split(",");
//		for (String node : arrayNodes) {
//			String host = node.split(":")[0];
//			int port = Integer.parseInt(node.split(":")[1]);
//			JedisShardInfo info = new JedisShardInfo(host, port);
//			shards.add(info);
//		}
//		return new ShardedJedis(shards);
//	}
	
	
	//定义池对象
//		@Bean
//		public JedisSentinelPool jedisSentinelPool() {
//			Set<String> sentinels = new HashSet<>();
//			sentinels.add(nodes);
//			return new JedisSentinelPool("mymaster", sentinels);
//		}
		
		/**
		 * @Bean:实例化对象时如果方法中添加了参数.首先会从spring容器中
		 * 	查找该参数.如果有对象则直接注入.
		 * @param pool
		 * @return
		 */
//		@Bean
//		@Scope("prototype")	//多例对象  用户使用时创建
//		public Jedis jedis(JedisSentinelPool pool) {
//	
//			return pool.getResource();
//		}

	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> setNodes = new HashSet<>(); 
		String[] arrayNode = nodes.split(",");
		//node{IP:PORT}
		for (String node : arrayNode) {
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			setNodes.add(new HostAndPort(host, port));
		}
		return new JedisCluster(setNodes);
	}

	
}
