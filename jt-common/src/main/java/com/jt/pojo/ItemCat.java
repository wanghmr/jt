package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "tb_item_cat")
public class ItemCat extends BasePojo{
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Long id; // id号
	private Long parentId; // 父类id
	private String name; // 节点名称
	private Integer status; // 状态
	private Integer sortOrder; // 排序号
	private Boolean isParent; // 是否为父极

}
