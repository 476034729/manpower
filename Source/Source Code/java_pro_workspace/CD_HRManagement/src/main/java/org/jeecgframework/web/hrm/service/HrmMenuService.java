package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface HrmMenuService extends CommonService {
    List<HrmMenu> findMenuByParent(String id);
}
