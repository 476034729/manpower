package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface HrmDepartService extends CommonService {


    /**
     * 查询全部
     * @return
     */
    List<HrmDepart> findAll();
}
