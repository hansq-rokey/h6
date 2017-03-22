package com.papabear.dis.api;

import java.util.List;

import com.papabear.dis.entity.DisBase;
import com.papabear.dis.entity.DisClaim;
import com.papabear.dis.entity.DisClaimLog;
import com.papabear.dis.entity.DisClaimSum;

public interface DisQueryService {
	/**
	 * 返回补贴卷，按照手机号码，产品Id返回
	 * @return
	 */
	DisClaim getClaimByPhoneAndCategoryModelId(String phone,Long modelId);
	/**
	 * 返回优惠券
	 * @author yaoweiguo
	 * @date 2016年5月9日
	 * @param id		流水号
	 * @param phone		用户手机号
	 * @return
	 */
	DisClaim getDisClaim(Long id,String phone);
	/**
	 * 返回补贴卷列表
	 */
	List<DisClaim> queryListClaimByPhone(String phone);
	/**
	 * 根据手机号查询补贴卷总金额记录
	 * @param phone
	 * @return
	 */
	DisClaimSum getClaimSumByPhone(String phone);
	/**
	 * 通过model查询基础设置信息
	 * @param categoryModelId
	 * @return
	 */
	DisBase getBaseByModelId(Long categoryModelId);
	
	/**
	 * 查询所有产品优惠券
	 * @author yaoweiguo
	 * @date 2016年4月25日
	 * @return
	 */
	List<DisBase> queryDisBaseList();
	/**
	 * 查询是否使用优惠券
	 * @author yaoweiguo
	 * @date 2016年5月9日
	 * @param orderNumber 订单编号
	 * @return
	 */
	List<DisClaimLog > queryDisClaimLogs(String orderNumber);
}
