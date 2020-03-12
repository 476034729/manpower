package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 每月最后一天自动为员工增加年假天数
 */
public class AddLeaveDayRSTask {
    @Autowired
    private IAttendanceRecordService iAttendanceRecordService;

    private static Logger logger = LoggerFactory.getLogger(AsyncExecLampAndGZoneRSTask.class);
    public AddLeaveDayRSTask(){
        logger.info("============init system timer task:execute lamp and gzone relationship!============");
    }

    public void updateAnnualLeaveRecordAuto(){
        System.out.println("定时器执行了");
        iAttendanceRecordService.updateAnnualLeaveRecordAuto();
    }
}
