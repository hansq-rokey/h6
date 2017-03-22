package com.papabear.dis.entity;

import com.papabear.commons.entity.AbstractEntity;

public class DisClaimSum extends AbstractEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4808970678578794918L;

	private String phoneNumber;

    private Float money;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

}