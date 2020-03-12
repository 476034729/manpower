package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:luodan
 * @Description:加班时间
 * @Date:2018-11-01 16:16
 */
@Entity
@Table(name = "t_hrm_overtime")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrmOvertime implements Serializable {
    //审核状态（0、未审核1、通过2、不同意）
    public static final Integer UNAUDITED = 0;
    public static final Integer AGREE = 1;
    public static final Integer DISAGREE = 2;
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overtime_apply_id")
    private HrmOvertimeApply hrmOvertimeApply;
    @Column(name = "start_time")
    private Date start_time;
    @Column(name = "end_time")
    private Date end_time;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "modify_time")
    private Date modify_time;
    @Column(name = "status")
    private Integer status;
    @Column(name = "count_time")
    private Double count_time;
    @Column(name = "surplus_time")
    private Double surplus_time;
    @Column(name = "by_time")
    private Date by_time;
    @Column(name = "user_id")
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HrmOvertimeApply getHrmOvertimeApply() {
        return hrmOvertimeApply;
    }

    public void setHrmOvertimeApply(HrmOvertimeApply hrmOvertimeApply) {
        this.hrmOvertimeApply = hrmOvertimeApply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public Double getCount_time() {
        return count_time;
    }

    public void setCount_time(Double count_time) {
        this.count_time = count_time;
    }

    public Double getSurplus_time() {
        return surplus_time;
    }

    public void setSurplus_time(Double surplus_time) {
        this.surplus_time = surplus_time;
    }

    public Date getBy_time() {
        return by_time;
    }

    public void setBy_time(Date by_time) {
        this.by_time = by_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
