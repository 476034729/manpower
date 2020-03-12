package org.jeecgframework.web.hrm.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.hrm.bean.ExpenseProject;
import org.jeecgframework.web.hrm.service.ReimburseService;
import org.jeecgframework.web.system.pojo.base.TSReimDetails;
import org.jeecgframework.web.system.pojo.base.TSReimburse;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @authortangzhen
 * @createDate2019-07-17
 */
@Service("reimburseService")
@Transactional
public class ReimburseServiceImpl extends CommonServiceImpl implements ReimburseService {
    @Override
    public TSReimburse findReimburse(String id) {
        String sql="from TSReimburse t where t.id=? ";
        List<TSReimburse> list= this.findHql(sql, id);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TSReimDetails> findReimDetails(String reimburseId) {
        String sql="from TSReimDetails t where t.reimburseId=? ";
        List<TSReimDetails> list=this.findHql(sql,reimburseId);
        return list;
    }

    @Override
    public List<Map<String,Object>> getReimburseList(String userId, int page, int rows, java.util.Date startDate, java.util.Date endDate) {
        StringBuilder sql=new StringBuilder("select t.id,bu.username,u.jobNum,t.start_time as startTime,t.end_time as endTime,t.project_name as projectName," +
                "t.expend_name as expendName,t.costcenter_code as costCenterCode,t.country_name as countryName,t.apply_date as applyDate " +
                "from t_s_reimburse  t,t_s_user u,t_s_base_user bu  " +
                "where t.user_id=u.id " +
                "and  u.id=bu.id " +
                "and  u.id=?  ");
        List<Object> params2 = new ArrayList<Object>();
        params2.add(userId);
        if(null!=startDate){
            sql.append("and t.apply_date >= ? ");
            params2.add(startDate);
        }
        if(null!=endDate){
            sql.append("and t.apply_date <= ? ");
            params2.add(endDate);
        }
        Object[] a=new Object[params2.size()];
        for(int i=0,l=params2.size();i<l;i++){
            a[i]=params2.get(i);
        }
        List<Map<String,Object>> list=this.findForJdbcParam(sql.toString(), page, rows, a);
        return list;
    }

    @Override
    public List<Map<String, Object>> getReimburseList2(String userId) {
        String sql="select id,user_id  as userId,project_name as projectName,expend_name as expendName," +
                "apply_date as applyDate,start_time as startTime,end_time as endTime,costcenter_code as costcenterCode,currency,country_name as countryName," +
                "allowance_fee as allowanceFee,update_time as updateTime from t_s_reimburse where user_id=? ";

        return this.findForJdbc(sql,userId);
    }


    @Override
    public void addReimburse(Map<String,Object> map) throws Exception {
        String userId = (String) map.get("userId");
        String projectName = (String) map.get("projectName");
        String expendName = (String) map.get("expendName");
        String countryName = (String) map.get("countryName");
        Timestamp applyTime = Timestamp.valueOf(map.get("applyDate").toString()+" 00:00:00");

        String costcenterCode = (String) map.get("costcenterCode");
        String currency = (String) map.get("currency");
        BigDecimal allowanceFee=BigDecimal.ZERO;
        if(map.get("allowanceFee")!=null&&!"".equals(map.get("allowanceFee"))){
             allowanceFee= new BigDecimal(map.get("allowanceFee").toString());
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        TSReimburse reimburse = new TSReimburse();

        if(map.get("startTime")!=null&&!"".equals(map.get("startTime"))){
            long  startDt = Long.valueOf(map.get("startTime").toString());
            Timestamp startTime = new Timestamp(startDt);
            reimburse.setStartTime(startTime);
        }

        if(map.get("endTime")!=null&&!"".equals(map.get("endTime"))){
            long endDt = Long.valueOf(map.get("endTime").toString());
            Timestamp endTime = new Timestamp(endDt);
            reimburse.setEndTime(endTime);
        }
        reimburse.setApplyDate(applyTime);
        reimburse.setCostCenterCode(costcenterCode);
        reimburse.setCurrency(currency);
        reimburse.setUserId(userId);
        reimburse.setProjectNmae(projectName.trim());
        reimburse.setExpendName(expendName.trim());
        reimburse.setCountryName(countryName.trim());
        reimburse.setAllowanceFee(allowanceFee);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("reimburseList");
        List<TSReimDetails> tsReimDetailsList = new ArrayList<TSReimDetails>();

        this.save(reimburse);
        String reimburseId = reimburse.getId();
        String createDt = "";
        Date createDate = new Date(System.currentTimeMillis());
        int costType = 0;// 1 交通 2 酒店 3 通讯  9 其它
        String details = "";
        BigDecimal costfee = BigDecimal.ZERO;
        for (Map<String, Object> m : list) {
            TSReimDetails tsReimDetails = new TSReimDetails();
            createDt=(String) m.get("createDate");
            if(StringUtil.isNotEmpty(createDt)){
                createDate = Date.valueOf(createDt.trim());
            }
            costType = Integer.valueOf(m.get("costType").toString());
            details = (String) m.get("details");
            if(m.get("costFee")!=null&&!"".equals(m.get("costFee"))){
                costfee = new BigDecimal(m.get("costFee").toString());
            }
            tsReimDetails.setReimburseId(reimburseId);
            tsReimDetails.setCostFee(costfee);
            tsReimDetails.setCreateDate(createDate);
            tsReimDetails.setDetails(details);
            tsReimDetails.setExpenseType(costType);
            tsReimDetailsList.add(tsReimDetails);
        }
        addReimDetails(tsReimDetailsList);

    }
        @Override
        public void addReimDetails (List < TSReimDetails > tsReimDetails) {
            this.batchSave(tsReimDetails);
        }

        @Override
        public void updateReimburse (Map<String,Object> map) throws Exception {
            String id = (String) map.get("id");
            String userId = (String) map.get("userId");
            String projectName = (String) map.get("projectName");
            String expendName = (String) map.get("expendName");
            String countryName = (String) map.get("countryName");
            Timestamp applyTime = Timestamp.valueOf(map.get("applyDate").toString()+" 00:00:00");
            String costcenterCode = (String) map.get("costcenterCode");
            String currency = (String) map.get("currency");
            BigDecimal allowanceFee=BigDecimal.ZERO;
            if(map.get("allowanceFee")!=null&&!"".equals(map.get("allowanceFee"))){
                allowanceFee= new BigDecimal(map.get("allowanceFee").toString());
            }
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            TSReimburse reimburse = findReimburse(id);

            if(map.get("startTime")==null){
                reimburse.setStartTime(null);
            }
            else if(map.get("startTime")!=null&&!"".equals(map.get("startTime"))){
                long  startDt = Long.valueOf(map.get("startTime").toString());
                Timestamp startTime = new Timestamp(startDt);
                reimburse.setStartTime(startTime);
            }
            if(map.get("endTime")==null){
                reimburse.setEndTime(null);
            }else  if(map.get("endTime")!=null&&!"".equals(map.get("endTime"))){
                long endDt = Long.valueOf(map.get("endTime").toString());
                Timestamp endTime = new Timestamp(endDt);
                reimburse.setEndTime(endTime);
            }
            reimburse.setApplyDate(applyTime);
            reimburse.setCostCenterCode(costcenterCode);
            reimburse.setCurrency(currency);
            reimburse.setUserId(userId);
            reimburse.setProjectNmae(projectName.trim());
            reimburse.setExpendName(expendName.trim());
            reimburse.setUpdate_time(currentTime);
            reimburse.setCountryName(countryName.trim());
            reimburse.setAllowanceFee(allowanceFee);
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("reimburseList");
            List<TSReimDetails> tsReimDetailsList = new ArrayList<TSReimDetails>();
            this.saveOrUpdate(reimburse);
            deleteReimDetailsById(id);
            String createDt = "";
            Date createDate = new Date(System.currentTimeMillis());
            int costType = 0;// 1 交通 2 酒店 3 通讯  9 其它
            String details = "";
            BigDecimal costfee = BigDecimal.ZERO;
            for (Map<String, Object> m : list) {
                TSReimDetails tsReimDetails = new TSReimDetails();
                createDt=(String) m.get("createDate");
                if (StringUtils.isNotEmpty(createDt)) {
                    createDate = Date.valueOf(createDt.trim());
                }
                costType = Integer.valueOf(m.get("costType").toString());
                details = (String) m.get("details");
                if(m.get("costFee")!=null&&!"".equals(m.get("costFee"))){
                    costfee = new BigDecimal(m.get("costFee").toString());
                }
                tsReimDetails.setReimburseId(id);
                tsReimDetails.setCostFee(costfee);
                tsReimDetails.setCreateDate(createDate);
                tsReimDetails.setDetails(details);
                tsReimDetails.setExpenseType(costType);
                tsReimDetails.setUpdate_time(currentTime);
                tsReimDetailsList.add(tsReimDetails);
            }
            updateReimDetails(tsReimDetailsList);

        }

        @Override
        public void updateReimDetails (List < TSReimDetails > tsReimDetails) {
            this.batchUpdate(tsReimDetails);
        }

        @Override
        public List<Map<String, Object>> findAllowance () {
            return this.findForJdbc("select * from t_allowance_cfg ");
        }

    @Override
    public Map<String, Object> findCountryByName(String name) {
        String sql="select id,country_name,allowance_fee  from t_allowance_cfg where country_name=? ";
        Map<String,Object> map=this.findOneForJdbc(sql,name);
        return map;
    }

    @Override
        public void deleteReimDetailsById (String reimburseId){
            this.executeSql("delete from t_s_reim_details where reimburse_id=? ", reimburseId);
        }

        @Override
        public List<ExpenseProject> getProjectList () {
            String hql = "from ExpenseProject u where  u.codeStatus = 1   ";
            List<ExpenseProject> list = this.findHql(hql);
            return list;
        }

    @Override
    public ExpenseProject findByProjectName(String name) {
        String hql="from ExpenseProject u where  u.projectName=? and u.codeStatus = 1   ";
        List<ExpenseProject> list = this.findHql(hql,name);
        ExpenseProject p=null;
        if(CollectionUtils.isEmpty(list)){
            p=new ExpenseProject();
            return p;
        }
        p=list.get(0);
        return p;
    }

    @Override
    public List<Map<String, Object>> getReimburseListByAll(int page, int rows, String userName, java.util.Date startDate, java.util.Date endDate) {
        StringBuilder sql=new StringBuilder("select t.id,bu.username,u.jobNum,t.start_time as startTime,t.end_time as endTime,t.project_name as projectName," +
                "t.expend_name as expendName,t.costcenter_code as costCenterCode,t.country_name as countryName,t.apply_date as applyDate " +
                        "from t_s_reimburse  t,t_s_user u,t_s_base_user bu  " +
                        "where t.user_id=u.id " +
                        "and  u.id=bu.id  ");
        List<Object> params=new ArrayList<Object>();
        if(StringUtils.isNotEmpty(userName)){
            sql.append("and bu.username like ? ");
            params.add(userName);
        }
        if(null!=startDate){
            sql.append("and t.apply_date >= ? ");
            params.add(startDate);
        }
        if(null!=endDate){
            sql.append("and t.apply_date <= ? ");
            params.add(endDate);
        }
        Object[] a=new Object[params.size()];
        for(int i=0,l=params.size();i<l;i++){
            a[i]=params.get(i);
        }
        return this.findForJdbcParam(sql.toString(), page,rows,a);
    }

    @Override
    public List<TSUser> findAllUser() {
        String hql="from TSUser where userstatus ='在职' ";
        List<TSUser> list=this.findHql(hql);
        return list;
    }

    @Override
    public void deleteById(String id) {
        String sql="delete from t_s_reimburse where id=? ";
        deleteReimDetailsById(id);
        this.executeSql(sql, id);
    }
}
