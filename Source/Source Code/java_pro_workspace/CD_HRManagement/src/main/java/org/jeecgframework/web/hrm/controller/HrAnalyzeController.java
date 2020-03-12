package org.jeecgframework.web.hrm.controller;



import org.apache.poi.hssf.usermodel.*;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.hrm.service.HrStatisticsService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/HrAnalyzeController")
public class HrAnalyzeController {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(HrAnalyzeController.class);

    @Autowired
    private HrStatisticsService hrStatisticsService;

    @RequestMapping(params = "gohrstatisticslist")
    public ModelAndView goHrStatisticsList(){
        return new ModelAndView("hrm/hrAnalyze/hrstatistics");
    }



    @RequestMapping(params = "getprojectlist")
    @ResponseBody
    public List<ProjectCode> getProjectList(){
        return hrStatisticsService.getProjectList();
    }



    /**
     * @Author Eale
     * @Description
     * @Date 10:56 2019/4/27
     * @Param [year, month]
     * @return org.jeecgframework.core.common.model.json.AjaxJson
     **/
    @RequestMapping(params = "getstatisticsinfo")
    @ResponseBody
    public AjaxJson getStatisticsInfo(int year , int month){
        logger.info(">>getStatisticsInfo：Start query year:"+year+",month:"+month);
        AjaxJson a =new AjaxJson();
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        if (!hrStatisticsService.hasRecord(year,month)){
            a.setSuccess(false);
            a.setMsg("没有该月数据!");
            return a;
        }

        List<TSUser> users = hrStatisticsService.getUserByWorkHour(year,month);

//        Map<String,Map<String,Object>> map= new LinkedHashMap<String, Map<String, Object>>();

        //mark
//        for (TSUser user :users){
//            Map<String,Object> newMap = new HashMap<String, Object>();
//            newMap.putAll(hrStatisticsService.getTimeOfCode(year,month,user.getId()));
//            newMap.putAll(hrStatisticsService.getRateOfAttendance(year,month,user.getId()));
//            map.put(user.getUserName(),newMap);
//
//        }
        a.setObj(hrStatisticsService.getTimeOfCode(year,month,users));
        logger.info(">>getStatisticsInfo：End query");
        return a;
    }

    @RequestMapping(params = "gettotalinfo")
    @ResponseBody
    public Map<String,Map<String,Object>> getTotalInfo(int year,int month){
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        Map<String,Map<String,Object>> map = new HashMap<String, Map<String, Object>>();
        map.put("ProjectPeopleDay",hrStatisticsService.getCodePeopleTimes(year,month));
        map.put("ProjectPeopleDayRate",hrStatisticsService.getProjectPeopleDayRate(year,month));
        return map;
    }

    @RequestMapping(params = "gohranalyzemap")
    public ModelAndView goHrAnalyzeMap(){
        return new ModelAndView("hrm/hrAnalyze/analyzemap");
    }

    @RequestMapping(params = "getprojectpeopledayrate")
    @ResponseBody
    public AjaxJson getProjectPeopleDayRate(int year ,int month){
        AjaxJson a = new AjaxJson();
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        try{
        	
        	Map<String, Object> map =hrStatisticsService.getProjectPeopleDayRate(year,month);
        	if(map==null) {
        		a.setSuccess(false);
                a.setMsg("没有该月数据");
                return a;
        	}else {
        		  a.setObj(map);
        	}
        }catch (Exception e){
            a.setSuccess(false);
        }
        return a;
    }

    @RequestMapping(params = "exportmapexcel",method = RequestMethod.POST)
    @ResponseBody
    public void exportMapExcel(HttpServletRequest request,HttpServletResponse response,int year , int month ) throws Exception{
        Map<String,Object> map = hrStatisticsService.getProjectPeopleDayRate(year,month);
        List<ProjectCode> list =hrStatisticsService.getProjectList();
        List<String> projectList = new ArrayList<String>();
        List<String> rateList = new ArrayList<String>();


        String chart = request.getParameter("img");
        String path  =request.getRealPath("images/echart/now.jpg");



        for (ProjectCode projectCode : list){
            projectList.add(projectCode.getProject_name());
            rateList.add((String) map.get(projectCode.getCode()));
        }
        String fileName = year+":"+month +".xls";

        HSSFWorkbook hssfWorkbook = hrStatisticsService.getHSSFWorkbook(year+"年"+month +"月人力分析图表",projectList,rateList);
        if (chartDecode(chart,path)){
            HSSFSheet sheet = hssfWorkbook.createSheet("map");
            HSSFRow row;
            row = sheet.createRow(60);
            HSSFCell cell =row.createCell(0);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            ImageIO.write(bufferedImage,"png",outputStream);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,100,(short) 0,0,(short) 15,44);
            patriarch.createPicture(anchor,hssfWorkbook.addPicture(outputStream.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));
        }


        try {
            this.setResponseHeader(response,fileName);
            OutputStream os = response.getOutputStream();
            hssfWorkbook.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response,String fileName){
        try {
            try {
                fileName = new String(fileName.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //解码图片
    public boolean chartDecode(String chart ,String path){
        if (chart == null || chart.equals("")){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            String[] url = chart.split(",");
            String u = url[1];

            byte[] buffer = decoder.decodeBuffer(u);
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
