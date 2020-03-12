package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 考勤记录实现类
 */
public interface IAttendanceRecordService extends CommonService {

    /**
     * 根据时间（yyyy-mm）获取所有考勤记录
     * @param chooseTime
     * @return
     */
    public List<AttendanceRecordResult> getAllAttendanceRecords(String chooseTime);

    /**
     * 每年1月1日自动初始化员工月份加班表
     */
    void insertAllMonthOvertime();

    /**
     * 根据用户id查询该月的累积加班时间
     * @param userId
     * @return
     */
    Double getSunMonthOvertime(String userId,Integer month);

    /**
     * 保存加班申请
     * @param overtimeApply
     * @param startDate
     * @param endDate
     */
    void saveOvertimeApply(HrmOvertimeApply overtimeApply, String[] startDate, String[] endDate,String[] countTime,String[] surplusTime, TSUser tsUser, Integer copy,Integer immediateSupervisorExamine) throws ParseException;

    /**
     * 查询加班申请列表
     * @param tsUser
     * @param roleType
     * @return
     */
    List<HrmOvertimeApply> findOvertimeApplyList(TSUser tsUser, Integer roleType, Integer status, String userName, Date startDate,Date endDate);

    /**
     * 为新员工添加该年的加班时间记录
     * @param user
     */
    void insertNewEmpMonthOvertime(TSUser user);

    /**
     * 根据id查询加班申请记录
     * @param id
     * @return
     */
    HrmOvertimeApply getOvertimeApplyById(String id);

    /**
     * 加班申请审批保存
     * @param overtimeApplyId
     * @param roleType
     * @param immediateSupervisorExamine
     * @param immediateSupervisorSign
     * @param copyType
     * @param personnelMattersName
     * @param divisionManagerExamine
     * @param divisionManagerSign
     * @param personnelMattersStatus
     */
    void saveOvertimeApplyExamination(String overtimeApplyId,Integer roleType,Integer immediateSupervisorExamine,String immediateSupervisorSign,Integer copyType,String personnelMattersName,
                                      Integer divisionManagerExamine,String divisionManagerSign,Integer personnelMattersStatus,String adminName);

    /***
     * 保存用户加班申请确认
     * @param overtimeApplyId
     */
    Boolean saveUserConfirm(String overtimeApplyId);

    /**
     * 保存人事加班申请确认
     * @param overtimeApplyId
     * @return
     */
    Boolean saveAdminConfirm(String overtimeApplyId);

    /**
     * 获取用户的当月可换休的时间
     * @param userId
     * @param date
     * @return
     */
    Double countBreakOff(String userId,String[] date);

    /**
     * 获取当前用户的年假使用天数
     * @param userId
     * @param year
     * @return
     */
    Double countAnnualLeave(String userId,Integer year);

    /**
     * 获取用户的当年的病假剩余天数
     * @param userId
     * @return
     */
    Double countSickLeave(String userId,Integer year);

    /**
     * 保存换休申请
     * @param changeOfRestApply
     * @param user
     * @param startDate
     * @param endDate
     */
    void saveVacationapply(HrmChangeOfRestApply changeOfRestApply,TSUser user,String[] startDate, String[] endDate);

    /**
     * 查询换休申请列表
     * @param tsUser
     * @param roleType
     * @param status
     * @param userName
     * @param startDate
     * @param endDate
     * @return
     */
    List<HrmChangeOfRestApply> findVacationApplyList(TSUser tsUser, Integer roleType, Integer status, String userName, Date startDate,Date endDate);

    /**
     *
     * @param id
     * @return
     */
    HrmChangeOfRestApply getVacationApplyById(String id);

    /**
     * 保存换休审批
     * @param vacationId
     * @param roleType
     * @param immediate_supervisor_examine
     * @param immediate_supervisor_advice
     * @param division_manager_examine
     * @param division_manager_advice
     */
    void saveVacationapplyExamination(String vacationId,Integer roleType,Integer immediate_supervisor_examine,String immediate_supervisor_advice,
                                      Integer division_manager_examine,String division_manager_advice,String immediate_supervisor_sign,String division_manager_sign);

    /**
     * 换休申请用户确认
     * @param vacationId
     * @return
     */
    Boolean saveUserConfirmVacationApply(String vacationId);

    /**
     * 人事换休关闭
     * @param vacationId
     * @return
     */
    Boolean saveAdminConfirmVacationApply(String vacationId,String staff);

    /**
     * 每年一月一号自动插入正式员工的该年的年假记录
     */
    void insertAnnualLeaveRecordAuto();

    /**
     * 每月最后一天自动为正式员工增加一天的年假数
     */
    void updateAnnualLeaveRecordAuto();

    /**
     * 为新增的正式工增加
     * @param user
     */
    void insertAnnunalLeaveForNewUser(TSUser user);

    /**
     * 查询所有员工的年假统计
     * @return
     */
    List<HrmAnnualLeaveStatistics> findAllAnnualLeaveStatistics();

    /**
     * 保存年假统计编辑
     * @param idList
     * @param beforeList
     * @param thisYearList
     * @param sumDayList
     * @param surplusDayList
     * @param remarkList
     */
    String saveAnnualVacation(List<String> idList,List<Double> beforeList,List<Double> thisYearList,List<Double> sumDayList,List<Double> surplusDayList,List<String> remarkList);

    /**
     * 每月月初第一天自动为正式员工初始化考勤记录数据 
     */
    void insertAttendanceRecordAuto();

    /**
     * 考勤记录右边的状态数据查询 某年某月
     */
    List<HrmAttendanceRecord> getAttendanceRecordList( int year, int month);

    /**
     * 添加考勤记录(从入职当前时间添加)
     * @param user
     */
    void insertNewEmpAttendanceRecord(TSUser user);
    /**
     * 出差申请固定显示
     * @param id
     * @return
     */
    TSUser getUserById(String id);
    /**
     * 保存出差申请
     * @param file
     * @param request
     * @param user
     * @param hrmBusinessApply
     * @param sdate
     * @param edate
     */
    void saveBusinessApply(MultipartFile file, HttpServletRequest request, TSUser user,
                           HrmBusinessApply hrmBusinessApply, Date sdate, Date edate);
    /**
     * 查询出差信息 获取出差列表
     * @param user
     * @param roleType
     * @param status
     * @param applyName
     * @param startDate
     * @param endDate
     * @return
     */
    List<HrmBusinessApply> findBusinessApplyList(TSUser user, Integer roleType, Integer status, String applyName,
                                                 Date startDate, Date endDate);
    /**
     * 查询出差详细信息 BY id
     * @param businessId
     * @return
     */
    HrmBusinessApply findHrmBusinessById(String businessId);
    /**
     * 出差确认 自我确认
     * @param businessId
     * @return
     */
    Boolean saveUserConfirmBusinessApply(String businessId);
    /**
     * 出差人事关闭
     * @param businessId
     * @param userName
     * @return
     */
    Boolean saveAdminConfirmBusinessApply(String businessId, String userName);
}

