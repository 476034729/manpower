package org.jeecgframework.web.hrm.controller;


import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.service.CMProjectInputTotalService;
import org.jeecgframework.web.hrm.service.ProjectInputTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/CMProjectInputTotalController")
public class CMProjectInputTotalController {

    @Autowired
    private CMProjectInputTotalService cmProjectInputTotalService;

    @RequestMapping(params = "goprojectinputtotal")
    public ModelAndView goProjectInputTotal() {
        return new ModelAndView("hrm/hrAnalyze/projectinputtotalCM");
    }

    @RequestMapping(params = "getuserlist")
    @ResponseBody
    public AjaxJson getUserList() {
        AjaxJson a = new AjaxJson();

        a.setObj(cmProjectInputTotalService.getUserList());

        return a;

    }

    @RequestMapping(params = "getworkhourinfo")
    @ResponseBody
    public AjaxJson getWorkHourInfo(String projectCode, int year, int beginMonth, int endMonth) {
        AjaxJson a = new AjaxJson();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        for (int i = beginMonth; i <= endMonth; i++) {
            map.put(i, cmProjectInputTotalService.getWorkHourByProject(projectCode, year, i));
        }
        a.setObj(map);
        return a;
    }

    @RequestMapping(params = "getworkhourinfobylevel")
    @ResponseBody
    public AjaxJson getWorkHourInfoByLevel(String projectCode, int year, int beginMonth, int endMonth){
        AjaxJson a= new AjaxJson();
        Map<Integer,Map<String,Object>> map= new HashMap<Integer, Map<String, Object>>();

        for (int i=beginMonth;i<=endMonth;i++){
            map.put(i,cmProjectInputTotalService.getWorkHourCount(projectCode,year,i));
        }

        a.setObj(map);

        return a;
    }

}
