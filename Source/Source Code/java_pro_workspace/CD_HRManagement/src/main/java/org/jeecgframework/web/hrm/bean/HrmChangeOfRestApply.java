package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:luodan
 * @Description:换休申请
 * @Date:2018-11-01 15:16
 */
@Entity
@Table(name = "t_hrm_changeofrest_apply")
@JsonIgnoreProperties({"hrmChangofrestTimes","hibernateLazyInitializer", "handler"})
public class HrmChangeOfRestApply implements Serializable {
    //休假地点(1、国内2、国外)
    public static final Integer DOMESTIC = 1;
    public static final Integer ABROAD = 2;
    //审批意见(0、待审核1、同意2、不同意3、不处理,3审核中)
    public static final Integer IMEXAMINE = 0;
    public static final Integer AGREE = 1;
    public static final Integer DISAGREE = 2;
    public static final Integer NOHANDEL = 3;
    public static final Integer EXAMING = 3;

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "dept")
    private String dept;
    @Column(name = "vacation_type")
    private String vacation_type;
    @Column(name = "leave_start")
    private Date leave_start;
    @Column(name = "leave_end")
    private Date leave_end;
    @Column(name = "count_day")
    private Double count_day;
    @Column(name = "leave_reason")
    private String leave_reason;
    @Column(name = "vacation_place")
    private Integer vacation_place;
    @Column(name = "contact_information")
    private String contact_information;
    @Column(name = "instruction")
    private String instruction;
    @Column(name = "applicant_sign")
    private String applicant_sign;
    @Column(name = "apply_date")
    private Date apply_date;
    @Column(name = "immediate_supervisor_examine")
    private Integer immediate_supervisor_examine;
    @Column(name = "immediate_supervisor_advice")
    private String immediate_supervisor_advice;
    @Column(name = "division_manager_examine")
    private Integer division_manager_examine;
    @Column(name = "division_manager_advice")
    private String division_manager_advice;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;
    @Column(name = "immediate_supervisor_sign")
    private String immediate_supervisor_sign;
    @Column(name = "division_manager_sign")
    private String division_manager_sign;
    @Column(name = "status")
    private Integer status;
    @Column(name = "personnel_matters_status")
    private Integer personnel_matters_status;
    @Column(name = "personnel_matters_name")
    private String personnel_matters_name;
    @Column(name = "user_status")
    private Integer user_status;
    @OneToMany
    @JoinColumn(name = "changofrest_id")
    private List<HrmChangofrestTime> hrmChangofrestTimes;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "count_time")
    private Double count_time;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getVacation_type() {
        return vacation_type;
    }

    public void setVacation_type(String vacation_type) {
        this.vacation_type = vacation_type;
    }

    public Date getLeave_start() {
        return leave_start;
    }

    public void setLeave_start(Date leave_start) {
        this.leave_start = leave_start;
    }

   public Date getLeave_end() {
        return leave_end;
    }

    public void setLeave_end(Date leave_end) {
        this.leave_end = leave_end;
    }

    public Double getCount_day() {
        return count_day;
    }

    public void setCount_day(Double count_day) {
        this.count_day = count_day;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public Integer getVacation_place() {
        return vacation_place;
    }

    public void setVacation_place(Integer vacation_place) {
        this.vacation_place = vacation_place;
    }

    public String getContact_information() {
        return contact_information;
    }

    public void setContact_information(String contact_information) {
        this.contact_information = contact_information;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getApplicant_sign() {
        return applicant_sign;
    }

    public void setApplicant_sign(String applicant_sign) {
        this.applicant_sign = applicant_sign;
    }

    public Date getApply_date() {
        return apply_date;
    }

    public void setApply_date(Date apply_date) {
        this.apply_date = apply_date;
    }

    public Integer getImmediate_supervisor_examine() {
        return immediate_supervisor_examine;
    }

    public void setImmediate_supervisor_examine(Integer immediate_supervisor_examine) {
        this.immediate_supervisor_examine = immediate_supervisor_examine;
    }

    public String getImmediate_supervisor_advice() {
        return immediate_supervisor_advice;
    }

    public void setImmediate_supervisor_advice(String immediate_supervisor_advice) {
        this.immediate_supervisor_advice = immediate_supervisor_advice;
    }

    public Integer getDivision_manager_examine() {
        return division_manager_examine;
    }

    public void setDivision_manager_examine(Integer division_manager_examine) {
        this.division_manager_examine = division_manager_examine;
    }

    public String getDivision_manager_advice() {
        return division_manager_advice;
    }

    public void setDivision_manager_advice(String division_manager_advice) {
        this.division_manager_advice = division_manager_advice;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public String getImmediate_supervisor_sign() {
        return immediate_supervisor_sign;
    }

    public void setImmediate_supervisor_sign(String immediate_supervisor_sign) {
        this.immediate_supervisor_sign = immediate_supervisor_sign;
    }

    public String getDivision_manager_sign() {
        return division_manager_sign;
    }

    public void setDivision_manager_sign(String division_manager_sign) {
        this.division_manager_sign = division_manager_sign;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPersonnel_matters_status() {
        return personnel_matters_status;
    }

    public void setPersonnel_matters_status(Integer personnel_matters_status) {
        this.personnel_matters_status = personnel_matters_status;
    }

    public String getPersonnel_matters_name() {
        return personnel_matters_name;
    }

    public void setPersonnel_matters_name(String personnel_matters_name) {
        this.personnel_matters_name = personnel_matters_name;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public List<HrmChangofrestTime> getHrmChangofrestTimes() {
        return hrmChangofrestTimes;
    }

    public void setHrmChangofrestTimes(List<HrmChangofrestTime> hrmChangofrestTimes) {
        this.hrmChangofrestTimes = hrmChangofrestTimes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Double getCount_time() {
        return count_time;
    }

    public void setCount_time(Double count_time) {
        this.count_time = count_time;
    }
}
