package com.papabear.dis.entity;

import java.util.Date;

import com.papabear.commons.entity.AbstractEntity;

public class DisClaim extends AbstractEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3595150969023072285L;

    private String phoneNumber;

    private Long categoryModelId;

    private Long categoryModelFormatId;

    private Float money;

    private Long userId;

    private Byte sourceType;

    private Date useTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Long getCategoryModelId() {
        return categoryModelId;
    }

    public void setCategoryModelId(Long categoryModelId) {
        this.categoryModelId = categoryModelId;
    }

    public Long getCategoryModelFormatId() {
        return categoryModelFormatId;
    }

    public void setCategoryModelFormatId(Long categoryModelFormatId) {
        this.categoryModelFormatId = categoryModelFormatId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
}