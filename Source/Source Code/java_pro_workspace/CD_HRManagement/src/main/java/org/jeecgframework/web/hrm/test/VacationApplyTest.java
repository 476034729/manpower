package org.jeecgframework.web.hrm.test;

import org.hibernate.Query;
import org.jeecgframework.web.hrm.bean.HrmAnnualLeaveStatistics;
import org.jeecgframework.web.hrm.bean.VacationApply;
import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.jeecgframework.web.hrm.service.VacationApplyService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class VacationApplyTest {
    @Autowired
    private VacationApplyService vacationApplyService;

    @Autowired
    private IAttendanceRecordService iAttendanceRecordService;

    @Test
    public void getOne(){
        String hql = " from VacationApply u where u.id = ?";
        List<VacationApply> list = vacationApplyService.findHql(hql,"064d4b0a-4a21-4c7d-accc-cc19ef873591");
        System.out.println(list.size());
    }

    @Test
    public void testTwo(){
        String hql="from TSUser where userstatus='在职' and type='正式'";
        List<TSUser> users = iAttendanceRecordService.findHql(hql);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for(int i=0;i<users.size();i++){
            HrmAnnualLeaveStatistics hrmAnnualLeaveStatistics = new HrmAnnualLeaveStatistics();
            //查询该用户是否有去年的年假记录
            String hql1 = "from HrmAnnualLeaveStatistics where user_id=? and year=?";
            List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = iAttendanceRecordService.findHql(hql1,users.get(i).getId(),year-1);
            HrmAnnualLeaveStatistics hrmAnnualLeaveStatisticsBefore = null;
            if(hrmAnnualLeaveStatisticsList.size()>0){
                System.out.println("yyyyyyyyyyyyyy");
                hrmAnnualLeaveStatisticsBefore = hrmAnnualLeaveStatisticsList.get(0);
            }
            hrmAnnualLeaveStatistics.setId(UUID.randomUUID().toString());
            hrmAnnualLeaveStatistics.setUser_name(users.get(i).getUserName());
            hrmAnnualLeaveStatistics.setUser_id(users.get(i).getId());
            if(hrmAnnualLeaveStatisticsBefore!=null){
                hrmAnnualLeaveStatistics.setPrevious_year(hrmAnnualLeaveStatisticsBefore.getThis_surplus_days());
                hrmAnnualLeaveStatistics.setPrevious_surplus_days(hrmAnnualLeaveStatisticsBefore.getThis_surplus_days());
            }else{
                hrmAnnualLeaveStatistics.setPrevious_year(0.0);
            }
            hrmAnnualLeaveStatistics.setThis_year(0.0);
            hrmAnnualLeaveStatistics.setCount_annual_leave(hrmAnnualLeaveStatistics.getPrevious_year());
            hrmAnnualLeaveStatistics.setUse_days(0.0);
            hrmAnnualLeaveStatistics.setSurplus_days(hrmAnnualLeaveStatistics.getPrevious_year());
            hrmAnnualLeaveStatistics.setCreate_time(new Date());
            hrmAnnualLeaveStatistics.setModify_time(new Date());
            hrmAnnualLeaveStatistics.setThis_surplus_days(0.0);
            hrmAnnualLeaveStatistics.setYear(year);
            iAttendanceRecordService.save(hrmAnnualLeaveStatistics);
        }
    }

    @Test
    public void testThree(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        String sql = "update cronExpression set count_annual_leave=count_annual_leave+1,surplus_days=surplus_days+1,this_surplus_days=this_surplus_days+1 where year=?";
        Query query = iAttendanceRecordService.getSession().createSQLQuery(sql);
        query.setParameter(0,year);
        query.executeUpdate();
    }
}