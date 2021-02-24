package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItem(Integer page, Integer rows);

	void save(Item item, ItemDesc itemDesc);

	void updateitem(Item item, ItemDesc itemDesc);

	void delete(Long[] ids);

	void updateItemStatus(Integer status, Long[] ids);

	ItemDesc findItemDescById(Long id);

	Item findItemById(Long id);
	

}
