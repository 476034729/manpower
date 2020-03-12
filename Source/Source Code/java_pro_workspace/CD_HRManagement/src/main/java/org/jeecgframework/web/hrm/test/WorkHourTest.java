package org.jeecgframework.web.hrm.test;


import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.service.WorkHourService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class WorkHourTest {

    @Autowired
    private WorkHourService workHourService;

    @Test
    public void addWorkHour() {
        Date date = new Date();
        String sql = "insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm,project_code,work_details)" +
                "values(?,?,?,?,?,?,?)";
        int i = workHourService.executeSql(sql, UUID.randomUUID().toString(), "8a8ab0b246dc81120146dc8181950052"
                , t(), date, "pm", "TEST", "something");
        System.out.println(i);
    }

    public Date t() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        System.out.println("当前时间是：" + dateFormat.format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();

        System.out.println("上一个月的时间： " + dateFormat.format(date));
        return date;
    }

    @Test
    public void getRecordByYearAndMonth() {
        int year = 2018;
        int month = 6;
        String sql = "select * from t_hrm_workhour where YEAR(work_day)=" + year + " and MONTH(work_day) = " + month + " and user_id = '4028518164d576b20164d5a52f6d005c'";
        String hql = "from WorkHour u where YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        System.out.println(workHourService.findListbySql(sql).size());
        System.out.println(workHourService.findHql(hql, year, month).size());
        System.out.println(workHourService.getWorkHourList("8a8ab0b246dc81120146dc8181950052", year, 7).size());
    }

    @Test
    public void getUserList() {
        int year = 2018;
        int month = 8;
        int count = 0;
        Map<String,Integer> map = new HashMap<String, Integer>();
        String sql = "select a.user_id, a.work_day, a.amorpm, a.project_code, b.department from t_hrm_workhour a join t_s_user b  on a.user_id = b.id where YEAR(a.work_day)=" + year + " and MONTH(a.work_day) = " + month;
        String countSql = "select count(1),b.project_name from t_hrm_workhour a join t_hrm_project_code b  on a.project_code = b.code where YEAR(a.work_day)=" + year + " and MONTH(a.work_day) = " + month + " group by a.project_code ";
        String counSqll = "select DISTINCT  a.id from t_s_user a join t_hrm_workhour b on a.id = b.user_id where YEAR(b.work_day)=" + year + " and MONTH(b.work_day) = " + month;
        String[] strings = {"开发部","测试部","系统部"};
        List<Object> ooo = workHourService.findListbySql(counSqll);
        List<Object> oo = workHourService.findListbySql(sql);
        List<Object> o = workHourService.findListbySql(countSql);
        for (Object o1 : o){
            Object[] objects = (Object[]) o1;
            count+=Integer.parseInt(String.valueOf(objects[0]));
        }
        for (String s :strings){
            String sss = "select count(1),b.project_name from t_hrm_workhour a join t_hrm_project_code b  on a.project_code = b.code join t_s_user c on c.id = a.user_id  where YEAR(a.work_day)=" + year + " and MONTH(a.work_day) = " + month + " and c.department = '"+ s + "' group by a.project_code ";
            List<Object> countList = workHourService.findListbySql(sss);
            int sum = 0;
            for (Object o1 : countList){
                Object[] objects = (Object[]) o1;
                sum+=Integer.parseInt(String.valueOf(objects[0]));
            }
            map.put(s,sum);
        }
        System.out.println(count);
        System.out.println(o);
    }
    
    @Test
    public void deleteWorkHour() {
    	String hql = "delete from t_hrm_workhour  where  YEAR(work_day)=2019 and MONTH(work_day) =1";
    	workHourService.executeSql(hql);
    }
    
    @Test
    public  void insertWorkHour(){
        String hql="from TSUser u where u.userstatus=? and position!=?";
        List<TSUser> list = workHourService.findHql(hql,"在职","集团总裁");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        System.out.println(list.size()+"yyyyyyyyyykkkkkkkkkkkk");
        for(int i=0;i<list.size();i++){
            TSUser tSUser = list.get(i);
            String hql1 = "from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
            List<WorkHour> list1 = workHourService.findHql(hql1,tSUser.getId(),year,month);
            if(list1.size() == 0){
                String sql = "insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm)" +
                        "values(?,?,?,?,?)";
                List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);
                for (DateUtil dateUtil:dateUtilList){
                    workHourService.executeSql(sql,UUID.randomUUID().toString(),tSUser.getId(),dateUtil.getDate(),new Date(),"am");
                    workHourService.executeSql(sql,UUID.randomUUID().toString(),tSUser.getId(),dateUtil.getDate(),new Date(),"pm");
                }
            }
        }
    }
}
