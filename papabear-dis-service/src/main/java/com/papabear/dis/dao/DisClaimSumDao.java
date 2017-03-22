package com.papabear.dis.dao;

import com.papabear.dis.entity.DisClaimSum;

public interface DisClaimSumDao {
    int deleteByPrimaryKey(Long id);

    int insert(DisClaimSum record);

    int insertSelective(DisClaimSum record);

    DisClaimSum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisClaimSum record);

    int updateByPrimaryKey(DisClaimSum record);
    
    DisClaimSum getByPhone(String phoneNumber);
}