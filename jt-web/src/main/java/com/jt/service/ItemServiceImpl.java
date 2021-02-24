package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.utils.ObjectMapperUtil;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public Item findItemById(Long id) {
		String url = "http://manage.jt.com/web/item/findItemById/"+id;
		String itemJSON = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(itemJSON, Item.class);
	}

	@Override
	public ItemDesc findItemDescById(Long id) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+id;
		String itemDescJSON = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(itemDescJSON, ItemDesc.class);
	}


}
