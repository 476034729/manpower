package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmMenu;

import org.jeecgframework.web.hrm.bean.HrmUser;
import org.jeecgframework.web.hrm.bean.HrmUserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface HrmUserService extends CommonService {
    HrmUser checkUserExits(HrmUser hrmUser);
    List<HrmUserRole> getRoleByUser(HrmUser hrmUser);

    Map<String,List<HrmMenu>> getMenuByUser(HrmUser hrmUser);
}
