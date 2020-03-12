package org.jeecgframework.web.hrm.utils;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil implements Serializable{
    private String date;
    private String week;
    private boolean disable;
    private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfHmd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdfHm=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public DateUtil()
    {}
    public DateUtil(String date, String week , boolean disable){
        this.date=date;
        this.week=week;
        this.disable=disable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public List<DateUtil> getDate(int year, int month){
        int days=0;
        List<DateUtil> list=new ArrayList<DateUtil>();
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days=30;
                break;
            default:
                break;
        }
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            if (month==2){
                days=29;
            }
        }else if (month==2){
            days=28;
        }
        for (int i=1;i<=days;i++){
            String date=year+"-"+month+"-"+i;
            if (DateUtil.Weekday(date).equals("星期日")||DateUtil.Weekday(date).equals("星期六")){
                list.add(new DateUtil(date,DateUtil.Weekday(date),true));
            }else {
                list.add(new DateUtil(date,DateUtil.Weekday(date),false));
            }
        }
        return list;
    }

    public int getWorkDay(int year , int month){
        List<DateUtil> list = this.getDate(year,month);
        int count = 0;
        for (DateUtil dateUtil :list){
            if (dateUtil.getWeek().equals("星期日") || dateUtil.getWeek().equals("星期六")){
                count++;
            }
        }
        return  list.size() - count;
    }
    //	实现给定某日期，判断是星期几
    public static String Weekday(String date){//必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
        }
        return sdw.format(d);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        return sdf.parse(dateStr);
    }

    public static String formatDate(long timemills){
        return sdf.format(new Date(timemills));
    }
    public static String formatDateTime(long timemills){
        return sdfHmd.format(new Date(timemills));
    }

    public static String formatDateTime2(long timeills){
        return sdfHm.format(new Date(timeills));
    }
	@Override
	public String toString() {
		return "DateUtil [date=" + date + ", week=" + week + ", disable=" + disable + "]";
	}
    
}
