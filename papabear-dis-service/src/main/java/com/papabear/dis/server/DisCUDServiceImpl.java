package com.papabear.dis.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.papabear.commons.entity.enumentity.Constant;
import com.papabear.commons.entity.enumentity.InvalidEnum;
import com.papabear.commons.utils.DateUtil;
import com.papabear.dis.api.DisCUDService;
import com.papabear.dis.dao.DisBaseDao;
import com.papabear.dis.dao.DisClaimDao;
import com.papabear.dis.dao.DisClaimLogDao;
import com.papabear.dis.dao.DisClaimSumDao;
import com.papabear.dis.entity.DisBase;
import com.papabear.dis.entity.DisClaim;
import com.papabear.dis.entity.DisClaimLog;
import com.papabear.dis.entity.DisClaimSum;
import com.papabear.dis.exception.ClaimException;
/**
 * 补贴卷相关数据存储操作
 * @author zhaolei
 * @date   2016年4月6日
 */
public class DisCUDServiceImpl implements DisCUDService {
	@Resource
	DisBaseDao disBaseDao;
	@Resource
	DisClaimDao disClaimDao;
	@Resource
	DisClaimLogDao disClaimLogDao;
	@Resource
	DisClaimSumDao disClaimSumDao;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveDisBase(DisBase base) {
		//插入disBase时，首先按照categoryModelId查找可用记录如果存在，则认为是修改，不存在则认为是新增操作
		if(base == null)
			throw new ClaimException("基础数据对象不能为空");
//		if(base.getCategoryModelId() == null)
//			throw new ClaimException("基础数据对象categoryModelId不能为空");
		//1.查询基础信息表按照category查找
		DisBase baseTemp = disBaseDao.getByCategoryModelId(base.getCategoryModelId());
		if(baseTemp == null){
			base.setCreateDateTime(new Date());
			base.setUpdateTime(new Date());
			base.setStatus(Constant.Status.NORMAL.getStatus());
			disBaseDao.insertSelective(base);
		}else{ 
			base.setId(baseTemp.getId());
			base.setUpdateTime(new Date());
			disBaseDao.updateByPrimaryKeySelective(base);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveDisClaim(DisClaim claim) {
		//领取优惠券是必须要填写手机号码,必须传categoryModelId,必须有分销人员ID存在
		if(claim == null)
			throw new ClaimException("补贴卷对象不能为空");
//		if(claim.getCategoryModelId() == null)
//			throw new ClaimException("补贴卷对象的categoryModelId不能为空");
		if(StringUtils.isBlank(claim.getPhoneNumber()))
			throw new ClaimException("补贴卷对象的PhoneNumber不能为空");
//		if(claim.getUserId() == null)
//			throw new ClaimException("补贴卷对象的UserId不能为空");
		//通过手机号查询领取的优惠券列表
		List<DisClaim> list = disClaimDao.queryListByPhone(claim.getPhoneNumber());
		if(list != null && list.size()>0){
			//说明是优惠券领用过
			Map<Long,DisBase> map = getBaseMap();
			if(map !=null && map.size()>0){
				DisClaimSum s = disClaimSumDao.getByPhone(claim.getPhoneNumber());
				s.setMoney(0f);//金额重新植入0
				//修改原有的补贴卷
				for (DisClaim disClaim : list) {
					DisBase base = map.get(disClaim.getCategoryModelId());
					if(base!=null){
						if(disClaim.getStatus().intValue()==2){
							//说明使用过执行插入操作
							disClaim.setId(null);
							disClaim.setCategoryModelId(base.getCategoryModelId());
							disClaim.setMoney(base.getDisMoney());
							disClaim.setSourceType((byte)0);//默认分销系统
							disClaim.setCreateDateTime(new Date());
							disClaim.setUpdateTime(new Date());
							disClaim.setStatus((byte)0);
							disClaim.setUserId(claim.getUserId());
							disClaim.setUseTime(DateUtil.addDate(base.getTimeCount(), (short)1));
							disClaimDao.insertSelective(disClaim);
							s.setMoney(s.getMoney()+base.getDisMoney());
						}else{
							//只发生修改
							disClaim.setUpdateTime(new Date());
							disClaim.setMoney(base.getDisMoney());
							disClaim.setUseTime(DateUtil.addDate(base.getTimeCount(), (short)1));
							disClaim.setStatus((byte)0);
							disClaimDao.updateByPrimaryKeySelective(disClaim);
							s.setMoney(s.getMoney()+base.getDisMoney());
						}
					}
				}
				//还有一种是后来新加的基础数据在这里需要执行新增操作
				List<DisBase> dataList = disBaseDao.queryAll();
				Map<Long,DisClaim> getClaimMap = getClaimMap(list);
				for (DisBase disBase : dataList) {
					if(!getClaimMap.containsKey(disBase.getCategoryModelId())){
						//说明没找到
						claim.setMoney(disBase.getDisMoney());
						claim.setSourceType((byte)0);//默认分销系统
						claim.setCreateDateTime(new Date());
						claim.setCategoryModelId(disBase.getCategoryModelId());
						claim.setUpdateTime(new Date());
						claim.setStatus((byte)0);
						//设置有效期
						claim.setUseTime(DateUtil.addDate(disBase.getTimeCount(), (short)1));
						disClaimDao.insertSelective(claim);
						s.setMoney(s.getMoney()+disBase.getDisMoney());
					}
				}
				disClaimSumDao.updateByPrimaryKeySelective(s);
			}
		}else{
			//新用户
			insertClaim(claim);
		}
		return 0;
	}
	private Map<Long,DisBase> getBaseMap(){
		Map<Long,DisBase> map = new HashMap<Long, DisBase>();
		List<DisBase> dataList = disBaseDao.queryAll();
		if(dataList != null && dataList.size()>0){
			for (DisBase disBase : dataList) {
				map.put(disBase.getCategoryModelId(), disBase);
			}
		}
		return map;
	}
	private Map<Long,DisClaim> getClaimMap(List<DisClaim> dataList){
		Map<Long,DisClaim> map = new HashMap<Long, DisClaim>();
		if(dataList != null && dataList.size()>0){
			for (DisClaim disBase : dataList) {
				map.put(disBase.getCategoryModelId(), disBase);
			}
		}
		return map;
	}
	/**
	 * 批量插入优惠券
	 * @param claim
	 */
	private void insertClaim(DisClaim claim){
		//查询基础信息列表
		List<DisBase> dataList = disBaseDao.queryAll();
		if(dataList != null && dataList.size()>0){
			DisClaimSum s = new DisClaimSum();
			s.setMoney(0f);
			for (DisBase disBase : dataList) {
				claim.setMoney(disBase.getDisMoney());
				claim.setSourceType((byte)0);//默认分销系统
				claim.setCreateDateTime(new Date());
				claim.setCategoryModelId(disBase.getCategoryModelId());
				claim.setUpdateTime(new Date());
				claim.setStatus((byte)0);
				//设置有效期
				claim.setUseTime(DateUtil.addDate(disBase.getTimeCount(), (short)1));
				disClaimDao.insertSelective(claim);
				s.setMoney(s.getMoney()+disBase.getDisMoney());
			}
			//设置初始化数据
			s.setCreateDateTime(new Date());
			s.setUpdateTime(new Date());
			s.setStatus(Constant.Status.NORMAL.getStatus());
			s.setPhoneNumber(claim.getPhoneNumber());
			disClaimSumDao.insertSelective(s);
		}
	}
	/**
	 * 检测存在的优惠券是否过期
	 * @param list
	 * @return
	 */
	private boolean checkDisClaim(List<DisClaim> list,Long modelId){
		boolean b = false;
		for (DisClaim disClaim : list) {
			//说明找到了相关的
			if(disClaim.getCategoryModelId().intValue() == modelId.intValue()){
				//1.判断是否过期
			}
		}
		return b;
	}
	@Override
	public void saveDisClaimLog(DisClaimLog claimLog) {
		disClaimLogDao.insertSelective(claimLog);
	}

	/* (non-Javadoc)
	 * @see com.papabear.dis.api.DisCUDService#cancelDisClaim(java.lang.Long)
	 */
	@Override
	public int cancelDisClaim(Long id) {
		DisClaim disClaim=  disClaimDao.selectByPrimaryKey(id);
		disClaim.setInvalid(InvalidEnum.TRUE.getInvalidValue());
		return disClaimDao.updateByPrimaryKeySelective(disClaim);
	}

	/* (non-Javadoc)
	 * @see com.papabear.dis.api.DisCUDService#complete(java.lang.Long)
	 */
	@Override
	public int complete(Long id) {
		DisClaim disClaim=  disClaimDao.selectByPrimaryKey(id);
		disClaim.setStatus((byte)2);
		return disClaimDao.updateByPrimaryKeySelective(disClaim);
	}
	
}
