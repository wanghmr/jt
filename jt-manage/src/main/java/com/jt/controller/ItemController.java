package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;


@RestController // 为ajax返回数据,不能跳转页面
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Value("${server.port}")
	private String port;

	/**
	 * 查询 http://localhost:8091/item/query?page=1&rows=20
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		return itemService.findItem(page, rows);
	}

	/**
	 * 新增 http://localhost:8091/item/save
	 */
	@RequestMapping("/save")
	public SysResult save(Item item, ItemDesc itemDesc) {
		itemService.save(item, itemDesc);
		return SysResult.success("新增成功", null);
	}

	/**
	 * 编辑 http://localhost:8091/item/update
	 */
	@RequestMapping("/update")
	public SysResult update(Item item, ItemDesc itemDesc) {
		itemService.updateitem(item, itemDesc);
		return SysResult.success();
	}

	/**
	 * 删除 http://localhost:8091/item/delete 删除Item与ItemDesc
	 */
	@RequestMapping("/delete")
	public SysResult delete(Long[] ids) {
//		System.out.println("-----------"+ids);
		itemService.delete(ids);
		return SysResult.success();
	}

	/**
	 * 上架 http://localhost:8091/item/reshelf
	 */
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
//		System.out.println("-----------"+ids);
		int states = 1;
		itemService.updateItemStatus(states, ids);
		return SysResult.success();
	}

	/**
	 * 下架 http://localhost:8091/item/instock
	 */
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		int status = 2;
		itemService.updateItemStatus(status, ids);
		return SysResult.success();
	}

	/**
	 * 目的:展现商品详情信息 url: /item/query/item/desc/'+data.id
	 * http://localhost:8091/item/query/item/desc/1474391972
	 * 
	 * 参数: 感觉不用参数 Id信息 业务操作: 根据商品Id信息查询商品详情信息 返回值数据: 检查js中的回调
	 */

	@RequestMapping("/query/item/desc/{id}")
	public SysResult findItemDescById(@PathVariable Long id) {

		ItemDesc itemDesc = itemService.findItemDescById(id);
		return SysResult.success(itemDesc);
		// Sysresult.data=itemDesc数据
	}
	
	@RequestMapping("/getMsg")
	public String getMsg() {	
		
		return "我是"+port+"项目";
	}

}
