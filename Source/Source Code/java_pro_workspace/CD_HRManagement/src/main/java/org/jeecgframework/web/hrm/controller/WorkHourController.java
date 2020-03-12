package org.jeecgframework.web.hrm.controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.bean.WorkHourCM;
import org.jeecgframework.web.hrm.service.WorkHourService;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketOptions;
import java.util.*;


@Controller
@RequestMapping("/WorkHourController")
public class WorkHourController {

    @Autowired
    private WorkHourService workHourService;

    @Autowired
    private SystemService systemService;

    /**
     * 工时填写
     * @return
     */
    @RequestMapping(params = "goworkhour")
    public ModelAndView goWorkHour(){
        return new ModelAndView("hrm/workhour/workhourlist");
    }
    @RequestMapping(params = "showlist")
    @ResponseBody
    public AjaxJson showList(int year,int month){
        AjaxJson a = new AjaxJson();
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        a.setObj(workHourService.getTimeList(year,month));
        return a;
    }


    /**
     * 根据session的用户id获取填写的工时列表,
     * @param year
     * @param month
     * @param session
     * @return
     */
    @RequestMapping(params = "getworkhourlist",method = RequestMethod.GET)
    @ResponseBody
    public List<WorkHour> getWorkHourList(int year,int month,HttpSession session){
        Client client = ClientManager.getInstance().getClient(session.getId());
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        //对于用户的id进行校验
        if(client != null && client.getUser() != null && StringUtils.isNotEmpty(client.getUser().getId())){
            return workHourService.getWorkHourList(client.getUser().getId(),year,month);
        }
        return workHourService.getWorkHourList(null,year,month);
    }

    /**
     * 根据ID获取工时列表
     * @param year
     * @param month
     * @param id
     * @return
     */
    @RequestMapping(params = "getworkhourlist1",method = RequestMethod.GET)
    @ResponseBody
    public List<WorkHour> getWorkHourList1(int year,int month,String id){
    	Calendar calendar=Calendar.getInstance();
    	if (year==0){
    		year=calendar.get(Calendar.YEAR);
    	}
    	if (month==0){
    		month=calendar.get(Calendar.MONTH)+1;
    	}
    	return workHourService.getWorkHourList(id,year,month);
    }

    @RequestMapping(params = "getcmworkhourlist",method = RequestMethod.GET)
    @ResponseBody
    public List<WorkHourCM> getCMWorkHourList(int year, int month, String id){
    	Calendar calendar=Calendar.getInstance();
    	if (year==0){
    		year=calendar.get(Calendar.YEAR);
    	}
    	if (month==0){
    		month=calendar.get(Calendar.MONTH)+1;
    	}
    	return workHourService.getCMWorkHourList(id,year,month);
    }

