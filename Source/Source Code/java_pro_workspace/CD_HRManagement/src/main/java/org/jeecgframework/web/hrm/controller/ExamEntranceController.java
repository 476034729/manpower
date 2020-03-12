package org.jeecgframework.web.hrm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/exam")
public class ExamEntranceController {
	@RequestMapping(params = "examEntranceList")
    public String examEntrance(HttpServletRequest request){
        return "hrm/exam/entrance";
    }
}
