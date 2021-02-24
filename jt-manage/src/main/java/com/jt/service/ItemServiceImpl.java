package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	/**
	 * page 当前页 rows 每页的条数
	 */
	@Override
	public EasyUITable findItem(Integer page, Integer rows) {
		// 1.查询总行数
		int total = itemMapper.selectCount(null);
		// 1.1当前页起始位置
		int startIndex = (page - 1) * rows;
		// 2.查询商品列表
		List<Item> itemlist = itemMapper.findAllByPage(startIndex, rows);
		return new EasyUITable(total, itemlist);
	}

	@Override
	public void save(Item item, ItemDesc itemDesc) {
		item.setStatus(1);// 上架
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		// Mybatis-plus在新增数据时,会自动回传主键信息.
		// 保证主键有值.
		// 入库商品描述信息
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateitem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//编辑temDesc
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
		System.out.println("-------------"+itemDesc.getItemDesc());
	}

	@Override
	public void delete(Long[] ids) {
		// 数组转化为集合
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		//删除itemDesc
		itemDescMapper.deleteBatchIds(idList);
	}

	@Override
	public void updateItemStatus(Integer status, Long[] ids) {
		for (Long l : ids) {
			Item item = new Item();
			item.setId(l);
			item.setStatus(status);
			item.setUpdated(new Date());
			itemMapper.updateById(item);
		}
	}

	@Override
	public ItemDesc findItemDescById(Long id) {

		return itemDescMapper.selectById(id);
	}
	

	@Override
	public Item findItemById(Long id) {
		
		return itemMapper.selectById(id);
	}


}
