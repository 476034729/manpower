package org.jeecgframework.web.hrm.controller;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.jeecgframework.web.hrm.service.WorkHourService;
import org.jeecgframework.web.system.controller.core.UserController;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/hrmEmployee")
public class HrmEmployeeContrller {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkHourService workHourService;

    @Autowired
    private IAttendanceRecordService iAttendanceRecordService;

    @RequestMapping(params = "cdDemo")
    public String cdList(HttpServletRequest request){
        return "hrm/employee/cdList";
    }

    @RequestMapping(params = "hqDemo")
    public String hrList(HttpServletRequest request){
        return "hrm/employee/hqList";
    }

    /**q
     * 成都办事处员工列表
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "cddatagrid")
    public void cdDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        cq.add(Restrictions.sqlRestriction("site='CD'"));
        if (StringUtil.isNotEmpty(user.getUserName())){
            cq.add(Restrictions.sqlRestriction("username like '%"+user.getUserName()+"%'"));
        }
        if (StringUtil.isNotEmpty(user.getPosition())){
            cq.add(Restrictions.sqlRestriction("position like '%"+user.getPosition()+"%'"));
        }
        if (StringUtil.isNotEmpty(user.getRemark())){
            cq.add(Restrictions.sqlRestriction("remark like concat('%"+user.getRemark()+"%'"));
        }

        this.systemService.getDataGridReturn(cq, true);

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 总部员工列表
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "hqdatagrid")
    public void hqDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        cq.add(Restrictions.sqlRestriction("site='HQ'"));
        if (StringUtil.isNotEmpty(user.getUserName())){
            cq.add(Restrictions.sqlRestriction("username like '%"+user.getUserName()+"%'"));
        }
        if (StringUtil.isNotEmpty(user.getPosition())){
            cq.add(Restrictions.sqlRestriction("position like '%"+user.getPosition()+"%'"));
        }
        this.systemService.getDataGridReturn(cq, true);

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 成都办事处员工修改或添加跳转页面
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "cdaddorupdate")
    public ModelAndView cdAddOrUpdate(TSUser user, HttpServletRequest req) {

        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());

            idandname(req, user);

            req.setAttribute("user", user);
        }


        return new ModelAndView("hrm/employee/cdemployee");
    }

    /**
     * 总部员工修改或添加跳转页面
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "hqaddorupdate")
    public ModelAndView hqAddOrUpdate(TSUser user, HttpServletRequest req) {

        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());
            req.setAttribute("user", user);
        }
        return new ModelAndView("hrm/employee/hqemployee");
    }

    /**
     * 保存修改或添加的员工信息
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(params = "saveUser",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, TSUser user , String roleid,String level,String department) {
     
        String message = null;
        AjaxJson j = new AjaxJson();

        String password = oConvertUtils.getString(req.getParameter("password"));

            TSUser users = systemService.findUniqueByProperty(TSUser.class, "id",user.getId());
            if (users != null) {
                users.setSite(user.getSite());
                users.setLevel(user.getLevel());
                users.setPosition(user.getPosition());
                users.setEmail(user.getEmail());
                users.setMobilePhone(user.getMobilePhone());
                users.setUserstatus(user.getUserstatus());
                users.setRemark(user.getRemark());

                users.setDepartment(user.getDepartment());

                users.setType(user.getType());

                systemService.updateEntitie(users);
                message = "员工: " + users.getUserName() + "更新成功";
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

                //为正式员工增加年假记录
                if("正式".equals(users.getType())&&"在职".equals(users.getUserstatus())){
                    iAttendanceRecordService.insertAnnunalLeaveForNewUser(users);
                }
                if (roleid != null){
                    String[] roleList= roleid.split(",");
                    String sql = "delete from t_s_role_user where userid='"+user.getId()+"'";
                    String insertSql = "insert into t_s_role_user(id,userid,roleid) values(?,?,?)";
                    try {
                        systemService.executeSql(sql);
                        for (String role:roleList){
                            systemService.executeSql(insertSql,UUID.randomUUID().toString().replace("-",""),user.getId(),role);
                        }
                    }catch (Exception e){

                    }
                }
            } else {
                user.setPassword(PasswordUtil.encrypt(user.getJobNum(), password, PasswordUtil.getStaticSalt()));
                user.setStatus((short)1);
                user.setDeleteFlag(Globals.Delete_Normal);
                systemService.save(user);
                TSUserOrg userOrg=new TSUserOrg();
                userOrg.setTsUser(user);
                TSDepart depart=new TSDepart();
                /*depart.setId("8a8080ca64a1f6c40164a1fa2f940001");//104测试数据库*/
                depart.setId("8a8080ca64d0da790164d0dbce490001");//210线上数据库

                userOrg.setTsDepart(depart);
                systemService.save(userOrg);
                message = "员工: " + user.getUserName() + "添加成功";

                logger.info("---添加ing"+user.getId()+"-----"+user.getUserName()+"-----"+user.getJobNum());

                if (roleid != null){
                    String[] roleList= roleid.split(",");
                    String sql = "delete from t_s_role_user where userid='"+user.getId()+"'";
                    String insertSql = "insert into t_s_role_user(id,userid,roleid) values(?,?,?)";
                    try {
                        systemService.executeSql(sql);
                        for (String role:roleList){
                            systemService.executeSql(insertSql,UUID.randomUUID().toString().replace("-",""),user.getId(),role);
                        }
                        logger.info("---添加ing"+user.getId()+"-----"+user.getUserName()+"-----"+user.getJobNum()+"---成功");

                    }catch (Exception e){

                    }
                }

                //为新员工添加工时记录
                workHourService.insertNewEmpWorkHour(user.getId());

                //为新员工添加该年的加班信息记录
                iAttendanceRecordService.insertNewEmpMonthOvertime(user);

                //为正式员工增加年假记录
                if("正式".equals(user.getType())&&"在职".equals(user.getUserstatus())){
                    iAttendanceRecordService.insertAnnunalLeaveForNewUser(user);
                }
                
                //添加考勤记录(从入职当前时间添加)
                if("在职".equals(user.getUserstatus())&&!"集团总裁".equals(user.getPosition())) {
                	iAttendanceRecordService.insertNewEmpAttendanceRecord(user);
                }
                
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }


        j.setMsg(message);

        return j;
    }

    /**
     * 删除某一个员工的信息
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSUser user, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        user = systemService.getEntity(TSUser.class, user.getId());
        if (userService.delUserInfo(user) == 1){
        	//删除所有考勤记录
        	String sql = "delete  from t_hrm_attendance_record where user_id='"+user.getId()+"'";
        	systemService.executeSql(sql);
            systemService.delete(user);
        }else {
            j.setMsg("员工："+user.getUserName()+",删除失败");
            return j;
        }



        j.setMsg("员工："+user.getUserName()+",删除成功");
        return j;
    }

    public void idandname(HttpServletRequest req, TSUser user) {
        List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        String roleId = "";
        String roleName = "";
        if (roleUsers.size() > 0) {
            for (TSRoleUser tRoleUser : roleUsers) {
                roleId += tRoleUser.getTSRole().getId() + ",";
                roleName += tRoleUser.getTSRole().getRoleName() + ",";
            }
        }
        req.setAttribute("id", roleId);
        req.setAttribute("roleName", roleName);

    }
}
