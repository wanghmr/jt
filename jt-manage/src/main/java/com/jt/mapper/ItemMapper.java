package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ItemMapper extends BaseMapper<Item> {

	List<Item> findAllByPage(@Param("startIndex") int startIndex, @Param("rows") Integer rows);



}
