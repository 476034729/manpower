package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.service.ProjectCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProjectCodeService")
@Transactional
public class ProjectCodeServiceImpl extends CommonServiceImpl implements ProjectCodeService {
}
