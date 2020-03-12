package org.jeecgframework.web.hrm.controller;


import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.HrmMenuService;
import org.jeecgframework.web.hrm.service.HrmRoleService;
import org.jeecgframework.web.hrm.service.HrmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hrmMenuController")
public class HrmMenuController {

    @Autowired
    private HrmMenuService hrmMenuService;

    @Autowired
    private HrmUserService hrmUserService;

    @Autowired
    private HrmRoleService hrmRoleService;

    @RequestMapping(params = "getMenuByUser")
    @ResponseBody
    public Map<String,List<HrmMenu>> getMenuList(){
//        Map<String,HrmMenu> map=new HashMap<String, HrmMenu>();
//        HrmUser hrmUser = ResourceUtil.getSessionHrmUserName();
        List<HrmMenu> hrmMenus=new ArrayList<HrmMenu>();
        HrmUser hrmUser=new HrmUser();
        hrmUser.setUser_id("1");
        List<HrmUserRole> hrmUserRoles=hrmUserService.getRoleByUser(hrmUser);
        Map<String,List<HrmMenu>> map=hrmUserService.getMenuByUser(hrmUser);
        return map;
    }
}
