package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.VacationApply;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface VacationApplyService extends CommonService {
    TSUser getUserById(String id);
    boolean addApply(VacationApply vacationApply);
    List<VacationApply> getApplyList(String user_id);
    VacationApply getApplyById(String id);
    boolean updateApply(VacationApply vacationApply);
}
