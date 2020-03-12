package org.jeecgframework.web.hrm.service.impl;


import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.VacationApply;
import org.jeecgframework.web.hrm.service.VacationApplyService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("VacationApplyService")
@Transactional
public class VacationApplyServiceImpl extends CommonServiceImpl implements VacationApplyService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(VacationApplyServiceImpl.class);

    @Override
    public TSUser getUserById(String id) {
        String hql = "from TSUser u where u.id = ? and u.jobNum != ' ' and u.site = 'CD'";
        List<TSUser> list = this.findHql(hql, id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean addApply(VacationApply vacationApply) {

        logger.info(">>VacationApplyServiceImpl:addApply:");

        String sql = "insert into t_hrm_vacation_apply(id,user_id,department,reson,begin_time,end_time,type,site,remark,signature,submit_time,totaltime,phone,status)" +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int success = this.executeSql(sql, vacationApply.getId(), vacationApply.getUser_id(), vacationApply.getDepartment(), vacationApply.getReason(), vacationApply.getBeginTime()
                , vacationApply.getEndTime(), vacationApply.getType(), vacationApply.getSite(), vacationApply.getRemark(), vacationApply.getSignature(), vacationApply.getSubmitTime(),
                vacationApply.getTotalTime(), vacationApply.getPhone(), vacationApply.getStatus());
        return success == 1;
    }

    @Override
    public List<VacationApply> getApplyList(String user_id) {
        String hql = "from VacationApply u where u.user_id=?";
        return this.findHql(hql, user_id);
    }

    @Override
    public VacationApply getApplyById(String id) {
        String hql = "from VacationApply u where u.id=?";
        return (VacationApply) this.findHql(hql, id).get(0);
    }

    @Override
    public boolean updateApply(VacationApply vacationApply) {

        logger.info(">>VacationApplyServiceImpl:updateApply:");

        String sql = "update t_hrm_vacation_apply set reson = ?,begin_time = ?,end_time = ?, type = ? ,site = ?, status = ?, phone = ?, totaltime = ?, signature = ?" +
                ",submit_time = ? ,remark = ? where id = ?";
        int i =this.executeSql(sql, vacationApply.getReason(), vacationApply.getBeginTime(), vacationApply.getEndTime(), vacationApply.getType(), vacationApply.getSite()
                , vacationApply.getStatus(), vacationApply.getPhone(), vacationApply.getTotalTime(), vacationApply.getSignature(), vacationApply.getSubmitTime(), vacationApply.getRemark(), vacationApply.getId());
        return 1 == i;
    }
}
