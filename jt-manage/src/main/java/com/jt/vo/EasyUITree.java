package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
	// list[{id,text,state},{},{}]
	private Long id; // 节点Id值
	private String text; // 节点名称
	private String state; // 状态 open/closed

	// 如果包含子节点信息,后期维护.

}
