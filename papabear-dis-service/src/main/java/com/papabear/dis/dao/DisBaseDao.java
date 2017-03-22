package com.papabear.dis.dao;

import java.util.List;

import com.papabear.dis.entity.DisBase;

public interface DisBaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(DisBase record);

    int insertSelective(DisBase record);

    DisBase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisBase record);

    int updateByPrimaryKey(DisBase record);
    
    DisBase getByCategoryModelId(Long categoryModelId);
    /**
     * 查询所有
     * @return
     */
    List<DisBase> queryAll();
}