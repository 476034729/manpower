package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author:luodan
 * @Description:换休申请
 * @Date:2018-11-01 16:16
 */
@Entity
@Table(name = "t_hrm_overtime_apply")
@JsonIgnoreProperties({"hrmOvertimes","hibernateLazyInitializer", "handler"})
public class HrmOvertimeApply {
    //审批意见(0、待审核1、同意2、不同意3、审核中4、不审核)
    public static final Integer IMEXAMINE = 0;
    public static final Integer AGREE = 1;
    public static final Integer DISAGREE = 2;
    public static final Integer EXAMINEING = 3;
    public static final Integer NOAUDIT = 4;
    //抄送类型1、行政2、部门经理
    public static final Integer ADMINISTRATION = 1;
    public static final Integer MANAGER = 2;
    //用户确认状态
    public static final Integer UNCONFIRM = 0;
    public static final Integer  CONFIRM = 1;

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "dept")
    private String dept;
    @Column(name = "overtime_reason")
    private String overtime_reason;
    @Column(name = "prebatch")
    private String prebatch;
    @Column(name = "deduct_meal_time")
    private Double deduct_meal_time;
    @Column(name = "count_month_overtime")
    private Double count_month_overtime;
    @Column(name = "immediate_supervisor_examine")
    private Integer immediate_supervisor_examine;
    @Column(name = "immediate_supervisor_sign")
    private String immediate_supervisor_sign;
    @Column(name = "division_manager_examine")
    private Integer division_manager_examine;
    @Column(name = "division_manager_sign")
    private String division_manager_sign;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "net_meter")
    private Double net_meter;
    @Column(name = "copy_type")
    private Integer copy_type;
    @Column(name = "copy_person")
    private String copy_person;
    @Column(name = "personnel_matters_status")
    private Integer personnel_matters_status;
    @Column(name = "personnel_matters_name")
    private String personnel_matters_name;
    @Column(name = "status")
    private Integer status;
    @OneToMany
    @JoinColumn(name = "overtime_apply_id")
    private List<HrmOvertime> hrmOvertimes;
    @Column(name = "user_status")
    private Integer user_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getOvertime_reason() {
        return overtime_reason;
    }

    public void setOvertime_reason(String overtime_reason) {
        this.overtime_reason = overtime_reason;
    }

    public String getPrebatch() {
        return prebatch;
    }

    public void setPrebatch(String prebatch) {
        this.prebatch = prebatch;
    }

    public Double getDeduct_meal_time() {
        return deduct_meal_time;
    }

    public void setDeduct_meal_time(Double deduct_meal_time) {
        this.deduct_meal_time = deduct_meal_time;
    }

    public Double getCount_month_overtime() {
        return count_month_overtime;
    }

    public void setCount_month_overtime(Double count_month_overtime) {
        this.count_month_overtime = count_month_overtime;
    }

    public Integer getImmediate_supervisor_examine() {
        return immediate_supervisor_examine;
    }

    public void setImmediate_supervisor_examine(Integer immediate_supervisor_examine) {
        this.immediate_supervisor_examine = immediate_supervisor_examine;
    }

    public String getImmediate_supervisor_sign() {
        return immediate_supervisor_sign;
    }

    public void setImmediate_supervisor_sign(String immediate_supervisor_sign) {
        this.immediate_supervisor_sign = immediate_supervisor_sign;
    }

    public Integer getDivision_manager_examine() {
        return division_manager_examine;
    }

    public void setDivision_manager_examine(Integer division_manager_examine) {
        this.division_manager_examine = division_manager_examine;
    }

    public String getDivision_manager_sign() {
        return division_manager_sign;
    }

    public void setDivision_manager_sign(String division_manager_sign) {
        this.division_manager_sign = division_manager_sign;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Double getNet_meter() {
        return net_meter;
    }

    public void setNet_meter(Double net_meter) {
        this.net_meter = net_meter;
    }

    public Integer getCopy_type() {
        return copy_type;
    }

    public void setCopy_type(Integer copy_type) {
        this.copy_type = copy_type;
    }

    public String getCopy_person() {
        return copy_person;
    }

    public void setCopy_person(String copy_person) {
        this.copy_person = copy_person;
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

    public List<HrmOvertime> getHrmOvertimes() {
        return hrmOvertimes;
    }

    public void setHrmOvertimes(List<HrmOvertime> hrmOvertimes) {
        this.hrmOvertimes = hrmOvertimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }
}
