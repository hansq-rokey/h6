package com.papabear.dis.entity;

import com.papabear.commons.entity.AbstractEntity;

public class DisBase extends AbstractEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9175916918018150917L;

	private Long categoryModelId;

    private Long categoryModelFormatId;

    private Float disMoney;

    private Float profitMoney;

    private Integer timeCount;


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

    public Float getDisMoney() {
        return disMoney;
    }

    public void setDisMoney(Float disMoney) {
        this.disMoney = disMoney;
    }

    public Float getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(Float profitMoney) {
        this.profitMoney = profitMoney;
    }

    public Integer getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(Integer timeCount) {
        this.timeCount = timeCount;
    }
   
}