package org.jeecgframework.web.hrm.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

@Service
@Transactional
public interface AttendanceAnalysisService extends CommonService{

	public JSONArray getAttendanceAnalysis(Timestamp begTime, Timestamp endTime);

	public List<HrmDate> getBetweenDates(Date begTime, Date endTime);

}
