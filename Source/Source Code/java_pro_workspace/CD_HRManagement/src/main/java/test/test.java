package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.hrm.utils.HolidayUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.alibaba.fastjson.JSONObject;

public class test {
	public static void main(String[] args) throws ParseException {
		/*try {
		String date1 ="2018-11-29 9:00:00";
		String date2 = "2018-11-30 18:00:00" ;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date start = sim.parse(date1);
		Date end = sim.parse(date2);
		System.out.println(start+"---"+end);
		 SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
         SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String startYMDString = sdfYearMonthDay.format(start);
         String endYMDString = sdfYearMonthDay.format(end);
         String startAllString = sdfAll.format(start);
         String endAllString = sdfAll.format(end);
         System.out.println(startYMDString+"----"+endYMDString+"----"+startAllString+"----"+endAllString);
     	Date startYMD =  sdfYearMonthDay.parse(startYMDString);
     	Date endYMD = sdfYearMonthDay.parse(endYMDString);
     	Date startAll = sdfAll.parse(startAllString);
     	Date endAll = sdfAll.parse(endAllString);
     	System.out.println(startYMD+"---"+endYMD+"---"+startAll+"---"+endAll);
		
			Date l = sim.parse(date1);
			
			System.out.println(dr(sim.parse(date1),sim.parse(date2)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static int dr(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }*/
		/*Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
		List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
		for(int i=day-1;i<dateUtilList.size();i++) {
			System.out.println(dateUtilList.get(i).getDate());
		}*/
		
		/*String Date = "2019-2-1";
		String []a = Date.split("-");
		Integer year =Integer.parseInt(a[0]);
		Integer month = Integer.parseInt(a[1]);
		List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
		for(int i=0;i<dateUtilList.size();i++) {
			System.out.println(dateUtilList.get(i).getDate());
		}*/
		
		
		/*//判断今天是否是工作日 周末 还是节假日
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String httpArg = "20181230";
        String jsonResult = holidayUtil.request(httpArg);
        System.out.println(httpArg+" = "+jsonResult);*/
		
		
		
		/*HolidayUtil holidayUtil = new HolidayUtil();
        Calendar a = Calendar.getInstance();
        a.add(Calendar.YEAR, 1);
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
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
        	
        	String id = UUID.randomUUID().toString();
            String fdate = sdfDateFormat.format(first.getTime());
            String httparg = sdf.format(first.getTime());
            String reslut = holidayUtil.request(httparg);
            
            System.out.println("id"+id+","+fdate+","+reslut);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
          //calendar.getTime();
        }*/  
		// 系统当前时间
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
			            List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);
			            for (DateUtil dateUtil:dateUtilList){
			            	String date = dateUtil.getDate();
			            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			            	Date getData = java.sql.Date.valueOf(dateUtil.getDate());
			            	  String http = sdf.format(getData);
			                  String reslut = HolidayUtil.request(http);
			                  System.out.println(date+"--"+reslut);
			      }
	}
}
