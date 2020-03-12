package org.jeecgframework.web.hrm.controller;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.hrm.bean.HrmCustomer;
import org.jeecgframework.web.system.controller.core.UserController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/hrmCustomer")
public class HrmCustomerController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "customerDemo")
    public String cdList(HttpServletRequest request){
        return "hrm/customer/customerList";
    }

    @RequestMapping(params = "datagrid")
    public void cdDatagrid(HrmCustomer user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(HrmCustomer.class, dataGrid);
        if (StringUtil.isNotEmpty(user.getCustomerName())){
            cq.add(Restrictions.sqlRestriction("customerName like '%"+user.getCustomerName()+"%'"));
        }
        if (StringUtil.isNotEmpty(user.getRemark())){
            cq.add(Restrictions.sqlRestriction("remark like concat('%"+user.getRemark()+"%'"));
        }
        this.systemService.getDataGridReturn(cq, true);

        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "addorupdate")
    public ModelAndView cdAddOrUpdate(HrmCustomer customer, HttpServletRequest req) {

        if (StringUtil.isNotEmpty(customer.getId())) {
            customer = systemService.getEntity(HrmCustomer.class, customer.getId());

            req.setAttribute("customer", customer);
        }

        return new ModelAndView("hrm/customer/customer");
    }

    @RequestMapping(params = "saveCustomer",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, HrmCustomer customer) {
        String message = null;
        AjaxJson j = new AjaxJson();

        HrmCustomer customer1 = systemService.findUniqueByProperty(HrmCustomer.class, "id",customer.getId());
        if (customer1 != null) {
            customer1.setCustomerEmail(customer.getCustomerEmail());
            customer1.setCustomerPhone(customer.getCustomerPhone());
            customer1.setRemark(customer.getRemark());
            systemService.updateEntitie(customer1);
            message = "客户: " + customer1.getCustomerName() + "更新成功";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            systemService.save(customer);
            message = "客户: " + customer.getCustomerName() + "添加成功";

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }


        j.setMsg(message);

        return j;
    }

    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(HrmCustomer user, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        user = systemService.getEntity(HrmCustomer.class, user.getId());
        systemService.delete(user);
        j.setMsg("客户："+user.getCustomerName()+",删除成功");
        return j;
    }
}
