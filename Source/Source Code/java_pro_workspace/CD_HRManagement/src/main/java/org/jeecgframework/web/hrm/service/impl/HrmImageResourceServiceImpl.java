package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmImageResource;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
public class HrmImageResourceServiceImpl extends CommonServiceImpl implements HrmImageResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(HrmImageResource.class);

    /**
     * 图片的保存
     * @param image 图片实体
     * @return image
     */
    @Transactional
    @Override
    public HrmImageResource saveImage(HrmImageResource image, TSUser optUser) {
        // 插入操作信息
        image.setCreateDate(new Date());
        image.setCreateId(optUser.getId());
        image.setCreateUser(optUser.getRealName());
        image.setModifyDate(new Date());
        image.setModifyId(optUser.getId());
        image.setModifyUser(optUser.getRealName());
        save(image);
        LOG.info("保存:" + image.getUrl());
        return image;
    }

    /**
     * 删除图片资源
     * @param image
     * @param optUser
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteImage(HrmImageResource image, TSUser optUser) {
        if (image.getId() == null){
            return Boolean.FALSE;
        }
        HrmImageResource hrmImageResource = get(HrmImageResource.class, image.getId());
        String url = hrmImageResource.getUrl();
        File file = new File(url);
        if (file.exists()){
            if (file.delete()){  // 如果删除成功，则操作数据库。
                LOG.info("删除:" + hrmImageResource.getUrl());
                deleteEntityById(HrmImageResource.class, hrmImageResource.getId());
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
