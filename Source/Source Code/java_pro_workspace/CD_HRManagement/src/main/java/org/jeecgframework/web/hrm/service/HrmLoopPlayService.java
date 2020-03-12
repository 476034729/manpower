package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmLoopPlayConfig;
import org.jeecgframework.web.hrm.bean.dto.LoopPlayConfigDTO;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.List;

/**
 * @author Wang Yu
 * 轮播配置serivce
 */
public interface HrmLoopPlayService extends CommonService {
    /**
     * 更新轮播配置
     * @param configList need Img Id
     * @param optUser
     * @return
     */
    Boolean saveConfig(List <HrmLoopPlayConfig> configList, TSUser optUser);

    List<LoopPlayConfigDTO> listInOrder();
}
