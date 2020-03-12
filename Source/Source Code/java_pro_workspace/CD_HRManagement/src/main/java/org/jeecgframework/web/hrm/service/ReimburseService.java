package org.jeecgframework.web.hrm.service;

import org.jeecgframework.web.hrm.bean.ExpenseProject;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.system.pojo.base.TSReimDetails;
import org.jeecgframework.web.system.pojo.base.TSReimburse;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报销清单录入
 */
@Service
@Transactional
public interface ReimburseService {
    /**
     * 根据Id 查询报销详情
     * @param id
     * @return
     */
    TSReimburse findReimburse (String id);

    /**
     * 根据报销ID查询报销明细
     * @param reimburseId
     * @return
     */
    List<TSReimDetails> findReimDetails(String reimburseId);

    /**
     * 根据USERID查询报销集合
     * @param userId
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String,Object>> getReimburseList(String userId, int page, int rows, Date startDate, Date endDate);
    List<Map<String,Object>> getReimburseList2(String userId);


    void addReimburse(Map<String,Object> map) throws Exception;
    void addReimDetails(List<TSReimDetails> tsReimDetails);

    void updateReimburse(Map<String,Object> map) throws Exception;
    void updateReimDetails(List<TSReimDetails> tsReimDetails);

    List<Map<String,Object>> findAllowance();

    Map<String,Object> findCountryByName(String name);

    void deleteReimDetailsById(String reimburseId);

    /**
     * 查询所有项目
     * @return
     */
    List<ExpenseProject> getProjectList();

    /**
     * 根据项目名称查询项目
     * @param name
     * @return
     */
    ExpenseProject findByProjectName(String name);
    /**
     * 权限操作查询报销集合
     */
    List<Map<String,Object>> getReimburseListByAll(int page,int rows,String userName,Date startDate,Date endDate);

    /**
     * 查询所有在职用户
     * @return
     */
    List<TSUser> findAllUser();

    void deleteById(String id);
}
