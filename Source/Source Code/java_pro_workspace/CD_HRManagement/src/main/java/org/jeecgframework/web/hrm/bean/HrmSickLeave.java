package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_hrm_sick_leave")
public class HrmSickLeave implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "year")
    private Integer year;
    @Column(name = "total_days")
    private Double total_days;
    @Column(name = "days_remaining")
    private Double days_remaining;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getTotal_days() {
        return total_days;
    }

    public void setTotal_days(Double total_days) {
        this.total_days = total_days;
    }

    public Double getDays_remaining() {
        return days_remaining;
    }

    public void setDays_remaining(Double days_remaining) {
        this.days_remaining = days_remaining;
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
}
