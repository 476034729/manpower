package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

/**
 * 每年一月一号为正式员工增加该年的年假记录
 */
@Component
public class AnnualLeaveNewRecordRSTask {
    @Autowired
    private IAttendanceRecordService iAttendanceRecordService;

    private static Logger logger = LoggerFactory.getLogger(AsyncExecLampAndGZoneRSTask.class);
    public AnnualLeaveNewRecordRSTask(){
        logger.info("============init system timer task:execute lamp and gzone relationship!============");
    }

    public void insertAnnualLeaveRecordAuto(){
        System.out.println("定时器执行了");
        iAttendanceRecordService.insertAnnualLeaveRecordAuto();
    }
}
