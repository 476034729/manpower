package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.WorkHourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AllWorkHourListUtil {
	    
	@Autowired
	private WorkHourService workHourService;

    private static Logger logger = LoggerFactory.getLogger(addAttendanceRecordRSTask.class);
    public AllWorkHourListUtil(){
        logger.info("============init system timer task:execute lamp and gzone relationship!============");
    }

    public void insertWorkHourListAuto(){
        System.out.println("定时器执行了");
        workHourService.insertWorkHourListAuto();
    }
}
