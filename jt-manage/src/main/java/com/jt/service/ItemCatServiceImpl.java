package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	ItemCatMapper itemCatMapper;

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

}
