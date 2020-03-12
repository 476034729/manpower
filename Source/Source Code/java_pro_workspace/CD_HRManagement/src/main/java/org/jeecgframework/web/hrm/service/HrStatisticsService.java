package org.jeecgframework.web.hrm.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface HrStatisticsService extends CommonService {
    List<ProjectCode> getProjectList();

    List<TSUser> getUserByWorkHour(int year,int month);

//    Map<String,Object> getTimeOfCode(int year , int month ,String id);

//    Map<String,Object> getRateOfAttendance(int year , int month ,String id);

    Map<String,Object> getCodePeopleTimes(int year, int month);

    Map<String,Object> getProjectPeopleDayRate(int year , int month);

    boolean hasRecord(int year, int month);

    HSSFWorkbook getHSSFWorkbook(String sheetName,List<String> projectList,List<String> rateList);

    Map<String,Map<String,Object>> getTimeOfCode(int year, int month,List<TSUser> users);


}
