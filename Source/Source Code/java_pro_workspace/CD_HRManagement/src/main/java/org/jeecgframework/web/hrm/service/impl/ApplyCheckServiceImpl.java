package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.ApplyCheckService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("ApplyCheckService")
@Transactional
public class ApplyCheckServiceImpl extends CommonServiceImpl implements ApplyCheckService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(ApplyCheckServiceImpl.class);

    @Override
    public boolean addVacation(String apply_id, String department) {
        logger.info(">>ApplyCheckServiceImpl:addVacation: "+new Date().toString());

        String hql = "from CheckProcess u where u.department =?";
        List<CheckProcess> checkProcessList = this.findHql(hql, department);
        ApplyCheck applyCheck = new ApplyCheck();
        applyCheck.setId(UUID.randomUUID().toString());
        applyCheck.setApply_id(apply_id);
        applyCheck.setUser_id(checkProcessList.get(0).getFunctionary_id());
        applyCheck.setType("请假");
        applyCheck.setStatus("3");//审核中
        String sql = "insert into t_hrm_apply_check(id,apply_id,user_id,status,type) values(?,?,?,?,?)";
        int success = this.executeSql(sql, applyCheck.getId(), applyCheck.getApply_id(), applyCheck.getUser_id(), applyCheck.getStatus(), applyCheck.getType());

        return success == 1;
    }

    @Override
    public List<ApplyCheckWithApply> getVacationCheckList(String user_id) {


        List<ApplyCheckWithApply> list = new ArrayList<ApplyCheckWithApply>();

        String hql = "from ApplyCheck u  where u.user_id = ? order by u.checkTime asc";
        String hqll = "from VacationApply u where u.id = ?";

        List<ApplyCheck> applyChecks = this.findHql(hql, user_id);

        if (applyChecks.size() == 0) {
            return null;
        }

        for (ApplyCheck applyCheck : applyChecks) {
            VacationApply vacationApply = (VacationApply) this.findHql(hqll, applyCheck.getApply_id()).get(0);
            ApplyCheckWithApply applyCheckWithApply = new ApplyCheckWithApply();
            applyCheckWithApply.setId(applyCheck.getId());
            applyCheckWithApply.setApply_id(applyCheck.getApply_id());
            applyCheckWithApply.setBeginTime(vacationApply.getBeginTime().toString());
            applyCheckWithApply.setEndTime(vacationApply.getEndTime().toString());
            if ("3".equals(applyCheck.getStatus())) {
                applyCheckWithApply.setStatus("请审核");
            } else {
                applyCheckWithApply.setStatus("已审核");
            }
            applyCheckWithApply.setType(applyCheck.getType());

            applyCheckWithApply.setApplyPerson(getUserName(vacationApply.getUser_id()).getUserName());
            list.add(applyCheckWithApply);
        }
        return list;
    }

    @Override
    public TSUser getUserName(String id) {


        String hql = "from TSUser u where u.id = ?";
        List<TSUser> list = this.findHql(hql, id);
        return list.get(0);
    }

    @Override
    public Boolean check(String id, String agree, String remark) {



        String checkBeanSql = "SELECT a.`user_id` AS check_id,b.`user_id`,b.`id` AS apply_id,c.`functionary_id` AS f_id,c.`conductor_id` AS c_id,c.`personnel_id` AS p_id " +
                "FROM t_hrm_apply_check a JOIN t_hrm_vacation_apply b ON a.`apply_id` = b.`id` JOIN t_hrm_checkprocess c ON b.`department`=c.`department`" +
                " WHERE a.id ='" + id + "' ";
        CheckBean checkBean = new CheckBean();
        List list = this.findListbySql(checkBeanSql);
        Object[] object = (Object[]) list.get(0);
        checkBean.setCheck_id(String.valueOf(object[0]));
        checkBean.setUser_id(String.valueOf(object[1]));
        checkBean.setApply_id(String.valueOf(object[2]));
        checkBean.setF_id(String.valueOf(object[3]));
        checkBean.setC_id(String.valueOf(object[4]));
        checkBean.setP_id(String.valueOf(object[5]));

        String nextCheckPerson = "";

        if (checkBean.getCheck_id().equals(checkBean.getF_id())) {
            nextCheckPerson = checkBean.getC_id();
        } else if (checkBean.getCheck_id().equals(checkBean.getC_id())) {
            nextCheckPerson = checkBean.getP_id();
        } else if (checkBean.getCheck_id().equals(checkBean.getP_id())) {
            nextCheckPerson = "none";
        }


        if ("agree".equals(agree)) {
            if ("none".equals(nextCheckPerson)) {
                String updateSqlToApply = "update t_hrm_vacation_apply set status = '1' where id = ?";
                String updateSqlToCheck = "update t_hrm_apply_check set status = ? , check_time=? , remark = ? where id = ?";
                this.executeSql(updateSqlToCheck, "1", new Date(), remark, id);
                this.executeSql(updateSqlToApply, checkBean.getApply_id());
                return true;
            } else if (nextCheckPerson.length() > 0) {
                String insertSql = "insert into t_hrm_apply_check(id,apply_id,user_id,status) values(?,?,?,?)";
                String updateSql = "update t_hrm_apply_check set status = ? , check_time=? , remark = ? where id = ?";
                try {
                    this.executeSql(updateSql, "1", new Date(), remark, id);
                    this.executeSql(insertSql, UUID.randomUUID().toString(), checkBean.getApply_id(), nextCheckPerson, "3");//3表示未处理
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        } else if ("disagree".equals(agree)) {
            String updateSqlToCheck = "update t_hrm_apply_check set status = ? , check_time=? , remark = ? where id = ?";
            String updateSqlToApply = "update t_hrm_vacation_apply set status = ? where id = ?";
            this.executeSql(updateSqlToCheck, "1", new Date(), remark, id);
            this.executeSql(updateSqlToApply, "0", checkBean.getApply_id());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateVacation(String apply_id, String department) {

        logger.info(">>ApplyCheckServiceImpl:updateVacation: "+new Date().toString()+department);

        String hql = "from CheckProcess u where u.department =?";
        List<CheckProcess> checkProcessList = this.findHql(hql, department);
        ApplyCheck applyCheck = new ApplyCheck();
        applyCheck.setId(UUID.randomUUID().toString());
        applyCheck.setApply_id(apply_id);
        applyCheck.setUser_id(checkProcessList.get(0).getFunctionary_id());
        applyCheck.setType("请假");
        applyCheck.setStatus("3");//审核中
        String sql = "insert into t_hrm_apply_check(id,apply_id,user_id,status,type) values(?,?,?,?,?)";
        int success = this.executeSql(sql, applyCheck.getId(), applyCheck.getApply_id(), applyCheck.getUser_id(), applyCheck.getStatus(), applyCheck.getType());

        return success == 1;
    }


}
