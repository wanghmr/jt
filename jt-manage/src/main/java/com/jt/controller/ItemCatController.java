package com.jt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.Cache_Find;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	ItemCatService itemCatService;

	/**
	 * http://localhost:8091/item/cat/queryItemName
	 */
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {

		return itemCatService.findItemCatNameById(itemCatId);
	}

	/**
	 * Request URL: http://localhost:8091/item/cat/list
	 * 
	 * @RequestParam(name="id",defaultValue = "0",required = true) Long parentId)
	 *                                      设定参数: name/value: 接收参数名称
	 *                                      defaultValue:默认值 required: 是否为必填项.
	 * 
	 */
	@Cache_Find
	@RequestMapping("list")
	public List<EasyUITree> findItemCatTree(
			@RequestParam(name = "id", defaultValue = "0", required = true) Long parentId) {
		// 一级菜单
//		Long parentId = 0L;
		return itemCatService.findItemCatTree(parentId);
//		return itemCatService.findItemCatCache(parentId);
	}
}
