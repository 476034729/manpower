package org.jeecgframework.web.hrm.service.impl;


import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.service.HrmDateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("HrmDateService")
@Transactional
public class HrmDateServiceImpl extends CommonServiceImpl implements HrmDateService {
}
