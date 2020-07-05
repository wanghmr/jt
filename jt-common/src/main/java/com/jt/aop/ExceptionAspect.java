package com.jt.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAspect{

	@ExceptionHandler(RuntimeException.class)
	public  SysResult result(Exception exception) {
		exception.printStackTrace(); //为程序员调错方便准备.
		log.error("异常信息:", exception);
		return SysResult.fail();
	}
}
