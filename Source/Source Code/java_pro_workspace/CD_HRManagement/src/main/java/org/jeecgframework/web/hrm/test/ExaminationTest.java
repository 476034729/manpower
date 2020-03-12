package org.jeecgframework.web.hrm.test;

import java.util.List;

import org.jeecgframework.web.hrm.bean.Examination;
import org.jeecgframework.web.hrm.bean.ExaminationQuestion;
import org.jeecgframework.web.hrm.bean.ExaminationQuestionOption;
import org.jeecgframework.web.hrm.bean.ExaminationType;
import org.jeecgframework.web.hrm.service.IExamQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class ExaminationTest {
	@Autowired
	private IExamQuestionService examinationQustionService;
	
	@Test
	public void test0(){
		Examination examination = examinationQustionService.getExaminationByPostId(1);
		System.out.println(examination.getExamination_id());
	}
	
	@Test
	public void test1(){
		ExaminationType examinationType = examinationQustionService.getExaminationType("单选题", examinationQustionService.getExaminationByPostId(1).getExamination_id());
		System.out.println(examinationType.getExaminationType_id());
	}
	
	@Test
	public void test3(){
		List<ExaminationQuestion> questions = examinationQustionService.getExaminationQuestionByType(1);
		for (ExaminationQuestion examinationQuestion : questions) {
			System.out.println(examinationQuestion.getExaminationQuestion_title());
		}
	}
	
	@Test
	public void test4(){
		List<ExaminationQuestion> list = examinationQustionService.getQuestionByPost(1, "单选题");
		for (ExaminationQuestion examinationQuestion : list) {
			System.out.println(examinationQuestion.getExaminationQuestion_title());
			//System.out.println(examinationQuestion.getOptions().get(0).getExaminationQuestion_value());
		}
	}
	
	@Test
	public void test5(){
		ExaminationQuestion question = new ExaminationQuestion();
		System.out.println(examinationQustionService.getQuestionById("1").getExaminationQuestion_title());
		question.setExaminationQuestion_title("这是测试第二次修改试题的了");
		examinationQustionService.updateQuestion(question);
		System.out.println("修改之后"+examinationQustionService.getQuestionById("1").getExaminationQuestion_title());
		
	}
}
