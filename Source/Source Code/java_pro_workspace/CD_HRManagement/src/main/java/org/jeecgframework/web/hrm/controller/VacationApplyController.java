package org.jeecgframework.web.hrm.controller;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.VacationApply;
import org.jeecgframework.web.hrm.service.ApplyCheckService;
import org.jeecgframework.web.hrm.service.VacationApplyService;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/VacationApplyController")
public class VacationApplyController {
    @Autowired
    private VacationApplyService vacationApplyService;

    @Autowired
    private ApplyCheckService applyCheckService;

    @RequestMapping(params = "add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addApply(VacationApply vacationApply, long begin, long end, HttpSession session) {
        vacationApply.setId(UUID.randomUUID().toString());
        vacationApply.setBeginTime(new Date(begin));
        vacationApply.setEndTime(new Date(end));
        AjaxJson a = new AjaxJson();
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser tsUser = vacationApplyService.getUserById(client.getUser().getId());
        vacationApply.setUser_id(tsUser.getId());
        vacationApply.setDepartment(tsUser.getDepartment());
        vacationApply.setSubmitTime(new Date());
        vacationApply.setStatus("3");//3:审核中
        if (vacationApplyService.addApply(vacationApply)) {
            if (applyCheckService.addVacation(vacationApply.getId(), vacationApply.getDepartment())) {
                a.setMsg("申请成功!");
            }
        }
        return a;
    }

    @RequestMapping(params = "getuseronline")
    @ResponseBody
    public AjaxJson getUser(HttpSession session) {
        AjaxJson a = new AjaxJson();
        try {
            Client client = ClientManager.getInstance().getClient(session.getId());
            TSUser tsUser = vacationApplyService.getUserById(client.getUser().getId());
            a.setObj(tsUser);
        } catch (Exception e) {
            a.setSuccess(false);
            a.setMsg("session 已经过期 ，请重新登陆");
            return a;
        }
        return a;
    }

    @RequestMapping(params = "getapplylist")
    @ResponseBody
    public AjaxJson getApplyList(HttpSession session){
        AjaxJson a = new AjaxJson();
        Client client = ClientManager.getInstance().getClient(session.getId());
        a.setObj(vacationApplyService.getApplyList(client.getUser().getId()));
        return a;
    }

    @RequestMapping(params = "getapplybyid")
    @ResponseBody
    public AjaxJson getApplyById(String id){
        AjaxJson a = new AjaxJson();
        a.setObj(vacationApplyService.getApplyById(id));
        return a;
    }

    @RequestMapping(params = "update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateApply(VacationApply vacationApply, long begin, long end){
        AjaxJson a= new AjaxJson();
        vacationApply.setStatus("3");
        vacationApply.setSubmitTime(new Date());
        vacationApply.setBeginTime(new Date(begin));
        vacationApply.setEndTime(new Date(end));
        //这里没有控制事务，注意！
        if (vacationApplyService.updateApply(vacationApply)){
            if (applyCheckService.updateVacation(vacationApply.getId(), vacationApply.getDepartment())) {
                a.setMsg("申请成功!");
            }else {
                a.setSuccess(false);
                a.setMsg("失败");
            }
        }else {
            a.setSuccess(false);
            a.setMsg("失败！");
        }
        return a;
    }
}
