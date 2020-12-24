package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.utils.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private Jedis jedis;


	@Override
	public String findItemCatNameById(Long id) {
		ItemCat itemCat = itemCatMapper.selectById(id);
		return itemCat.getName();
	}

	@Override
	public List<EasyUITree> findItemCatTree(Long parentId) {
		// 1.获取所有商品类别
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
		// 2.转化为List<EasyUITree>
		List<EasyUITree> list = new ArrayList<EasyUITree>();
		for (ItemCat itemCat : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			String state=itemCat.getIsParent()?"closed":"open";
			easyUITree.setId(itemCat.getId())
					  .setText(itemCat.getName())
					  .setState(state);
			list.add(easyUITree);
		}

		return list;
	}

	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		List<EasyUITree> treeList = new ArrayList<>();
		
		//1.根据key查询缓存
		String key = "ITEM_CAT::"+parentId;
		String result = jedis.get(key);
		
		//2.检查是否有数据
		if(StringUtils.isEmpty(result)) {
			//缓存中没有数据,查询数据库.
			treeList = findItemCatTree(parentId);
			//将数据转化为JSON串
			String dataJSON = ObjectMapperUtil.toJSON(treeList);
			//将数据保存到redis中
			jedis.set(key, dataJSON);
			System.out.println("第一次查询数据库");
		}else {
			//3.表示缓存中有数据的
			treeList = ObjectMapperUtil.toObject(result,treeList.getClass());
			System.out.println("查询缓存数据!!!!!");
		}
		
		return treeList;

	}
	
	
	


}
