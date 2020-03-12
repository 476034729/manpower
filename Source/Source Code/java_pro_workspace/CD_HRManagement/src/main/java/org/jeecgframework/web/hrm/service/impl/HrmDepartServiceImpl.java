package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDepart;
import org.jeecgframework.web.hrm.service.HrmDepartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HrmDepartServiceImpl extends CommonServiceImpl implements HrmDepartService {
    @Override
    public List<HrmDepart> findAll() {
        String sql="from HrmDepart";
        List<HrmDepart> departs= this.findHql(sql);
        return departs;
    }
}
