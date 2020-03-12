package org.jeecgframework.web.hrm.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.CardingRecord;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.service.AttendanceAnalysisService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("AttendanceAnalysisService")
@Transactional
public class AttendanceAnalysisServiceImpl extends CommonServiceImpl implements AttendanceAnalysisService {

	private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(AttendanceAnalysisServiceImpl.class);


	@Override
	public JSONArray getAttendanceAnalysis(Timestamp begTime, Timestamp endTime) {


		boolean flag=false;
		int size=0;
		List<HrmDate> timeList = getBetweenDates((Date)begTime, (Date)endTime);
		if(timeList.size()<1){
			return null;
		}
		List<TSUser> userList = this.commonDao.findByQueryString("from TSUser u order by u.jobNum");
		String hql="FROM CardingRecord where T_TIME between ? and ? ";
		List<CardingRecord> CardingRecords = this.findHql(hql,timeList.get(0).getWork_date(),timeList.get(timeList.size()-1).getWork_date());
		List<CardingRecord> cardingRecords =null;
		JSONArray dtArry=new JSONArray();
		//匹配员工
		for (TSUser users : userList) {
			
			//匹配时间
			for (HrmDate times : timeList) {
				cardingRecords =new ArrayList<CardingRecord>();
				for (CardingRecord cardingRecord : CardingRecords) {
					//System.out.println(users.getJobNum()+" -->  "+cardingRecord.gettAccount());
					if(cardingRecord.gettAccount().equals(users.getJobNum())){
						if(times.getWork_date().getTime() < cardingRecord.gettTime().getTime() && cardingRecord.gettTime().getTime() < getendTime(times.getWork_date()).getTime()){
							cardingRecords.add(cardingRecord);
						}
					}
				}
				JSONObject jsobj=new JSONObject();
				if(cardingRecords.size()<1){
					//没有打卡记录
					jsobj.put("creatTime", times);
					jsobj.put("jobNum", users.getJobNum());
					jsobj.put("analysis", "暂无打卡");
					jsobj.put("status", 0);//0状态 表示没有打卡记录
					
				}else if(cardingRecords.size()==1){
					//一次打卡记录
					jsobj.put("creatTime", times);
					jsobj.put("jobNum", users.getJobNum());
					jsobj.put("analysis", "一次打卡记录");
					jsobj.put("status", 1);//1状态 表示只有一次打卡记录
				}else{
					//大于一次打卡记录
					if(getTime(cardingRecords.get(0).gettTime(), cardingRecords.get(cardingRecords.size()-1).gettTime())<9){
						jsobj.put("creatTime", times);
						jsobj.put("jobNum", users.getJobNum());
						jsobj.put("analysis", getTimeDifference(cardingRecords.get(0).gettTime(), cardingRecords.get(cardingRecords.size()-1).gettTime()));
						jsobj.put("status", 2);//2状态 打卡记录完整--工时不足九小时
					}else{
						
						jsobj.put("creatTime", times);
						jsobj.put("jobNum", users.getJobNum());
						jsobj.put("analysis", getTimeDifference(cardingRecords.get(0).gettTime(), cardingRecords.get(cardingRecords.size()-1).gettTime()));
						jsobj.put("status", 3);//3状态 打卡记录完整--工时满足九小时
					}
					flag=true;
				}
				dtArry.add(jsobj);
			}
			//统计打卡人数
			if(flag==true){
				size++;
				flag=false;
			}
		}	
		
		JSONObject sizeobj=new JSONObject();
		sizeobj.put("size", size);
		dtArry.add(sizeobj);
		return dtArry;
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
	@Override
	public List<HrmDate> getBetweenDates(Date startTime, Date endTime) {
		String hql = "from HrmDate u where 1=1 and u.work_date between ? and ? order by u.work_date asc";
	    List<HrmDate> datelist = this.findHql(hql,startTime,endTime);
    
	    return datelist;
	}
	
	/**
	 * 
	 * 返回两个时间的差值
	 * @param begtime
	 * @param endtime
	 * @return
	 */
	private String getTimeDifference(Date begtime,Date endtime){
		
		long tempt=endtime.getTime()-begtime.getTime();
		long hour=tempt/(60*60*1000);		
		long min=tempt/(60*1000)-hour*60;
		long s=tempt/1000-hour*60*60-min*60;
		return hour+" 小时 "+min+" 分钟 "+s+" 秒";		
	}
	
	private long getTime(Date begtime, Date endtime) {

		long tempt = endtime.getTime() - begtime.getTime();
		long hour = tempt / (60 * 60 * 1000);
		return hour;
	}
}
