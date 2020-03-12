package org.jeecgframework.web.hrm.controller;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.IOUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/attendance")
public class HrmAttendanceController {

	
    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "attendanceDemo")
    public String attendanceDemo(HttpServletRequest request){
    	//插入当月考勤记录的数据。
    	attendanceRecordService.insertAttendanceRecordAuto();
        return "hrm/attendance/attendanceRecord";
    }

    /**
     * 年假统计
     * @return
     */
    @RequestMapping(params = "goannualvacation")
    public ModelAndView goAnnualVacation(){
        List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = attendanceRecordService.findAllAnnualLeaveStatistics();
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/annualvacationtotal");
        modelAndView.addObject("annualLeaveStatisticsList",hrmAnnualLeaveStatisticsList);
        return modelAndView;
    }

    @RequestMapping(params = "goovertimeapply")
    public ModelAndView overtimeApply(HttpSession session,String overtimeApplyId){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());

        //是否为负责人0、否1、是
        Integer isResponsPerson = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("管理员".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
        }
        HrmOvertimeApply hrmOvertimeApply = new HrmOvertimeApply();
        if(overtimeApplyId!=null){
            hrmOvertimeApply = attendanceRecordService.getOvertimeApplyById(overtimeApplyId);
        }

        //格式化加班时间，用于修改页面展现
        HrmDateFormat oneDateFormat = new HrmDateFormat();
        List<HrmDateFormat> dateFormats = new ArrayList<HrmDateFormat>();
        if(hrmOvertimeApply!=null){
            if(hrmOvertimeApply.getHrmOvertimes()!=null){
                List<HrmOvertime> hrmOvertimes = hrmOvertimeApply.getHrmOvertimes();
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startTime = hrmOvertimes.get(0).getStart_time();
                Date endTime = hrmOvertimes.get(0).getEnd_time();
                Double count_time = hrmOvertimes.get(0).getCount_time();
                Double surplus_time = hrmOvertimes.get(0).getSurplus_time();
                oneDateFormat.setStart_time(sdf.format(startTime).replace(" ","T"));
                oneDateFormat.setEnd_time(sdf.format(endTime).replace(" ","T"));
                oneDateFormat.setCount_time(count_time);
                oneDateFormat.setSurplus_time(surplus_time);
                for(int i=1;i<hrmOvertimes.size();i++){
                    HrmDateFormat hrmDateFormat = new HrmDateFormat();
                    Date startTime1 = hrmOvertimes.get(i).getStart_time();
                    Date endTime1 = hrmOvertimes.get(i).getEnd_time();
                    Double count_time1 = hrmOvertimes.get(i).getCount_time();
                    Double surplus_time1 = hrmOvertimes.get(i).getSurplus_time();
                    hrmDateFormat.setStart_time(sdf.format(startTime1).replace(" ","T"));
                    hrmDateFormat.setEnd_time(sdf.format(endTime1).replace(" ","T"));
                    hrmDateFormat.setCount_time(count_time1);
                    hrmDateFormat.setSurplus_time(surplus_time1);
                    dateFormats.add(hrmDateFormat);
                }
            }
        }
        Calendar calendar=Calendar.getInstance();
        Integer month=calendar.get(Calendar.MONTH)+1;
        Double sumMonthOvertime = attendanceRecordService.getSunMonthOvertime(user.getId(),month);
        if(sumMonthOvertime==null){
            sumMonthOvertime = 0.0;
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/overtimeapply");
        modelAndView.addObject("user",user);
        modelAndView.addObject("sumMonthOverTime",sumMonthOvertime);
        modelAndView.addObject("isResponsPerson",isResponsPerson);
        modelAndView.addObject("overtimeApply",hrmOvertimeApply);
        modelAndView.addObject("oneDateFormat",oneDateFormat);
        modelAndView.addObject("dateFormats",dateFormats);
        return modelAndView;
    }

    @RequestMapping(params = "govacateapply")
    public ModelAndView vacateApply(){
        return new ModelAndView("hrm/attendance/vacateapply");
    }

    @RequestMapping(params = "govacationprocess")
    public ModelAndView vacationProcess(){
        return new ModelAndView("hrm/attendance/vacationprocess");
    }

    @RequestMapping(params = "gochecklist")
    public ModelAndView checkList(){
        return new ModelAndView("hrm/attendance/checklist");
    }

    @RequestMapping(params = "getDate")
    @ResponseBody
    public AjaxJson getDate(HttpServletRequest request){
        AjaxJson json=new AjaxJson();
        Integer year=Integer.parseInt(request.getParameter("year"));
        Integer month=Integer.parseInt(request.getParameter("month"));
        DateUtil util=new DateUtil();
        json.setObj(util.getDate(year,month));
        return json;
    }

    @RequestMapping(params = "getAttendanceRecords")
    @ResponseBody
    public AjaxJson getAttendanceRecords(HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        String chooseTime=request.getParameter("time");
        List<AttendanceRecordResult> attendanceRecordResults=attendanceRecordService.getAllAttendanceRecords(chooseTime);
        ajaxJson.setObj(attendanceRecordResults);
        return ajaxJson;
    }

    @RequestMapping(params = "userinfo")
    @ResponseBody
    public AjaxJson getUserInfo(HttpSession session){
        AjaxJson a = new AjaxJson();
        Client client = ClientManager.getInstance().getClient(session.getId());
        a.setObj(client.getUser());
        return a;
    }

    /**
     * 加班申请保存
     * @param hrmOvertimeApply
     * @param request
     * @param session
     * @param
     * @returnoneDateFormat
     */
    @RequestMapping(params = "saveOvertimeApply",method = RequestMethod.POST)
    public String saveOvertimeApply(HrmOvertimeApply hrmOvertimeApply,HttpServletRequest request,HttpSession session,Integer copyType,Integer immediateSupervisorExamine){
        String[] oneStart = request.getParameterValues("oneStart");
        String[] oneEnd = request.getParameterValues("oneEnd");
        String[] countTime = request.getParameterValues("countTime");
        String[] surplusTime = request.getParameterValues("surplusTime");
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        try {
            attendanceRecordService.saveOvertimeApply(hrmOvertimeApply,oneStart,oneEnd,countTime,surplusTime,user,copyType,immediateSupervisorExamine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:attendance.do?overTimeApplyListPageJump";
    }

    /**
     * 加班查询页面跳转
     * @return
     *
     */
    @RequestMapping(params = "overTimeApplyListPageJump",method = RequestMethod.GET)
    public ModelAndView overTimeApplyListPageJump(HttpSession session){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //是否为负责人0、否1、是
        Integer isResponsPerson = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 2;
                break;
            }/*
            if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }*/
            if ("行政".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 3;
                break;
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/overtimeList");
        modelAndView.addObject("isResponsPerson",isResponsPerson);
        modelAndView.addObject("user",user);
        return modelAndView;
    }



    /**
     * 加班申请查询
     * @return
     */
    @RequestMapping(params = "queryOvertime")
    @ResponseBody
    public List<HrmOvertimeApply> queryOvertime(HttpSession session, Integer status, String userName, Date startDate,Date endDate){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //是否为负责人0、普通员工1、部门负责人2、部门经理3、行政
        Integer roleType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                roleType = 2;
                break;
            }
            if("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
            }
            /*if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("管理员".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = true;
                break;
            }*/
         }
         List<HrmOvertimeApply> overtimeApplyList = attendanceRecordService.findOvertimeApplyList(user,roleType,status,userName,startDate,endDate);
         return overtimeApplyList;
    }

    /**
     * 加班申请详情
     * @param overtimeApplyId
     * @return
     */
    @RequestMapping(params = "overtimeApplyDetail",method = RequestMethod.GET)
    public ModelAndView overtimeApplyDetail(String overtimeApplyId){
        HrmOvertimeApply hrmOvertimeApply = attendanceRecordService.getOvertimeApplyById(overtimeApplyId);
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/overtimeapplyDetial");
        modelAndView.addObject("overtimeApply",hrmOvertimeApply);
        return  modelAndView;
    }

    /**
     * 审批页面跳转
     * @param overtimeApplyId
     * @param session
     * @return
     */
    @RequestMapping(params = "overtimeApplyExamination",method = RequestMethod.GET)
    public ModelAndView overtimeApplyExamination(String overtimeApplyId,HttpSession session) {
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //判断登录人员的角色类型1、部门负责人2、部门经理3、行政
        Integer type = null;
        for (TSRoleUser roleUser : rUsers) {
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())) {
                type = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())) {
                type = 2;
                break;
            }
            if("行政".equals(roleUser.getTSRole().getRoleName())){
                type = 3;
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/overtimeapplyExamination");
        HrmOvertimeApply hrmOvertimeApply = attendanceRecordService.getOvertimeApplyById(overtimeApplyId);
        modelAndView.addObject("type",type);
        modelAndView.addObject("overtimeApply",hrmOvertimeApply);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    /**
     * 加班申请审批保存
     * @return
     */
    @RequestMapping(params = "saveOvertimeApplyExamination",method = RequestMethod.POST)
    public String saveOvertimeApplyExamination(String overtimeApplyId,Integer roleType,Integer immediateSupervisorExamine,String immediateSupervisorSign,Integer copyType,String personnelMattersName,
                                                Integer divisionManagerExamine,String divisionManagerSign,Integer personnelMattersStatus,String adminName){
        attendanceRecordService.saveOvertimeApplyExamination(overtimeApplyId,roleType,immediateSupervisorExamine,immediateSupervisorSign,copyType,
                personnelMattersName,divisionManagerExamine,divisionManagerSign,personnelMattersStatus,adminName);
        return "redirect:attendance.do?overTimeApplyListPageJump";
    }

    /**
     * 用户加班申请确认
     * @param overtimeApplyId
     * @return
     */
    @RequestMapping(params = "userConfirm")
    @ResponseBody
    public AjaxJson userConfirm(String overtimeApplyId){
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveUserConfirm(overtimeApplyId);
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }

    /**
     * 人事加班关闭
     * @param overtimeApplyId
     * @return
     */
    @RequestMapping(params = "adminConfirm")
    @ResponseBody
    public AjaxJson adminConfirm(String overtimeApplyId){
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveAdminConfirm(overtimeApplyId);
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }

    /**
     * 换休申请页面跳转
     * @return
     */
    @RequestMapping(params = "vacationApply",method = RequestMethod.GET)
    public ModelAndView vacationApply(HttpSession session,String changeofrestId){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //是否为负责人0、否1、是
        Integer userType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                userType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                userType = 2;
                break;
            }/*
            if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }*/
        }

        HrmChangeOfRestApply hrmChangeOfRestApply = new HrmChangeOfRestApply();
        if(changeofrestId!=null){
            hrmChangeOfRestApply = attendanceRecordService.getVacationApplyById(changeofrestId);
        }
        //格式化加班时间，用于修改页面展现
        HrmDateFormat oneDateFormat = new HrmDateFormat();
        List<HrmDateFormat> dateFormats = new ArrayList<HrmDateFormat>();
        if(hrmChangeOfRestApply!=null){
            if(hrmChangeOfRestApply.getHrmChangofrestTimes()!=null){
                List<HrmChangofrestTime> hrmChangofrestTimes = hrmChangeOfRestApply.getHrmChangofrestTimes();
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startTime = hrmChangofrestTimes.get(0).getStart_time();
                Date endTime = hrmChangofrestTimes.get(0).getEnd_time();
                oneDateFormat.setStart_time(sdf.format(startTime).replace(" ","T"));
                oneDateFormat.setEnd_time(sdf.format(endTime).replace(" ","T"));
                for(int i=1;i<hrmChangofrestTimes.size();i++){
                    HrmDateFormat hrmDateFormat = new HrmDateFormat();
                    Date startTime1 = hrmChangofrestTimes.get(i).getStart_time();
                    Date endTime1 = hrmChangofrestTimes.get(i).getEnd_time();
                    hrmDateFormat.setStart_time(sdf.format(startTime1).replace(" ","T"));
                    hrmDateFormat.setEnd_time(sdf.format(endTime1).replace(" ","T"));
                    dateFormats.add(hrmDateFormat);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/vacationapply");
        modelAndView.addObject("user",user);
        modelAndView.addObject("userType",userType);
        modelAndView.addObject("oneDateFormat",oneDateFormat);
        modelAndView.addObject("dateFormats",dateFormats);
        modelAndView.addObject("changeOfRestApply",hrmChangeOfRestApply);
        return modelAndView;
    }

    /**
     * 查询当月可换休小时
     * @param userId
     * @return
     */
    @RequestMapping(params = "breakOff")
    @ResponseBody
    public AjaxJson breakOff(String userId,String[] oneStart){
        Double countTime = attendanceRecordService.countBreakOff(userId,oneStart);
        if(countTime == null){
            countTime = 0.0;
        }
        AjaxJson ajaxJson = new AjaxJson();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("countTime",countTime);
        ajaxJson.setAttributes(map);
        return  ajaxJson;
    }

    /**
     * 查询用户的年假天数
     * @param
     * @return
     */
    @RequestMapping(params = "annualLeave")
    @ResponseBody
    public AjaxJson annualLeave(HttpSession session){
    	 //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();
        String  userId = user.getId();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Double countDays = attendanceRecordService.countAnnualLeave(userId,year);
        AjaxJson ajaxJson = new AjaxJson();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("countDays",countDays);
        ajaxJson.setAttributes(map);
        return ajaxJson;
    }

    /**
     * 查询用户的病假剩余天数
     * @param
     * @return
     */
    @RequestMapping(params = "sickLeave")
    @ResponseBody
    public AjaxJson sickLeave(HttpSession session){
    	 //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();
        String  userId = user.getId();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Double countDays = attendanceRecordService.countSickLeave(userId,year);
        AjaxJson ajaxJson = new AjaxJson();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("countDays",countDays);
        ajaxJson.setAttributes(map);
        return ajaxJson;
    }

    /**
     * 换休申请保存
     * @param hrmChangeOfRestApply
     * @param
     * @return
     */
    @RequestMapping(params = "saveVacationapply",method = RequestMethod.POST)
    public String saveVacationapply(HrmChangeOfRestApply hrmChangeOfRestApply, HttpServletRequest request,HttpSession session){
        System.out.println(hrmChangeOfRestApply.getDivision_manager_examine()+"rrrrrrreeeeeeeeeeeeeeeee");
        String[] oneStart = request.getParameterValues("oneStart");
        String[] oneEnd = request.getParameterValues("oneEnd");
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        attendanceRecordService.saveVacationapply(hrmChangeOfRestApply,user,oneStart,oneEnd);
        return "redirect:attendance.do?vacationApplyListPageJump";
    }

    /**
     * 换休页面查询页面跳转
     * @param session
     * @return
     */
    @RequestMapping(params = "vacationApplyListPageJump",method = RequestMethod.GET)
    public ModelAndView vacationApplyListPageJump(HttpSession session){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //是否为负责人0、否1、是
        Integer roleType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                roleType = 2;
                break;
            }
            if ("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
                break;
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/vacationapplyList");
        modelAndView.addObject("roleType",roleType);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    /**
     *查询换休申请列表
     * @return
     */
    @RequestMapping(params = "queryvacationApply")
    @ResponseBody
    public List<HrmChangeOfRestApply> queryvacationApply(HttpSession session,Integer status,String userName,Date startDate,Date endDate){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        
        
        //是否为负责人0、普通员工1、部门负责人2、部门经理3、行政
        Integer roleType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                roleType = 2;
                break;
            }
            if("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
            }
            /*if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = 1;
                break;
            }
            if ("管理员".equals(roleUser.getTSRole().getRoleName())){
                isResponsPerson = true;
                break;
            }*/
        }
        List<HrmChangeOfRestApply> hrmChangeOfRestApplyList = attendanceRecordService.findVacationApplyList(user,roleType,status,userName,startDate,endDate);
        return hrmChangeOfRestApplyList;
    }

    /**
     * 换休审批页面跳转
     * @param vacationApplyId
     * @param session
     * @return
     */
    @RequestMapping(params = "vacationApplyExamination",method = RequestMethod.GET)
    public ModelAndView vacationApplyExamination(String vacationApplyId,HttpSession session) {
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //判断登录人员的角色类型1、部门负责人2、部门经理3、行政
        Integer roleType = null;
        for (TSRoleUser roleUser : rUsers) {
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())) {
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())) {
                roleType = 2;
                break;
            }
            if("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
                break;
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/vacationapplyExamination");
        HrmChangeOfRestApply hrmChangeOfRestApply = attendanceRecordService.getVacationApplyById(vacationApplyId);
        modelAndView.addObject("roleType",roleType);
        modelAndView.addObject("changeOfRestApply",hrmChangeOfRestApply);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    /**
     * 换休审批保存
     * @param vacationId
     * @param roleType
     * @param immediate_supervisor_examine
     * @param immediate_supervisor_advice
     * @param division_manager_examine
     * @param division_manager_advice
     * @return
     */
    @RequestMapping(params = "saveVacationapplyExamination",method = RequestMethod.POST)
    public String saveVacationapplyExamination(String vacationId,Integer roleType,Integer immediate_supervisor_examine,String immediate_supervisor_advice,
                                                    Integer division_manager_examine,String division_manager_advice,String immediate_supervisor_sign,String division_manager_sign){
        attendanceRecordService.saveVacationapplyExamination(vacationId,roleType,immediate_supervisor_examine,immediate_supervisor_advice,
                                                            division_manager_examine,division_manager_advice,immediate_supervisor_sign,division_manager_sign);
        return "redirect:attendance.do?vacationApplyListPageJump";
    }

    /**
     * 用户换休申请确认
     * @param vacationApplyId
     * @return
     */
    @RequestMapping(params = "userConfirmVacationApply")
    @ResponseBody
    public AjaxJson userConfirmVacationApply(String vacationApplyId){
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveUserConfirmVacationApply(vacationApplyId);
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }

    /**
     * 人事换休关闭
     * @param vacationApplyId
     * @return
     */
    @RequestMapping(params = "adminConfirmVacationApply")
    @ResponseBody
    public AjaxJson adminConfirmVacationApply(String vacationApplyId,HttpSession session){
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveAdminConfirmVacationApply(vacationApplyId,user.getUserName());
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }

    /**
     * 换休申请详情
     * @param vacationApplyId
     * @return
     */
    @RequestMapping(params = "vacationApplyDetail",method = RequestMethod.GET)
    public ModelAndView vacationApplyDetail(String vacationApplyId){
        HrmChangeOfRestApply hrmChangeOfRestApply = attendanceRecordService.getVacationApplyById(vacationApplyId);
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/vacationapplyDetial");
        modelAndView.addObject("changeOfRestApply",hrmChangeOfRestApply);
        return modelAndView;
    }

    /**
     * 年假统计保存
     * @param idList
     * @param beforeList
     * @return
     */
    @RequestMapping(params = "saveAnnualVacation",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveAnnualVacation(String idList,String beforeList,String thisYearList,String sumDayList,String surplusDayList,String remarkList){
    	List<String> ids = JSONObject.parseArray(idList,String.class);
        List<Double> befores = JSONObject.parseArray(beforeList,Double.class);
        List<Double> thisYears = JSONObject.parseArray(thisYearList,Double.class);
        List<Double> sumDays = JSONObject.parseArray(sumDayList,Double.class);
        List<Double> surplusDays = JSONObject.parseArray(surplusDayList,Double.class);
        List<String> remarks = JSONObject.parseArray(remarkList,String.class);
        
        String flag = attendanceRecordService.saveAnnualVacation(ids,befores,thisYears,sumDays,surplusDays,remarks);
        AjaxJson ajaxJson = new AjaxJson();
        if("ok".equals(flag)) {
        	 ajaxJson.setMsg("ok");
        }else {
        	 ajaxJson.setMsg("false");
        }
        return  ajaxJson;
    }
    /**
     * 考勤记录状态显示查询
     * 0-10 对应的状态显示
     * @return
     */
    @RequestMapping(params = "attendancerecordtypelist")
    @ResponseBody
    public List<HrmAttendanceRecordType> attendancerecordtypelist(){
        String hql="from HrmAttendanceRecordType";
        return attendanceRecordService.findHql(hql);
    }
    
    /**
     * 考勤记录查询
     * 
     * @return
     */
    /*@RequestMapping(params = "attendancerecordlist")
    @ResponseBody
    public List<HrmAttendanceRecord> getProject(){
        String hql="from HrmAttendanceRecord";
        return attendanceRecordService.findHql(hql);
    }*/
    /**
     * 获取考勤左边栏 姓名 工号
     * @return
     */
    @RequestMapping(params = "getnameandjobnum")
    @ResponseBody
    public List<TSUser> getnameandjobnum(){
    	//查询在职人员的集合
    	String hql="from TSUser u where u.userstatus=? and position!=? order by createDate asc";
    	return  attendanceRecordService.findHql(hql,"在职","集团总裁");
    }
    
    /**
     * 查询某年某月考勤记录的详细数据（右边的状态栏目）
     * @param year
     * @param month
     * @param
     * @return
     */
    @RequestMapping(params = "getattendancerecordlist",method = RequestMethod.GET)
    @ResponseBody
    public List<HrmAttendanceRecord> getWorkHourList(int year,int month){
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        return attendanceRecordService.getAttendanceRecordList(year,month);
    }
    /**
     * 跳转到出差申请列表
     */
    @RequestMapping(params = "businessApplyList")
    public ModelAndView businessApplyList(HttpSession session){
    	 //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        //是否为负责人0、否1、是
        Integer roleType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                roleType = 2;
                break;
            }
            if ("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
                break;
            }
        }
        ModelAndView modelAndView = new ModelAndView("hrm/attendance/businessapplylist");
        modelAndView.addObject("roleType",roleType);
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    /**
     * 出差申请显示固定信息 姓名 部门 
     * @param session
     * @return
     */
    @RequestMapping(params = "getuseronline")
    @ResponseBody
    public AjaxJson getUser(HttpSession session) {
        AjaxJson a = new AjaxJson();
        try {
            Client client = ClientManager.getInstance().getClient(session.getId());
            TSUser tsUser = attendanceRecordService.getUserById(client.getUser().getId());
            a.setObj(tsUser);
        } catch (Exception e) {
            a.setSuccess(false);
            a.setMsg("session 已经过期 ，请重新登陆");
            return a;
        }
        return a;
    }
    /**
     * 保存出差申请
     */
    @RequestMapping(params = "businessapply",method = RequestMethod.POST)
    public String saveBusinessApply(@RequestParam(value = "file") MultipartFile file,HrmBusinessApply hrmBusinessApply, HttpServletRequest request,HttpSession session){
		//获取当前用户信息
	    Client client = ClientManager.getInstance().getClient(session.getId());
	    TSUser user =client.getUser();
        String leaveStart = request.getParameter("beginTime");
        String leaveEnd = request.getParameter("endTime");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 	Date sdate = null;
		Date edate = null;
		try {
			 sdate = simpleDateFormat.parse(leaveStart.replace("T", " "));
			 edate = simpleDateFormat.parse(leaveEnd.replace("T", " "));
			 attendanceRecordService.saveBusinessApply(file,request,user,hrmBusinessApply,sdate,edate);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("保存失败");
		}
		return "redirect:attendance.do?businessApplyList";
    }
  /**
   * 查询出差列表
   * @param session
   * @param status
   * @param applyName
   * @param startDate
   * @param endDate
   * @return
   */
    @RequestMapping(params = "querybusinessApply")
    @ResponseBody
    public List<HrmBusinessApply> querybusinessApply(HttpSession session,Integer status,String applyName,Date startDate,Date endDate){
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();

        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        
        //是否为负责人   0:普通员工 ,1:部门负责人,2:部门经理,3:行政
        Integer roleType = 0;
        for (TSRoleUser roleUser : rUsers){
            if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                roleType = 1;
                break;
            }
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                roleType = 2;
                break;
            }
            if("行政".equals(roleUser.getTSRole().getRoleName())){
                roleType = 3;
            }
        }
        List<HrmBusinessApply> hrmBusinessApplyList = attendanceRecordService.findBusinessApplyList(user,roleType,status,applyName,startDate,endDate);
        return hrmBusinessApplyList;
    }
  /**
   * 查询出差详情
   * @param businessId
   * @return
   */
    @RequestMapping(params = "showdetails")
    @ResponseBody
    public AjaxJson showbusinessdetails(String businessId){
        AjaxJson ajaxJson = new AjaxJson();
        HrmBusinessApply hrmBusinessApply = attendanceRecordService.findHrmBusinessById(businessId);
        ajaxJson.setObj(hrmBusinessApply);
        if(hrmBusinessApply!=null){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }
    /**
     * 出差 文件下载
     * @param model
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping(params = "downloadfile", method = {RequestMethod.GET,RequestMethod.POST})
    public  void  downLoadFile(Model model,HttpServletResponse response,String id) throws IOException {
    	HrmBusinessApply hrmBusinessApply = attendanceRecordService.findHrmBusinessById(id);
    	String fileAddress = hrmBusinessApply.getFileAddress();
        File file=new File(fileAddress);
        if(fileAddress==null||!file.exists()){
            model.addAttribute("msg", "亲,您要下载的文件不存在");
        }
        try{
            response.reset();
		    //设置ContentType
		    response.setContentType("application/octet-stream; charset=utf-8");
		    //处理中文文件名中文乱码问题
		    String fileName=new String(file.getName().getBytes("utf-8"),"ISO-8859-1");
		    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		    IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		    model.addAttribute("msg", "下载成功");
        }catch (Exception e) {
            model.addAttribute("msg", "下载失败");
            System.out.println("下载失败");
        }
    }
    /**
     * 用户出差确认
     * 
     */
    @RequestMapping(params = "userConfirmbusinessApply")
    @ResponseBody
    public AjaxJson userConfirmbusinessApply(String businessId){
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveUserConfirmBusinessApply(businessId);
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }
    /**
     * 人事关闭(出差)
  */
    @RequestMapping(params = "adminConfirmBusinessApply")
    @ResponseBody
    public AjaxJson adminConfirmBusinessApply(String businessId,HttpSession session){
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user = client.getUser();
        AjaxJson ajaxJson = new AjaxJson();
        Boolean flag = attendanceRecordService.saveAdminConfirmBusinessApply(businessId,user.getUserName());
        if(flag){
            ajaxJson.setMsg("ok");
        }else{
            ajaxJson.setMsg("fail");
        }
        return ajaxJson;
    }
}