    /**
     * 提交填写的工时信息
     * @param request
     * @param ids
     * @param codes
     * @param texts
     * @return
     */
    @RequestMapping(params = "submitworkhourlist",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson SubmitWorkHourList(HttpServletRequest request,String ids,String codes,String texts){

        HttpSession session=request.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        String user_id = client.getUser().getId();

        List<String> idList= JSONObject.parseArray(ids,String.class);
        List<String> codeList= JSONObject.parseArray(codes,String.class);
        List<String> textList= JSONObject.parseArray(texts,String.class);
        AjaxJson a=new AjaxJson();
        try {
//            workHourService.updateWorkHour(idList,codeList,textList,user_id);
            for (int i=0;i<idList.size();i++){
                String code = " ";
                if (!"请选择项目编码".equals(codeList.get(i))){
                    code = codeList.get(i);
                }
                workHourService.updateWorkHour(idList.get(i),code,textList.get(i));
                workHourService.updateWorkHourCM(idList.get(i),code,textList.get(i));

            }
        }catch (Exception e){
            System.out.println("数据库错误");
            a.setMsg("操作失败");
        }
        return a;
    }

    /**
     * 提交CM修改的工时数据
     * @param request
     * @param ids
     * @param codes
     * @param texts
     * @return
     */
    @RequestMapping(params = "submitcmworkhourlist",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson SubmitCMWorkHourList(HttpServletRequest request,String ids,String codes,String texts){
        List<String> idList= JSONObject.parseArray(ids,String.class);
        List<String> codeList= JSONObject.parseArray(codes,String.class);
        List<String> textList= JSONObject.parseArray(texts,String.class);
        AjaxJson a=new AjaxJson();
        try {
            for (int i=0;i<idList.size();i++){
                String code = " ";
                if (!"请选择项目编码".equals(codeList.get(i))){
                    code = codeList.get(i);
                }
                workHourService.updateWorkHourCM(idList.get(i),code,textList.get(i));
            }
        }catch (Exception e){
            System.out.println("数据库错误");
            a.setMsg("操作失败");
        }
        return a;
    }

    /**
     * 工时查询
     * @return
     */
    @RequestMapping(params = "goqueryworkhourlist")
    public ModelAndView goQueryWorkHourList(){
        return new ModelAndView("hrm/workhour/queryworkhour");
    }

    /**
     * CM检查
     * @return
     */
    @RequestMapping(params = "cmcheckworkhourlist")
    public ModelAndView cmCheckWorkHourList() {
    	return new ModelAndView("hrm/workhour/cmcheckworkhourlist");
    }

    /**
     * CM工时修改
     * @return
     */
    @RequestMapping(params = "allworkhourlist")
    public ModelAndView allWorkHourlist() {
    	return new ModelAndView("hrm/workhour/allworkhourlist");
    }

    @RequestMapping(params = "getsteuserlist")
    @ResponseBody
    public Set<TSUser> getSteUserList(HttpSession session, int year, int month){
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        String department =user.getDepartment();
        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        for (TSRoleUser roleUser : rUsers){
            if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                department = "all";
                break;
            }
            if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                department = "all";
                break;
            }
        }
        return workHourService.getUserList(department,year,month);
    }

    /**
     * 获取全部职工
     * @param session
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(params = "getstealluserlist")
    @ResponseBody
    public Set<TSUser> getSteAllUserList(HttpSession session, int year, int month){
    	Calendar calendar=Calendar.getInstance();
    	if (year==0){
    		year=calendar.get(Calendar.YEAR);
    	}
    	if (month==0){
    		month=calendar.get(Calendar.MONTH)+1;
    	}
    	String department = "all";
    	return workHourService.getUserList(department,year,month);
    }

    /**
     * 判断是否有记录
     * @param user_id
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(params = "haveworkhourrecord",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson haveRecord(String user_id,int year , int month){
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        AjaxJson a=new AjaxJson();
        a.setObj(workHourService.haveRecord(user_id,year,month));
        return a;
    }


    @RequestMapping(params = "getworkhourlistbyid",method = RequestMethod.GET)
    @ResponseBody
    public List<WorkHour> getWorkHourListById(String id, int year ,int month){
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        return workHourService.getWorkHourList(id,year,month);
    }

    @RequestMapping(params = "getworkhourlistbyids",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,List<WorkHour>> getWorkHourListByIds(String ids,int year,int month){
    	List<String> idList= JSONObject.parseArray(ids,String.class);
        Map<String,List<WorkHour>> map=new HashMap<String, List<WorkHour>>();
        Calendar calendar=Calendar.getInstance();
        if (year==0){
            year=calendar.get(Calendar.YEAR);
        }
        if (month==0){
            month=calendar.get(Calendar.MONTH)+1;
        }
        
        for (String id:idList){
            map.put(workHourService.getUserById(id).getUserName(),workHourService.getWorkHourList(id,year,month));
        }
        return map;
    }
    @RequestMapping(params = "cmCheck",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,List<WorkHour>> cmCheckWorkHourList(String statuss,String projects,String ids,int year,int month){
    	List<Integer> statusList = null;
    	List<String> projectList = null;
    	if(statuss!=null) {
    		 statusList = JSONObject.parseArray(statuss,Integer.class);
    	}
    	if(projects!=null) {
    		projectList = JSONObject.parseArray(projects,String.class);
    	}
    	List<String> idList= JSONObject.parseArray(ids,String.class);
    	Calendar calendar=Calendar.getInstance();
    	if (year==0){
    		year=calendar.get(Calendar.YEAR);
    	}
    	if (month==0){
    		month=calendar.get(Calendar.MONTH)+1;
    	}
    	return workHourService.getCMCheckWorkHourList(statusList,projectList,idList,year,month);
    }
    
    
}
