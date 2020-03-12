package org.jeecgframework.web.hrm.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.CardingRecord;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.service.CardingRecordQueryService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("CardingRecordQueryService")
@Transactional
public class CardingRecordQueryServiceImpl extends CommonServiceImpl implements CardingRecordQueryService {


	@Override
	public List<Object[]> getCardingRecordByIDAndTime(Timestamp begTime, Timestamp endTime, String account_name) {


		List<HrmDate> timeList = getBetweenDates((Date)begTime, (Date)endTime);
		if(timeList.size()<1){
			return null;
		}
		String hql1="from TSUser u where u.userName=? order by u.jobNum";
		List<TSUser> userlist = this.findHql(hql1, account_name);
		if(userlist.size()<1){
			return null;
		}
		String hql="";
		JSONArray jsArr=new JSONArray();
		for (HrmDate hrmDate : timeList) {
			JSONObject obj=new JSONObject();
			
			Timestamp work_date_end = getendTime(hrmDate.getWork_date());
			hql="FROM CardingRecord where T_ACCOUNT=? and T_TIME between ? and ? ";
			List<CardingRecord> CardingRecords = this.findHql(hql, userlist.get(0).getJobNum(),hrmDate.getWork_date(),work_date_end);
			obj.put("worktime", hrmDate.getWork_date());
			obj.put("cardingreport",CardingRecords);
			jsArr.add(obj);
		}
		return jsArr;
	}
	
	
	/**
	 * 返回当天时间的 23:59:59
	 * @param date
	 * @return
	 */
	private Timestamp getendTime(Date date){
		Long time=date.getTime()+23*60*60*1000+59*60*1000+59*1000;
		Timestamp timestamp=new Timestamp(time);
		return timestamp;
	}
	
	/**
	 * 返回两个时间的每一天
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<HrmDate> getBetweenDates(Date startTime, Date endTime) {
	    
		String hql = "from HrmDate u where 1=1 and u.work_date between ? and ? order by u.work_date asc";
	    List<HrmDate> datelist = this.findHql(hql,startTime,endTime);
    
	    return datelist;
	}


}
