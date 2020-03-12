package org.jeecgframework.web.hrm.controller;


import java.sql.Timestamp;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.service.CardingRecordQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.Logger;
import jodd.servlet.URLDecoder;
import net.sf.json.JSONObject;

/**
 * 考勤记录查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/CardingRecord")
public class CardingRecordQueryController {
	
	@Autowired
	private CardingRecordQueryService cardingRecord;	
	
	/**
	 * 返回对应页面
	 * @return
	 */
	@RequestMapping(params="getCardingRecordPage",method = RequestMethod.GET)
	public ModelAndView getCardingRecordPage(){
		return new ModelAndView("hrm/attendance/cardingReport");
	}
	
//	@ResponseBody
//	@RequestMapping(params="getCardingRecord",method = RequestMethod.GET)
//	public AjaxJson getCardingRecord(int year,int month,String account_name){
//		account_name=URLDecoder.decode(account_name,"UTF-8");
//		System.out.println(year+"  "+ month+ account_name);
//		JSONObject jsonobj=new JSONObject();
//		jsonobj.put("CardingReccord", cardingRecord.getCardingRecordByID(year,month,account_name));
//		return new AjaxJson(true, "返回数据", jsonobj);
//	}
	
	/**
	 * 返回打卡记录查询数据
	 * @param begtime
	 * @param endtime
	 * @param account_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params="getCardingRecordByTime",method = RequestMethod.GET)
	public AjaxJson getCardingRecordByTime(String begtime,String endtime,String account_name){
		try {
			account_name=URLDecoder.decode(account_name,"UTF-8");
			Timestamp begTime=Timestamp.valueOf(begtime);
	        Timestamp endTime=Timestamp.valueOf(endtime);
//			System.out.println(begTime+"  "+ endTime+" " + account_name);
			JSONObject jsonobj=new JSONObject();
			jsonobj.put("CardingReccord", cardingRecord.getCardingRecordByIDAndTime(begTime,endTime,account_name));
			return new AjaxJson(true, "返回数据", jsonobj);
		} catch (Exception e) {
			return new AjaxJson(false, "数据异常", null);
		}
		
	}
	
}
