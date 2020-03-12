package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class addAttendanceRecordRSTask {
	 	@Autowired
	    private IAttendanceRecordService iAttendanceRecordService;

	    private static Logger logger = LoggerFactory.getLogger(addAttendanceRecordRSTask.class);
	    public addAttendanceRecordRSTask(){
	        logger.info("============init system timer task:execute lamp and gzone relationship!============");
	    }

	    public void insertAttendanceRecordAuto(){
	        System.out.println("定时器执行了");
	        iAttendanceRecordService.insertAttendanceRecordAuto();
	    }
}
