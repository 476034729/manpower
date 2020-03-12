package org.jeecgframework.web.hrm.service;

import java.sql.Timestamp;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 打卡记录查询  service层
 * @author Administrator
 *
 */
@Service
@Transactional
public interface CardingRecordQueryService extends CommonService{
	
	
//	List<Object[]> getCardingRecordByID(int year, int month,String account_name);

	List<Object[]> getCardingRecordByIDAndTime(Timestamp begTime, Timestamp endTime, String account_name);

}
