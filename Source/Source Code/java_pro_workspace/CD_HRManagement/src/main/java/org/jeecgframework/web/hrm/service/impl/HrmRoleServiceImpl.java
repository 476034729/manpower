package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmRole;
import org.jeecgframework.web.hrm.bean.HrmRoleMenu;
import org.jeecgframework.web.hrm.service.HrmRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("HrmRoleService")
@Transactional
public class HrmRoleServiceImpl extends CommonServiceImpl implements HrmRoleService {
    @Override
    public List<HrmRoleMenu> findMenuByRole(HrmRole hrmRole) {
        String hql="from HrmRoleMenu u where u.hrmRole.role_id=?";
        return commonDao.findHql(hql,hrmRole.getRole_id());
    }
}
