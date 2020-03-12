package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每年1号初始化该年所有员工得月份加班信息
 */
@Component
public class MonthOvertimeRSTask {
    @Autowired
    private IAttendanceRecordService iAttendanceRecordService;
    private static Logger logger = LoggerFactory.getLogger(AsyncExecLampAndGZoneRSTask.class);

    public MonthOvertimeRSTask(){
        logger.info("============init system timer task:execute lamp and gzone relationship!============");
    }

    public void insertAllMonthOvertime(){
        System.out.println("定时器执行了");
        iAttendanceRecordService.insertAllMonthOvertime();
    }
}
