package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:luodan
 * @Description:年假统计表
 * @Date:2018-11-26 14：42
 */
@Entity
@Table(name = "t_hrm_annual_leave_statistics")
public class HrmAnnualLeaveStatistics implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "previous_year")
    private Double previous_year;
    @Column(name = "this_year")
    private Double this_year;
    @Column(name = "count_annual_leave")
    private Double count_annual_leave;
    @Column(name = "changofrest_time")
    private String changofrest_time;
    @Column(name = "use_days")
    private Double use_days;
    @Column(name = "surplus_days")
    private Double surplus_days;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "modify_time")
    private Date modify_time;
    @Column(name = "previous_surplus_days")
    private Double previous_surplus_days;
    @Column(name = "this_surplus_days")
    private Double this_surplus_days;
    @Column(name = "year")
    private Integer year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getPrevious_year() {
        return previous_year;
    }

    public void setPrevious_year(Double previous_year) {
        this.previous_year = previous_year;
    }

    public Double getThis_year() {
        return this_year;
    }

    public void setThis_year(Double this_year) {
        this.this_year = this_year;
    }

    public Double getCount_annual_leave() {
        return count_annual_leave;
    }

    public void setCount_annual_leave(Double count_annual_leave) {
        this.count_annual_leave = count_annual_leave;
    }

    public String getChangofrest_time() {
        return changofrest_time;
    }

    public void setChangofrest_time(String changofrest_time) {
        this.changofrest_time = changofrest_time;
    }

    public Double getUse_days() {
        return use_days;
    }

    public void setUse_days(Double use_days) {
        this.use_days = use_days;
    }

    public Double getSurplus_days() {
        return surplus_days;
    }

    public void setSurplus_days(Double surplus_days) {
        this.surplus_days = surplus_days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Double getPrevious_surplus_days() {
        return previous_surplus_days;
    }

    public void setPrevious_surplus_days(Double previous_surplus_days) {
        this.previous_surplus_days = previous_surplus_days;
    }

    public Double getThis_surplus_days() {
        return this_surplus_days;
    }

    public void setThis_surplus_days(Double this_surplus_days) {
        this.this_surplus_days = this_surplus_days;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
