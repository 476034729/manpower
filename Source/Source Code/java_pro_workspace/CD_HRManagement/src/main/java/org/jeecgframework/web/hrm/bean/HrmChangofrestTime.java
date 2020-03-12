package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:luodan
 * @Description:换休时间表
 * @Date:2018-11-01 15:16
 */
@Entity
@Table(name = "t_hrm_changofrest_time")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrmChangofrestTime implements Serializable {
    //审批意见(0、待审核1、同意2、不同意
    public static final Integer IMEXAMINE = 0;
    public static final Integer AGREE = 1;
    public static final Integer DISAGREE = 2;

    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changofrest_id")
    private HrmChangeOfRestApply hrmChangeOfRestApply;
    @Column(name = "start_time")
    private Date start_time;
    @Column(name = "end_time")
    private Date end_time;
    @Column(name = "status")
    private Integer status;
    @Column(name = "vacation_type")
    private String vacation_type;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "modify_time")
    private Date modify_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVacation_type() {
        return vacation_type;
    }

    public void setVacation_type(String vacation_type) {
        this.vacation_type = vacation_type;
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

    public HrmChangeOfRestApply getHrmChangeOfRestApply() {
        return hrmChangeOfRestApply;
    }

    public void setHrmChangeOfRestApply(HrmChangeOfRestApply hrmChangeOfRestApply) {
        this.hrmChangeOfRestApply = hrmChangeOfRestApply;
    }

	@Override
	public String toString() {
		return "HrmChangofrestTime [id=" + id + ", hrmChangeOfRestApply=" + hrmChangeOfRestApply + ", start_time="
				+ start_time + ", end_time=" + end_time + ", status=" + status + ", vacation_type=" + vacation_type
				+ ", create_time=" + create_time + ", modify_time=" + modify_time + "]";
	}
    
}
