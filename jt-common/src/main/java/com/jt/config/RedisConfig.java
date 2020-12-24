package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;

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
}
