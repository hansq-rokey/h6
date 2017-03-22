package com.papabear.dis.api;

import com.papabear.dis.entity.DisBase;
import com.papabear.dis.entity.DisClaim;
import com.papabear.dis.entity.DisClaimLog;

public interface DisCUDService {
	/**
	 * 保存基础信息表数据，支持添加更新 ，本方法现在支持的是categoryModelId为唯一键
	 * @param base
	 */
	void saveDisBase(DisBase base);
	/**
	 * 保存领取优惠券
	 * @param claim
	 * @return 0：执行成功
	 */
	int saveDisClaim(DisClaim claim);
	/**
	 * 保存优惠券使用记录，支付成功之后调用
	 * @param claimLog
	 */
	void saveDisClaimLog(DisClaimLog claimLog);
	
	/**
	 * 取消使用优惠券
	 * @author yaoweiguo
	 * @date 2016年5月9日
	 * @param id
	 * @return
	 */
	int cancelDisClaim(Long id);
	
	/**
	 * 订单完成
	 * @author yaoweiguo
	 * @date 2016年5月9日
	 * @param id
	 * @return
	 */
	int complete(Long id);
}
