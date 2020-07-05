package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 管理端程序入口
 * http://localhost:8091
 * @author 15583
 *
 */
@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class, args);
	}
}
