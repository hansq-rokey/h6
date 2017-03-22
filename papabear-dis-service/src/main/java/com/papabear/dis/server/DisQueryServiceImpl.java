package com.papabear.dis.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.papabear.commons.utils.DateUtil;
import com.papabear.dis.api.DisQueryService;
import com.papabear.dis.dao.DisBaseDao;
import com.papabear.dis.dao.DisClaimDao;
import com.papabear.dis.dao.DisClaimLogDao;
import com.papabear.dis.dao.DisClaimSumDao;
import com.papabear.dis.entity.DisBase;
import com.papabear.dis.entity.DisClaim;
import com.papabear.dis.entity.DisClaimLog;
import com.papabear.dis.entity.DisClaimSum;
/**
 * 补贴卷相关数据查询操作
 * @author zhaolei
 * @date   2016年4月6日
 */
public class DisQueryServiceImpl implements DisQueryService {
	@Resource
	DisBaseDao disBaseDao;
	@Resource
	DisClaimDao disClaimDao;
	@Resource
	DisClaimLogDao disClaimLogDao;
	@Resource
	DisClaimSumDao disClaimSumDao;
	@Override
	public DisClaim getClaimByPhoneAndCategoryModelId(String phone, Long modelId) {
		DisClaim disClaim = disClaimDao.getClaimByPhoneAndCategoryModelId(phone, modelId);
		if(disClaim != null){
			if(!DateUtil.compareDate(disClaim.getUseTime(),new Date())){
				//如果有效期大于当前时间说明没过期，反之说明过期了
				disClaim.setStatus((byte)1);
				disClaimDao.updateByPrimaryKeySelective(disClaim);
			}
		}
		return disClaim;
	}

	@Override
	public List<DisClaim> queryListClaimByPhone(String phone) {
		List<DisClaim> list = disClaimDao.queryListByPhone(phone);
		List<DisClaim> listn = new ArrayList<DisClaim>();
		if(list != null){
			for (DisClaim disClaim : list) {
				//在这里有一步操作判断是否过期了，过期植入状态
				if(!DateUtil.compareDate(disClaim.getUseTime(),new Date())){
					//如果有效期大于当前时间说明没过期，反之说明过期了
					disClaim.setStatus((byte)1);
					disClaimDao.updateByPrimaryKeySelective(disClaim);
				}
				listn.add(disClaim);
			}
		}
		return listn;
	}
	@Override
	public DisClaimSum getClaimSumByPhone(String phone) {
		return disClaimSumDao.getByPhone(phone);
	}
	
	@Override
	public DisBase getBaseByModelId(Long categoryModelId) {
		return disBaseDao.getByCategoryModelId(categoryModelId);
	}

	/* (non-Javadoc)
	 * @see com.papabear.dis.api.DisQueryService#queryDisBaseList()
	 */
	@Override
	public List<DisBase> queryDisBaseList() {
		
		return disBaseDao.queryAll();
	}

	/* (non-Javadoc)
	 * @see com.papabear.dis.api.DisQueryService#getDisClaim(java.lang.Long, java.lang.Long)
	 */
	@Override
	public DisClaim getDisClaim(Long id, String phone) {
		DisClaim disClaim = disClaimDao.getDisClaim(id, phone);
		if(disClaim != null){
			if(!DateUtil.compareDate(disClaim.getUseTime(),new Date())){
				//如果有效期大于当前时间说明没过期，反之说明过期了
				disClaim.setStatus((byte)1);
				disClaimDao.updateByPrimaryKeySelective(disClaim);
			}
		}
		return disClaim;
	}

	/* (non-Javadoc)
	 * @see com.papabear.dis.api.DisQueryService#queryDisClaimLogs(java.lang.String)
	 */
	@Override
	public List<DisClaimLog> queryDisClaimLogs(String orderNumber) {
		
		return disClaimLogDao.queryDisClaimLogs(orderNumber);
	}
	
}
