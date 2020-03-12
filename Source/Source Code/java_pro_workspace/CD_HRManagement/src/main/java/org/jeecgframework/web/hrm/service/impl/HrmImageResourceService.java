package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmImageResource;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.io.File;
import java.util.List;

/**
 * @author Wang Yu
 * 图片资源Service
 */
public interface HrmImageResourceService extends CommonService {

    HrmImageResource saveImage(HrmImageResource image, TSUser optUser);

    // 删除图片
    Boolean deleteImage(HrmImageResource image, TSUser optUser);

}
