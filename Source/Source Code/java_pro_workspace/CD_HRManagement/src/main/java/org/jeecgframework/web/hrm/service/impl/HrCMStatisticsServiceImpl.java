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
import org.jeecgframework.web.hrm.service.HrCMStatisticsService;
import org.jeecgframework.web.hrm.service.HrStatisticsService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class HrCMStatisticsServiceImpl extends CommonServiceImpl implements HrCMStatisticsService {
    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(HrCMStatisticsServiceImpl.class);
    @Override
    public List<ProjectCode> getProjectList() {
        String hql="from ProjectCode u where 1=1 and u.status = '执行中' order by CONVERT(u.rank,SIGNED) asc";
        return this.findHql(hql);
    }

    @Override
    public List<TSUser> getUserByWorkHour(int year, int month) {

        logger.info(">>HrStatisticsServiceImplCM:getUserByWorkHour:");

        //根据年月获取工时
        String hql = "from WorkHourCM u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =? order by u.user_id";
        List<WorkHourCM> list=this.findHql(hql,year,month);

        Set<String> stringSet = new HashSet<String>();
        Set<TSUser> otherUserSet=new HashSet<TSUser>();

        List<TSUser> createUser = new ArrayList<TSUser>();
        List<TSUser> testUser = new ArrayList<TSUser>();
        List<TSUser> sysUser = new ArrayList<TSUser>();

        for (WorkHourCM workHourcm : list){
            stringSet.add(workHourcm.getUser_id());
        }

        //获取全部jobnum不为空的员工
        String hqll="from TSUser u where  u.jobNum != ' ' and serialNum != '' order by serialNum";
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
        logger.info(">>HrStatisticsServiceImplCM:getTimeOfCode:"+usersAll.size());

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
    public Map<String,Map<String,Object>> getTimeOfCodeOrderBySerialNum(int year, int month,List<TSUser> users) {
        //根据年月获取workhourCM
        String hql = "from WorkHourCM u where  YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHourCM> list = this.findHql(hql, year, month);

        //获取在执行中的项目
        String hqll = "from ProjectCode u where 1=1 and u.status = '执行中'";
        List<ProjectCode> codeList = this.findHql(hqll);

        logger.info(">>HrCMStatisticsServiceImpl:getTimeOfCode:");

        //分别获取am和pm的工时 workhour
        String amropmhql = "from WorkHourCM u where YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.amOrPm = ?";
        List<WorkHourCM> amList = this.findHql(amropmhql, year, month, "am");
        List<WorkHourCM> pmList = this.findHql(amropmhql, year, month, "pm");

        //根据年月获取日期
        String dateHql = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? order by u.work_date";
        List<HrmDate> dateList = this.findHql(dateHql, year, month);
        Map<Date, String> dateStringMap = new HashMap<Date, String>();
        for (HrmDate date : dateList) {
            dateStringMap.put(date.getWork_date(), date.getStatus());
        }


        //获取工作日的天数
        String dateHqll = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? and u.status = '0' order by u.work_date";
        int workday = this.findHql(dateHqll, year, month).size();

        int amWorkDay = 0;
        int pmWorkDay = 0;
        int amLeave = 0;
        int pmLeave = 0;
        int amVacation = 0;
        int pmVacation = 0;

        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();

            for (TSUser user : users) {
                //根据员工Id获取每个员工年月的工时
                List<WorkHourCM> workHours = new ArrayList<WorkHourCM>();
                for (WorkHourCM workHour : list) {
                    if (user.getId().equals(workHour.getUser_id())) {
                        workHours.add(workHour);
                    }
                }
                //根据员工Id获取每个员工年月的上午的工时
                List<WorkHourCM> amworkHours = new ArrayList<WorkHourCM>();
                for (WorkHourCM workHour : amList) {
                    if (user.getId().equals(workHour.getUser_id())) {
                        amworkHours.add(workHour);
                    }
                }

                //根据员工获取每个员工年月下午的工时
                List<WorkHourCM> pmworkHours = new ArrayList<WorkHourCM>();
                for (WorkHourCM workHour : pmList) {
                    if (user.getId().equals(workHour.getUser_id())) {
                        pmworkHours.add(workHour);
                    }
                }
                Map<String, Object> mapCount = new HashMap<String, Object>(16);

                for (ProjectCode code : codeList) {
                    int amCount = 0;
                    int pmCount = 0;
                    for (WorkHourCM w : workHours) {
                        if (code.getCode().equals(w.getProject_code())) {
                            if ("am".equals(w.getAmOrPm())) {
                                amCount++;
                            } else if ("pm".equals(w.getAmOrPm())) {
                                pmCount++;
                            }
                        }
                    }
                    float totalCount =(float)(amCount + pmCount)/2;
//                    logger.info(totalCount);
                    mapCount.put("total" + code.getCode(), totalCount);


                }

                //计算工作日,请假日,假期日
                double amsumworkhour = sumworkhour(amworkHours, dateStringMap, amLeave, amVacation, amWorkDay, workday);
                double pmsumworkhour = sumworkhour(pmworkHours, dateStringMap, pmLeave, pmVacation, pmWorkDay, workday);
                double totalSumworkHour = (amsumworkhour + pmsumworkhour) / 2;
                mapCount.put("totalRate", totalSumworkHour);
                mapCount.put("serialNum", user.getSerialNum());
                map.put(user.getUserName(), mapCount);
            }
            logger.info(">>HrCMStatisticsServiceImpl:getTimeOfCode:" + map.size());
            return map;

    }


    public Map<String,Map<String,Object>> getTimeOfCode(int year, int month,List<TSUser> users) {
        //根据年月获取workhourCM
        String hql="from WorkHourCM u where  YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHourCM> list = this.findHql(hql,year,month);

        //获取在执行中的项目
        String hqll="from ProjectCode u where 1=1 and u.status = '执行中'";
        List<ProjectCode> codeList = this.findHql(hqll);

        logger.info(">>HrCMStatisticsServiceImpl:getTimeOfCode:");

        //分别获取am和pm的工时 workhour
        String amropmhql="from WorkHourCM u where YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.amOrPm = ?";
        List<WorkHourCM> amList = this.findHql(amropmhql,year,month,"am");
        List<WorkHourCM> pmList = this.findHql(amropmhql,year,month,"pm");

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
            List<WorkHourCM> workHours=new ArrayList<WorkHourCM>();
            for (WorkHourCM workHour:list){
                if(user.getId().equals(workHour.getUser_id())){
                    workHours.add(workHour);
                }
            }
            //根据员工Id获取每个员工年月的上午的工时
            List<WorkHourCM> amworkHours=new ArrayList<WorkHourCM>();
            for (WorkHourCM workHour:amList){
                if(user.getId().equals(workHour.getUser_id())){
                    amworkHours.add(workHour);
                }
            }

            //根据员工获取每个员工年月下午的工时
            List<WorkHourCM> pmworkHours=new ArrayList<WorkHourCM>();
            for (WorkHourCM workHour:pmList){
                if(user.getId().equals(workHour.getUser_id())){
                    pmworkHours.add(workHour);
                }
            }
            Map<String,Object> mapCount = new HashMap<String, Object>(16);

            for (ProjectCode code : codeList){
                int amCount = 0;
                int pmCount = 0;
                for (WorkHourCM w :workHours){
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
        logger.info(">>HrCMStatisticsServiceImpl:getTimeOfCode:"+map.size());
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
    private double sumworkhour(List<WorkHourCM> workhours,Map<Date,String> dateStringMap,int leave,int vacation,int workDay,int workday){
        for(WorkHourCM workHour :workhours){
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


    //计算项目人日量
    @Override
    public Map<String, Object> getCodePeopleTimes(int year, int month) {


        String hql_up="from WorkHourCM u where  YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        String hqll="from ProjectCode u where 1=1 and u.status = '执行中'";
        Map<String,Object> map = new HashMap<String, Object>(16);
        List<ProjectCode> codeList = this.findHql(hqll);
        List<WorkHourCM> list = this.findHql(hql_up,year,month);
        for (ProjectCode projectCode :codeList){
            List<WorkHourCM> worklist=new ArrayList<WorkHourCM>();
            //根据项目Code查询工时
            for (WorkHourCM hourwork: list) {
                if(projectCode.getCode().equals(hourwork.getProject_code())){
                    worklist.add(hourwork);
                }
            }
            double size = worklist.size();
            String rate = String.format("%.2f",size/2.0);
            map.put(projectCode.getCode(), rate);
            worklist.clear();
        }
        return map;
    }

    //计算项目人日百分比
    @Override
    public Map<String, Object> getProjectPeopleDayRate(int year, int month) {


        String hql="from WorkHourCM u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        String codeHql="from ProjectCode u where 1=1 and u.status = '执行中'";
        String dateHql = " from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date) =? order by u.work_date";
        List<HrmDate> dateList = this.findHql(dateHql,year,month);
        Map<Date,String> dateStringMap = new HashMap<Date, String>();
        for (HrmDate date : dateList){
            dateStringMap.put(date.getWork_date(),date.getStatus());
        }
        List<WorkHourCM> list = this.findHql(hql,year,month);
        Map<String,Object> map = new HashMap<String, Object>();
        List<ProjectCode> codeList = this.findHql(codeHql);
        double total = (double) list.size();
        double total1 = (double)list.size();
        if (list.size() == 0){
            return null;
        }
        for(WorkHourCM workHour :list){
            if ("星期日".equals(DateUtil.Weekday(workHour.getWork_day().toString())) || "星期六".equals(DateUtil.Weekday(workHour.getWork_day().toString()))){
                total = total -1;
            }else if (workHour.getProject_code()!=null &&(!" ".equals(workHour.getProject_code())) && (!"请选择项目编码".equals(workHour.getProject_code()))){
                if ("L".equals(workHour.getProject_code())){
                    total = total -1 ;
                }
            }
        }

        for(WorkHourCM workHour :list){
            if (!"0".equals(dateStringMap.get(workHour.getWork_day()))){
                total1 = total1 -1;
            }else if (workHour.getProject_code()!=null &&(!" ".equals(workHour.getProject_code())) && (!"请选择项目编码".equals(workHour.getProject_code()))){
                if ("L".equals(workHour.getProject_code())){
                    total1 = total1 -1 ;
                }
            }
        }


        for (ProjectCode projectCode :codeList){

            List<WorkHourCM> worklists=new ArrayList<WorkHourCM>();
            //根据项目Code查询工时
            for (WorkHourCM hourwork: list) {
                if(projectCode.getCode().equals(hourwork.getProject_code())){
                    worklists.add(hourwork);
                }
            }

            String rate = String.format("%.2f",(double)worklists.size()*100/total1);
            map.put(projectCode.getCode(),rate);
            worklists.clear();
        }
        return map;
    }

    @Override
    public boolean hasRecord(int year, int month) {
        String hql = "from WorkHourCM u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHourCM> list = this.findHql(hql,year,month);
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
