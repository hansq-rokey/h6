package com.papabear.dis.dao;

import java.util.List;

import com.papabear.dis.entity.DisClaimLog;

public interface DisClaimLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(DisClaimLog record);

    int insertSelective(DisClaimLog record);

    DisClaimLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisClaimLog record);

    int updateByPrimaryKey(DisClaimLog record);
    
    List<DisClaimLog> queryDisClaimLogs(String orderNumber);
}