package com.papabear.dis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.papabear.dis.entity.DisClaim;

public interface DisClaimDao {
    int deleteByPrimaryKey(Long id);

    int insert(DisClaim record);

    int insertSelective(DisClaim record);

    DisClaim selectByPrimaryKey(Long id);
    
    DisClaim getDisClaim(@Param("id")Long id, @Param("phone")String phone);

    int updateByPrimaryKeySelective(DisClaim record);

    int updateByPrimaryKey(DisClaim record);
    
    List<DisClaim> queryListByPhone(String phoneNumber);
    
    DisClaim getClaimByPhoneAndCategoryModelId(@Param("phone")String phone, @Param("modelId")Long modelId);
}