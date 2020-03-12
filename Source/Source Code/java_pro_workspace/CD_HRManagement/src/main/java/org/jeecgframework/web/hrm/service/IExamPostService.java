package org.jeecgframework.web.hrm.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.ExamPost;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface IExamPostService extends CommonService{
	//查询所有的面试岗位
	List<ExamPost> getAllExamPosts();
	
	//根据职位id查询zhiw
	ExamPost getByPostId(int postId);
}

