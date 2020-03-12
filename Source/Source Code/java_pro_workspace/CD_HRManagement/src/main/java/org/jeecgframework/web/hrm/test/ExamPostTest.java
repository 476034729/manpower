package org.jeecgframework.web.hrm.test;

import java.util.List;

import org.jeecgframework.web.hrm.bean.ExamPost;
import org.jeecgframework.web.hrm.bean.Examination;
import org.jeecgframework.web.hrm.bean.ExaminationQuestion;
import org.jeecgframework.web.hrm.bean.ExaminationQuestionOption;
import org.jeecgframework.web.hrm.bean.ExaminationType;
import org.jeecgframework.web.hrm.service.IExamPostService;
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
public class ExamPostTest {
	@Autowired
	private IExamPostService examPostService;
	
	@Test
	public void test1(){
		ExamPost post = examPostService.getByPostId(1);
		System.out.println(post.getPost_name());
	}
}
