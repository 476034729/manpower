package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public interface ProjectInputTotalService extends CommonService {
    List<TSUser> getUserList();
    List<Map<String,Object>> getWorkHourByProject(String code,int year,int month);
    Map<String,Object> getUserWorkHour(String code, String id,int year ,int month);


    Map<String,Object> getWorkHourCount(String code,int year,int month);
}
