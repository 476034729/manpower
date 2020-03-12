package org.jeecgframework.web.hrm.service;

import java.util.List;

import org.jeecgframework.web.hrm.bean.Examination;
import org.jeecgframework.web.hrm.bean.ExaminationQuestion;
import org.jeecgframework.web.hrm.bean.ExaminationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface IExamQuestionService {
	//根据岗位查询对应的所有试题（包括单选，多选文本题）
	//1.根据题目类型和岗位查询试题题型类型
	
		//根据岗位找到试题卷总类型
		Examination getExaminationByPostId(int postId);
		
		//根据题目类型和试题卷总类型ID查询到试题题型类型
		ExaminationType getExaminationType(String typeName,int examinationId);
		
	//2.根据试题类型查出试题题目
		List<ExaminationQuestion> getExaminationQuestionByType(int examinationTypeId);
	
	//根据岗位找到试题题目
		List<ExaminationQuestion> getQuestionByPost(int postId,String typeName);
	//测试
		void aa();
		
	//根据试题id查询试题
		ExaminationQuestion getQuestionById(String questionId);
		
	//修改试题信息（答案，题目，试题补充，以及选项及选项内容）
		ExaminationQuestion updateQuestion(ExaminationQuestion question);
		
	
}

