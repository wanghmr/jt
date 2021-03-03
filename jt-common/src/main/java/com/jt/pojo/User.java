package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Data
@Accessors(chain = true)
public class User extends BasePojo{
	private static final long serialVersionUID = -2015513995087701772L;
	private Long id;
	private String username;
	private String password; //加密保存 md5
	private String phone;
	private String email;

}
