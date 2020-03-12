package org.jeecgframework.web.hrm.service;


import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.bean.WorkHourCM;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public interface WorkHourService extends CommonService {
    List<WorkHour> getWorkHourList(String user_id, int year, int month);

    List<WorkHourCM> getCMWorkHourList(String user_id, int year, int month);

    void updateWorkHour(String id, String code, String text);

    //测试未通过
    void updateWorkHour(List<String> idList,List<String> codeList, List<String> textList,String user_id);

    void updateWorkHourCM(String id, String code, String text);

    Set<TSUser> getUserList(String department, int year, int month);

    boolean haveRecord(String user_id, int year, int month);

    TSUser getUserById(String id);

    List<HrmDate> getTimeList(int year , int month);

    /**
     * 定时每月为员工增加工时记录
     */
    void insertAllWorkHour();

    /**
     * 为新增员工添加工时信息
     * @param user_id
     */
    void insertNewEmpWorkHour(String user_id);


	Map<String, List<WorkHour>> getCMCheckWorkHourList(List<Integer> statusList, List<String> projectList,
			List<String> idList, int year, int month);

	void insertWorkHourListAuto();

}
