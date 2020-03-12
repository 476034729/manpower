package org.jeecgframework.web.hrm.controller;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.ApplyCheckWithApply;
import org.jeecgframework.web.hrm.service.ApplyCheckService;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/ApplyCheckController")
public class ApplyCheckController {
    @Autowired
    private ApplyCheckService applyCheckService;

    @RequestMapping(params = "getchecklist")
    @ResponseBody
    public List<ApplyCheckWithApply> getCheckList(HttpSession session){
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        if (applyCheckService.getVacationCheckList(user.getId()) == null){
            return null;
        }
        return applyCheckService.getVacationCheckList(user.getId());
    }

    @RequestMapping(params = "getusername")
    @ResponseBody
    public AjaxJson getUserName(String id){
        AjaxJson a = new AjaxJson();
        a.setObj(applyCheckService.getUserName(id));
        return a;
    }

    @RequestMapping(params = "check")
    @ResponseBody
    public AjaxJson check(String id ,String agree,String remark){
        AjaxJson a = new AjaxJson();
        if (applyCheckService.check(id,agree,remark)){
            return a;
        }else {
            a.setSuccess(false);
            a.setMsg("处理失败!");
        }
        return a;
    }

}
