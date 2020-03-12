package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.ApplyCheckWithApply;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public interface ApplyCheckService extends CommonService {
    boolean addVacation(String apply_id, String department);

    List<ApplyCheckWithApply> getVacationCheckList(String user_id);

    TSUser getUserName(String id);

    Boolean check(String id ,String agree,String remark);

    boolean updateVacation(String apply_id, String department);
}
