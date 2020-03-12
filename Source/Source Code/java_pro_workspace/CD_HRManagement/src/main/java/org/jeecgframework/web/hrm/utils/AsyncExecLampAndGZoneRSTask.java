package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.hrm.service.WorkHourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
public class AsyncExecLampAndGZoneRSTask{
	@Autowired
	private WorkHourService workHourService;
	private static Logger logger = LoggerFactory.getLogger(AsyncExecLampAndGZoneRSTask.class);
	
	public AsyncExecLampAndGZoneRSTask(){
		logger.info("============init system timer task:execute lamp and gzone relationship!============");
	}
	
//	@Scheduled(cron="0 0/1 * * * ?")
	public void updateLampInfo(){
		workHourService.insertAllWorkHour();
	}
	


	
}
