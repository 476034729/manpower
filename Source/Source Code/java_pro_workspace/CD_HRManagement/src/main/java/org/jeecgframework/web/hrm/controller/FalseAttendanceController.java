package org.jeecgframework.web.hrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/FalseAttendanceController")
public class FalseAttendanceController {

    @RequestMapping(params = "overtimeApply",method = RequestMethod.GET)
    public ModelAndView test(){
        return new ModelAndView("hrm/falseAttendance/overtimeApply");
    }
}
