package org.jeecgframework.web.hrm.service.impl;

import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmAnnouncement;
import org.jeecgframework.web.hrm.service.HrmAnnouncementService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Yu
 */
@Service
public class HrmAnnouncementServiceImpl extends CommonServiceImpl implements HrmAnnouncementService {


    @Override
    public List<HrmAnnouncement> listLimit(int start, int max) {
        String hql = "from HrmAnnouncement h order by h.createDate desc";
        Query query = getSession().createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<HrmAnnouncement> announcementList = query.list();
        return announcementList;
    }

    @Override
    public HrmAnnouncement save(HrmAnnouncement announcement, TSUser optUser) {
        if (announcement.getId() == null){
            announcement.setCreateDate(new Date());
            announcement.setCreateId(optUser.getId());
            announcement.setCreateUser(optUser.getRealName());
            announcement.setModifyDate(new Date());
            announcement.setModifyId(optUser.getId());
            announcement.setModifyUser(optUser.getRealName());
            save(announcement);
            return announcement;
        }
        return null;
    }

    @Override
    public Boolean update(HrmAnnouncement announcement, TSUser optUser) {
        if (announcement.getId() != null){
            announcement = get(HrmAnnouncement.class, announcement.getId());
            announcement = get(HrmAnnouncement.class, announcement.getId());
            announcement.setModifyUser(optUser.getRealName());
            announcement.setModifyId(optUser.getId());
            announcement.setModifyDate(new Date());
            updateEntitie(announcement);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
