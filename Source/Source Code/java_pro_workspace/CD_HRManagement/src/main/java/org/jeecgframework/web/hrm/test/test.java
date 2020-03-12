package org.jeecgframework.web.hrm.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.hrm.utils.HolidayUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    @Test
    public void getdate() {
        int i = new DateUtil().getWorkDay(2018, 7);
        System.out.println(i);
    }

    //	实现给定某日期，判断是星期几
    public static String getWeekday(String date) {//必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }

    @Test
    public void e() {
        System.out.println("2018-7-13,am".equals("2018-7-13,am"));
    }

    public Map<Object,Object> getWorkDays()
            throws Exception {
//        JSONObject.parseArray(jsonResult,String.class)

        Map<Object,Object> map= new LinkedHashMap<Object, Object>();
        List<String> list = new ArrayList<String>();
        Calendar a = Calendar.getInstance();
        String httpUrl = "http://api.goseek.cn/Tools/holiday";
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
            String fdate = "date=" + sdf.format(first.getTime());
            String jsonResult = request(httpUrl, fdate);
            list.add(jsonResult);

//            String code = jsonResult.substring(jsonResult.length()-2,jsonResult.length()-1);
//            System.out.println(code);

            Date newDates = new Date();

            System.out.println(first);

            JSONObject  jasonObject = JSONObject.parseObject(jsonResult);
            map.put(sdf.format(first.getTime()),jasonObject);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            calendar.getTime();
        }
        return map;
    }


    public String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "abfa5282a89706affd2e4ad6651c9648");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
//                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void holiday() {
        Map<Object,Object> map = new LinkedHashMap<Object, Object>();
        try {
            map=getWorkDays();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o :map.keySet()){
            System.out.println("key=" +o +" value="+map.get(o));
        }
    }


}
