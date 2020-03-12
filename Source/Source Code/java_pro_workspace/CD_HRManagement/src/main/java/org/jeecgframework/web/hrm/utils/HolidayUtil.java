package org.jeecgframework.web.hrm.utils;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HolidayUtil {
	/**
     * @Description: 入参格式为“yyyyMMdd”如“20181001”
     * @param
     * @return result(0 工作日 1周末 2节假日)
     * @throws
     * @author liuyi
     * @date 2018/12/27
     */
    public static String request(String httpArg) {
        //工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
        String httpUrl = "http://api.goseek.cn/Tools/holiday";
        String result = "";
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?date=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = MapUtils.getString(jsonStringToMap(String.valueOf(sbf)),"data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doGet(String date) {
        // 输入流
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 创建httpClient实例
        HttpClient httpClient = new HttpClient();
        // 设置http连接主机服务超时时间：15000毫秒
        // 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(150000);
        // 创建一个Get方法实例对象
        String url="http://www.easybots.cn/api/holiday.php?d="+date;
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时为60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        // 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
        try {
            // 执行Get方法
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断返回码
            if (statusCode != HttpStatus.SC_OK) {
                // 如果状态码返回的不是ok,说明失败了,打印错误信息
                System.err.println("Method faild: " + getMethod.getStatusLine());
            } else {
                // 通过getMethod实例，获取远程的一个输入流
                is = getMethod.getResponseBodyAsStream();
                // 包装输入流
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                // 读取封装的输入流
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp).append("\r\n");
                }

                result = sbf.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 释放连接
            getMethod.releaseConnection();
        }
        Map<String,String> map= (Map<String, String>) JSON.parse(result);
        return map.get(date);
    }


    /**
     * @Description: JSONString转Map
     * @param 
     * @return 
     * @throws
     * @author liuyi
     * @date 2018/12/27
     */
    private static Map<String, Object> jsonStringToMap(String json) {
        //String转成JSONObject形式
        JSONObject jsonArray = JSONObject.parseObject(json);
        Map<String, Object> param = JSONObject.parseObject(jsonArray.toJSONString(), new TypeReference<Map<String, Object>>() {});
        return param;
    }
    
    public String request1(String httpUrl, String httpArg) {
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
