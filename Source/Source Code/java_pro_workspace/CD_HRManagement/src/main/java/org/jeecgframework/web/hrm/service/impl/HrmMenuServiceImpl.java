package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.jeecgframework.web.hrm.service.HrmMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("HrmMenuService")
@Transactional
public class HrmMenuServiceImpl extends CommonServiceImpl implements HrmMenuService {
    @Override
    public List<HrmMenu> findMenuByParent(String id) {
        String hql="from HrmMenu u where u.parent_id = ? and u.menu_id != ?";
        List<HrmMenu> list=commonDao.findHql(hql,id,id);
        return list;
    }
}
