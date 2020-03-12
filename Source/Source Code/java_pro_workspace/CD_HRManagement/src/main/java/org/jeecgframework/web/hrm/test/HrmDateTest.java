package org.jeecgframework.web.hrm.test;


import com.alibaba.fastjson.JSONObject;
import org.jeecgframework.web.hrm.service.HrmDateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class HrmDateTest {
    @Autowired
    private HrmDateService hrmDateService;

    @Test
    public void insertOneYear() throws Exception{
        String sql = "insert into t_hrm_date(id,work_date,status) values(?,?,?)";


        Map<Object, Object> map = new LinkedHashMap<Object, Object>();

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
        calendar2.add(Calendar.YEAR, 2);// 加上一年的后的日期
        Date first = calendar.getTime();
        Date next = calendar2.getTime();
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
            String fdate = "date=" + sdf.format(first.getTime());
            String jsonResult = request(httpUrl, fdate);

            String code = jsonResult.substring(jsonResult.length()-2,jsonResult.length()-1);

            //存入数据库
            hrmDateService.executeSql(sql,UUID.randomUUID().toString(),first,code);


            JSONObject jasonObject = JSONObject.parseObject(jsonResult);
            map.put(sdf.format(first.getTime()), jasonObject);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            calendar.getTime();
        }
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
}
