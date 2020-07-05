package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	/**
	 * 状态码 status 200 正常 201表示失败 信息 msg 服务器传给页面的信息 返回数据 data 服务器回传页面的数据
	 */
	private Integer status;
	private String msg;
	private Object data;

	public static SysResult success() {

		return new SysResult(200, "服务器执行成功!!!", null);
	}

	public static SysResult success(Object data) {

		return new SysResult(200, "服务器执行成功!!!", data);
	}

	public static SysResult success(String msg, Object data) {

		return new SysResult(200, msg, data);
	}

	public static SysResult fail() {

		return new SysResult(201, "服务器处理失败", null);
	}

	public static SysResult fail(String msg) {

		return new SysResult(201, msg, null);
	}

}
