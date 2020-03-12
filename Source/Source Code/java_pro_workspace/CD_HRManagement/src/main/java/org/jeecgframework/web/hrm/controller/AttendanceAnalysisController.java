package org.jeecgframework.web.hrm.controller;

import java.sql.Timestamp;

import org.apache.poi.ss.formula.functions.T;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.service.AttendanceAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

/**
 * 考勤分析
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/AttendanceAnalysis")
public class AttendanceAnalysisController {
	
	@Autowired
	private AttendanceAnalysisService attendanceAnalysisService;
	
	
	@RequestMapping(params="attendanceAnalysis",method = RequestMethod.GET)
	public ModelAndView AttendanceAnalysis(){
		return new ModelAndView("hrm/attendance/attendanceAnalysis");	
	}
	
	@ResponseBody
	@RequestMapping(params="getAttendanceAnalysis",method = RequestMethod.GET)
	public AjaxJson getAttendanceAnalysis(String begtime,String endtime){
		Timestamp begTime=Timestamp.valueOf(begtime);
        Timestamp endTime=Timestamp.valueOf(endtime);
		
		JSONObject jsobj=new JSONObject();
		jsobj.put("attendanceAnalysis", attendanceAnalysisService.getAttendanceAnalysis(begTime,endTime));
		return new AjaxJson(true, "考勤分析数据", jsobj);
		
	}
	
	@ResponseBody
	@RequestMapping(params="getAttendanceAnalysisTimes",method = RequestMethod.GET)
	public AjaxJson getAttendanceAnalysisTimes(String begtime,String endtime){
		Timestamp begTime=Timestamp.valueOf(begtime);
        Timestamp endTime=Timestamp.valueOf(endtime);
		
		JSONObject jsobj=new JSONObject();
		jsobj.put("attendanceAnalysisTimes", attendanceAnalysisService.getBetweenDates(begTime, endTime));
		return new AjaxJson(true, "时间数据", jsobj);
		
	}
	
}
