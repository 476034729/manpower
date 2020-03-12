package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmRole;
import org.jeecgframework.web.hrm.bean.HrmRoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface HrmRoleService  extends CommonService {
    List<HrmRoleMenu> findMenuByRole(HrmRole hrmRole);
}
