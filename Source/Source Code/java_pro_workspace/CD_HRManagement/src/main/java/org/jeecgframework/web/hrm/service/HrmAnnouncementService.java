package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.HrmAnnouncement;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.List;

/**
 * @author Wang Yu
 * 公告Service
 */
public interface HrmAnnouncementService extends CommonService {
    HrmAnnouncement save(HrmAnnouncement announcement, TSUser optUser);

    /**
     * 更新公告
     */
    Boolean update(HrmAnnouncement announcement, TSUser optUser);

    /**
     * 返回指定数量
     * @return
     */
    List<HrmAnnouncement> listLimit(int start, int max);

}
