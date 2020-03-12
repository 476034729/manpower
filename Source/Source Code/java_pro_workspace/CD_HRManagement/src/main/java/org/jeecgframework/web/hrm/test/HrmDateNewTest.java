package org.jeecgframework.web.hrm.test;


import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.service.HrmDateService;
import org.jeecgframework.web.hrm.utils.HolidayUtil;
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
public class HrmDateNewTest {
    @Autowired
    private HrmDateService hrmDateService;

    @Test
    public void insertOneYear() throws Exception{
        String sql = "insert into t_hrm_date(id,work_date,status) values(?,?,?)";
     /*   String sql1 = "delete from t_hrm_date where YEAR(work_date)="+2019;
        hrmDateService.executeSql(sql1);*/
        HolidayUtil holidayUtil = new HolidayUtil();
        Calendar a = Calendar.getInstance();
        String t = a.get(Calendar.YEAR) + "0101";// 开始的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 开始日期，并要累积加 一
        Calendar calendar2 = Calendar.getInstance();// 结束的日期
        Date time = sdf.parse(t);
        calendar.setTime(time);
        calendar2.setTime(time);
        calendar2.add(Calendar.YEAR, 2);// 加上一年的后的日期
        Date first = calendar.getTime();
        Date next = calendar2.getTime();
        HrmDate hrmDate=null;
        List<HrmDate> hrmDates=new ArrayList<HrmDate>();
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
        	hrmDate=new HrmDate();
        	String id = UUID.randomUUID().toString();
            String fdate = sdfDateFormat.format(first.getTime());
            String httparg = sdf.format(first.getTime());
//            String reslut = holidayUtil.doGet(httparg);
            hrmDate.setId(id);
            Calendar calendar22=Calendar.getInstance();
            calendar22.setTime(first);
            int week=calendar.get(Calendar.DAY_OF_WEEK);
            if(week==1|| week==7){
                hrmDate.setStatus("1");
            }else {
                hrmDate.setStatus("0");
            }

            hrmDate.setWork_date(first);
            hrmDates.add(hrmDate);
            //存入数据库
//            hrmDateService.executeSql(sql,UUID.randomUUID().toString(),first,reslut);
//            System.out.println("id"+id+","+fdate+","+reslut);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            //calendar.getTime();
        }
        hrmDateService.batchSave(hrmDates);
    }



    @Test
    public void insertOneYear2() throws Exception{
        String sql = "update t_hrm_date set status=2  where work_date in (:ids)";
     /*   String sql1 = "delete from t_hrm_date where YEAR(work_date)="+2019;
        hrmDateService.executeSql(sql1);*/
        Calendar a = Calendar.getInstance();
        String t = a.get(Calendar.YEAR) + "0101";// 开始的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 开始日期，并要累积加 一
        Calendar calendar2 = Calendar.getInstance();// 结束的日期
        Date time = sdf.parse(t);
        calendar.setTime(time);
        calendar2.setTime(time);
        calendar2.add(Calendar.YEAR, 1);// 加上一年的后的日期
        Date first = calendar.getTime();
        Date next = calendar2.getTime();
        HrmDate hrmDate=null;
        List<String> hrmDates=new ArrayList<String>();
        List<Date> dates=new ArrayList<Date>();
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
            //存入数据库
            String httparg = sdf.format(first.getTime());
            String result=HolidayUtil.doGet(httparg);
            if("2".equals(result)){
                dates.add(first);
            }
//            hrmDateService.executeSql(sql,UUID.randomUUID().toString(),first,reslut);
//            System.out.println("id"+id+","+fdate+","+reslut);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            //calendar.getTime();
        }

//        hrmDateService.getSession().createSQLQuery(sql).setParameterList("ids",dates).executeUpdate();
    }

}
