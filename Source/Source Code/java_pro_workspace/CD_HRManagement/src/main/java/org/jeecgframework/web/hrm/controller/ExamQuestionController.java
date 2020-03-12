package org.jeecgframework.web.hrm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.web.hrm.bean.ExamPost;
import org.jeecgframework.web.hrm.bean.ExaminationQuestion;
import org.jeecgframework.web.hrm.bean.ExaminationQuestionOption;
import org.jeecgframework.web.hrm.service.IExamPostService;
import org.jeecgframework.web.hrm.service.IExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javassist.expr.NewArray;

//@Controller
//@RequestMapping(value = "/examQuestion")
public class ExamQuestionController {
	
//	private IExamPostService examPostService	
	@RequestMapping(params = "examQustionChoose")
	public String examEntrance(HttpServletRequest request){
//		List<ExamPost> list = examPostService.getAllExamPosts();
		//request.setAttribute("examPosts", list);
		//修改成存放在session里
//		request.getSession().setAttribute("examPosts", list);
		//examinationQustionService.aa();测试查询
        return "hrm/exam/examQustionChoose";
    }
	
}	
