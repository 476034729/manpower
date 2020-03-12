package org.jeecgframework.web.hrm.service.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.bean.WorkHourCM;
import org.jeecgframework.web.hrm.service.HrStatisticsService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class HrStatisticsServiceImpl extends CommonServiceImpl implements HrStatisticsService {
    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(HrStatisticsServiceImpl.class);
    @Override
    public List<ProjectCode> getProjectList() {
        String hql="from ProjectCode u where 1=1 and u.status = '执行中' order by CONVERT(u.rank,SIGNED) asc";
        return this.findHql(hql);
    }

    @Override
    public List<TSUser> getUserByWorkHour(int year, int month) {

        logger.info(">>HrStatisticsServiceImpl:getUserByWorkHour:");

        //根据年月获取工时
        String hql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =? order by u.user_id";
        List<WorkHour> list=this.findHql(hql,year,month);

        Set<String> stringSet = new HashSet<String>();
        Set<TSUser> otherUserSet=new HashSet<TSUser>();

        List<TSUser> createUser = new ArrayList<TSUser>();
        List<TSUser> testUser = new ArrayList<TSUser>();
        List<TSUser> sysUser = new ArrayList<TSUser>();

        for (WorkHour workHour : list){
            stringSet.add(workHour.getUser_id());
        }

        //获取全部jobnum不为空的员工
        String hqll="from TSUser u where  u.jobNum != ' ' and serialNum!='' order by serialNum";
        List<TSUser> userList=this.findHql(hqll);
        for (String s :stringSet){
            //根据ID查询员工
            List<TSUser> users=new ArrayList<TSUser>();
            for(TSUser user : userList){
                if(s.equals(user.getId())){
                    users.add(user);
                }
            }

            if (users.size()>0){
                if ("开发部".equals(users.get(0).getDepartment())){
                    createUser.add(users.get(0));
                }else if ("测试部".equals(users.get(0).getDepartment())){
                    testUser.add(users.get(0));
                }else if ("系统部".equals(users.get(0).getDepartment())){
                    sysUser.add(users.get(0));
                }else {
                    otherUserSet.add(users.get(0));
                }
            }
        }

        List<TSUser> usersAll=new ArrayList<TSUser>();

        usersAll.addAll(createUser);
        usersAll.addAll(testUser);
        usersAll.addAll(sysUser);
        usersAll.addAll(otherUserSet);
        logger.info(">>HrStatisticsServiceImpl:getTimeOfCode:"+usersAll.size());

        Collections.sort(usersAll, new Comparator<TSUser>() {
            @Override
            public int compare(TSUser o1, TSUser o2) {

                int diff = Integer.parseInt(o1.getSerialNum()) - Integer.parseInt(o2.getSerialNum());
                if (diff > 0){
                    return 1;
                }else if (diff < 0){
                    return -1;
                }
                return 0;
            }
        });
        return usersAll;

    }



    @Override
    public Map<String,Map<String,Object>> getTimeOfCode(int year, int month,List<TSUser> users) {
        //根据年月获取workhour
        String hql="from WorkHour u where  YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHour> list = this.findHql(hql,year,month);

        //获取在执行中的项目
        String hqll="from ProjectCode u where 1=1 and u.status = '执行中'";
        List<ProjectCode> codeList = this.findHql(hqll);

        logger.info(">>HrStatisticsServiceImpl:getTimeOfCode:");

        //分别获取am和pm的工时 workhour
        String amropmhql="from WorkHour u where YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.amOrPm = ?";
        List<WorkHour> amList = this.findHql(amropmhql,year,month,"am");
        List<WorkHour> pmList = this.findHql(amropmhql,year,month,"pm");

        //根据年月获取日期
        String dateHql = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? order by u.work_date";
        List<HrmDate> dateList = this.findHql(dateHql,year,month);
        Map<Date,String> dateStringMap = new HashMap<Date, String>();
        for (HrmDate date : dateList){
            dateStringMap.put(date.getWork_date(),date.getStatus());
        }


        //获取工作日的天数
        String dateHqll = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? and u.status = '0' order by u.work_date";
        int workday = this.findHql(dateHqll,year,month).size();

        int amWorkDay = 0;
        int pmWorkDay = 0;
        int amLeave = 0;
        int pmLeave = 0;
        int amVacation = 0;
        int pmVacation = 0;

        Map<String,Map<String,Object>> map= new LinkedHashMap<String, Map<String, Object>>();

        for (TSUser user:users){
            //根据员工Id获取每个员工年月的工时
            List<WorkHour> workHours=new ArrayList<WorkHour>();
            for (WorkHour workHour:list){
                if(user.getId().equals(workHour.getUser_id())){
                    workHours.add(workHour);
                }
            }
            //根据员工Id获取每个员工年月的上午的工时
            List<WorkHour> amworkHours=new ArrayList<WorkHour>();
            for (WorkHour workHour:amList){
                if(user.getId().equals(workHour.getUser_id())){
                    amworkHours.add(workHour);
                }
            }

            //根据员工获取每个员工年月下午的工时
            List<WorkHour> pmworkHours=new ArrayList<WorkHour>();
            for (WorkHour workHour:pmList){
                if(user.getId().equals(workHour.getUser_id())){
                    pmworkHours.add(workHour);
                }
            }
            Map<String,Object> mapCount = new HashMap<String, Object>(16);

            for (ProjectCode code : codeList){
                int amCount = 0;
                int pmCount = 0;
                for (WorkHour w :workHours){
                    if (code.getCode().equals(w.getProject_code())){
                        if ("am".equals(w.getAmOrPm())){
                            amCount++;
                        }else if ("pm".equals(w.getAmOrPm())){
                            pmCount++;
                        }
                    }
                }
                mapCount.put("am"+code.getCode(),amCount);
                mapCount.put("pm"+code.getCode(),pmCount);

            }

            //计算工作日,请假日,假期日
            mapCount.put("amRate",sumworkhour(amworkHours,dateStringMap,amLeave,amVacation,amWorkDay,workday));
            mapCount.put("pmRate",sumworkhour(pmworkHours,dateStringMap,pmLeave,pmVacation,pmWorkDay,workday));
            mapCount.put("serialNum",user.getSerialNum());
            map.put(user.getUserName(),mapCount);
        }
        logger.info(">>HrStatisticsServiceImpl:getTimeOfCode:"+map.size());
        return map;
    }

    /**
     * 计算工作日,请假日,假期日
     * @param workhours
     * @param dateStringMap
     * @param leave
     * @param vacation
     * @param workDay
     */
    private double sumworkhour(List<WorkHour> workhours,Map<Date,String> dateStringMap,int leave,int vacation,int workDay,int workday){
        for(WorkHour workHour :workhours){
            if (workHour.getProject_code()!=null &&(!workHour.getProject_code().equals(" ")) && (!workHour.getProject_code().equals("请选择项目编码"))){
                if (workHour.getProject_code().equals("L")){
                    leave++;
                }else if ("O".equals(workHour.getProject_code())){
                    vacation++;
                }
                else if ("0".equals(dateStringMap.get(workHour.getWork_day()))){
                    workDay++;
                }
            }
        }
        //针对被减数进行校验并处理
        int minuend =workday - leave;
        double rate =0.0;
        if(minuend > 0){
            rate=workDay  * 100/minuend;
        }else {
            rate=workDay  * 100;
        }
        return rate;
    }


//    @Override
//    public Map<String, Object> getTimeOfCode(int year, int month, String id) {
//        String hql="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
//        String hqll="from ProjectCode u where 1=1 and u.status = '执行中'";
//        logger.info(">>HrStatisticsServiceImpl:getTimeOfCode:");
//        List<WorkHour> list = this.findHql(hql,id,year,month);
//        List<ProjectCode> codeList = this.findHql(hqll);
//        Map<String,Object> map = new HashMap<String, Object>(16);
//        for (ProjectCode code : codeList){
//            int amCount = 0;
//            int pmCount = 0;
//            for (WorkHour w :list){
//                if (code.getCode().equals(w.getProject_code())){
//                    if ("am".equals(w.getAmOrPm())){
//                        amCount++;
//                    }else if ("pm".equals(w.getAmOrPm())){
//                        pmCount++;
//                    }
//                }
//            }
//            map.put("am"+code.getCode(),amCount);
//            map.put("pm"+code.getCode(),pmCount);
//        }
//        return map;
//    }

//    @Override
//    public Map<String, Object> getRateOfAttendance(int year, int month, String id) {
//        String hql="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.amOrPm = ?";
//        List<WorkHour> amList = this.findHql(hql,id,year,month,"am");
//        List<WorkHour> pmList = this.findHql(hql,id,year,month,"pm");
//
//        String dateHql = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? order by u.work_date";
//        List<HrmDate> dateList = this.findHql(dateHql,year,month);
//        Map<Date,String> dateStringMap = new HashMap<Date, String>();
//        for (HrmDate date : dateList){
//            dateStringMap.put(date.getWork_date(),date.getStatus());
//        }
//
//        Map<String,Object> map = new HashMap<String, Object>();
//        String dateHqll = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? and u.status = '0' order by u.work_date";
//        int workDay = this.findHql(dateHqll,year,month).size();
//        int amWorkDay = 0;
//        int pmWorkDay = 0;
//        int amLeave = 0;
//        int pmLeave = 0;
//        int amVacation = 0;
//        int pmVacation = 0;
//        for(WorkHour workHour :amList){
//            if (workHour.getProject_code()!=null &&(!workHour.getProject_code().equals(" ")) && (!workHour.getProject_code().equals("请选择项目编码"))){
//                if (workHour.getProject_code().equals("L")){
//                    amLeave++;
//                }else if ("O".equals(workHour.getProject_code())){
//                    amVacation++;
//                }
//                else if ("0".equals(dateStringMap.get(workHour.getWork_day()))){
//                    amWorkDay++;
//                }
//            }
//        }
//
//        for(WorkHour workHour :pmList){
//            if (workHour.getProject_code()!=null &&(!workHour.getProject_code().equals(" ")) && (!workHour.getProject_code().equals("请选择项目编码"))){
//                if (workHour.getProject_code().equals("L")){
//                    pmLeave++; //包括请假
//                }else if ("O".equals(workHour.getProject_code())){
//                    pmVacation++;
//                }
//                else if ("0".equals(dateStringMap.get(workHour.getWork_day()))){
//                    pmWorkDay++;
//                }
//            }
//        }
//        //针对被减数进行校验并处理
//        int amMinuend =workDay - amLeave;
//        double amRate =0.0;
//        if(amMinuend > 0){
//            amRate=amWorkDay  * 100/amMinuend;
//        }else {
//            amRate=amWorkDay  * 100;
//        }
//        int pmMinuend =workDay - pmLeave;
//        double pmRate =0.0;
//        if(pmMinuend > 0){
//            pmRate = pmWorkDay * 100/pmRate;
//        }else{
//            pmRate = pmWorkDay * 100;
//        }
//
//        map.put("amRate",amRate);
//        map.put("pmRate",pmRate);
//        return map;
//    }


    //计算项目人日量
    @Override
    public Map<String, Object> getCodePeopleTimes(int year, int month) {
        String hql_up="from WorkHour u where YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        String hqll="from ProjectCode u where 1=1 and u.status = '执行中'";
        Map<String,Object> map = new HashMap<String, Object>(16);
        List<ProjectCode> codeList = this.findHql(hqll);
        List<WorkHour> list = this.findHql(hql_up,year,month);//全部
        for (ProjectCode projectCode :codeList){
            List<WorkHour> hourslist=new ArrayList<WorkHour>();//项目投入
            //根据项目code查询工时
            for (WorkHour hourwork: list) {
                if(projectCode.getCode().equals(hourwork.getProject_code())){
                    hourslist.add(hourwork);
                }
            }
            double size = hourslist.size();
            String rate = String.format("%.2f",size/2.0);
            map.put(projectCode.getCode(), rate);
        }
        return map;
    }

    //计算项目人日百分比
    @Override
    public Map<String, Object> getProjectPeopleDayRate(int year, int month) {
        String hql="from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        String codeHql="from ProjectCode u where 1=1 and u.status = '执行中'";
        String dateHql = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? order by u.work_date";
        List<HrmDate> dateList = this.findHql(dateHql,year,month);//日期
        Map<Date,String> dateStringMap = new HashMap<Date, String>();
        for (HrmDate date : dateList){
            dateStringMap.put(date.getWork_date(),date.getStatus());
        }
        List<WorkHour> list = this.findHql(hql,year,month);//全部
        Map<String,Object> map = new HashMap<String, Object>();
        List<ProjectCode> codeList = this.findHql(codeHql);//项目code
        double total = (double) list.size();
        double total1 = (double)list.size();
        logger.info(total);
        logger.info(total1);
        if (list.size() == 0){
            return null;
        }
        for(WorkHour workHour :list){
            if ("星期日".equals(DateUtil.Weekday(workHour.getWork_day().toString())) || "星期六".equals(DateUtil.Weekday(workHour.getWork_day().toString()))){
                total = total -1;
            }else if (workHour.getProject_code()!=null &&(!" ".equals(workHour.getProject_code())) && (!"请选择项目编码".equals(workHour.getProject_code()))){
                if ("L".equals(workHour.getProject_code())){
                    total = total -1 ;
                }
            }
        }


        for(WorkHour workHour :list){
            if (!"0".equals(dateStringMap.get(workHour.getWork_day()))){
                total1 = total1 -1;
                logger.info("非节假日"+" " +total1);
            }else if (workHour.getProject_code()!=null &&(!" ".equals(workHour.getProject_code())) && (!"请选择项目编码".equals(workHour.getProject_code()))){
                if ("L".equals(workHour.getProject_code())){
//                    total1 = total1 -1 ;
                    logger.info("离职的  "+ total1);
                }
            }
        }
        logger.info(total);
        logger.info(total1);

        for (ProjectCode projectCode :codeList){

            List<WorkHour> listhourwork=new ArrayList<WorkHour>();
            //根据项目code查询工时
            for (WorkHour hourwork: list) {
                if(projectCode.getCode().equals(hourwork.getProject_code())){
                    listhourwork.add(hourwork);
                }
            }
            String rate = String.format("%.2f",(double)listhourwork.size()*100/total1);
            map.put(projectCode.getCode(),rate);
        }
        return map;
    }

    @Override
    public boolean hasRecord(int year, int month) {
        String hql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHour> list = this.findHql(hql,year,month);
        return (list.size() > 0);
    }

    @Override
    public HSSFWorkbook getHSSFWorkbook(String sheetName, List<String> projectList, List<String> rateList) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);
        HSSFRow rowOne = sheet.createRow(0);
        HSSFCell cell;
        for (int i=0;i<projectList.size();i++){
            cell = rowOne.createCell(i);
            cell.setCellValue(projectList.get(i));
        }


        HSSFRow rowTwo = sheet.createRow(1);
        for (int i =0;i<rateList.size();i++){
            cell = rowTwo.createCell(i);
            cell.setCellValue(rateList.get(i)+"%");
        }
        return hssfWorkbook;
    }
}
