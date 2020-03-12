package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.service.CheckProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("CheckProcessService")
@Transactional
public class CheckProcessServiceImpl extends CommonServiceImpl implements CheckProcessService {
}
