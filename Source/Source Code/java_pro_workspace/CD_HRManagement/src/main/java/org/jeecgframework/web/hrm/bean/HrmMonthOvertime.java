package org.jeecgframework.web.hrm.bean;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:luodan
 * @Description:换休申请
 * @Date:2018-11-14 16:16
 */
@Entity
@Table(name = "t_hrm_month_overtime")
public class HrmMonthOvertime implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "year")
    private Integer year;
    @Column(name = "january_overtime")
    private Double january_overtime;
    @Column(name = "january_surtime")
    private Double january_surtime;
    @Column(name = "february_overtime")
    private Double february_overtime;
    @Column(name = "february_surtime")
    private Double february_surtime;
    @Column(name = "march_overtime")
    private Double march_overtime;
    @Column(name = "march_surtime")
    private Double march_surtime;
    @Column(name = "april_overtime")
    private Double april_overtime;
    @Column(name = "april_surtime")
    private Double april_surtime;
    @Column(name = "may_overtime")
    private Double may_overtime;
    @Column(name = "may_surtime")
    private Double may_surtime;
    @Column(name = "june_overtime")
    private Double june_overtime;
    @Column(name = "june_surtime")
    private Double june_surtime;
    @Column(name = "july_overtime")
    private Double july_overtime;
    @Column(name = "july_surtime")
    private Double july_surtime;
    @Column(name = "august_overtime")
    private Double august_overtime;
    @Column(name = "august_surtime")
    private Double august_surtime;
    @Column(name = "september_overtime")
    private Double september_overtime;
    @Column(name = "september_surtime")
    private Double september_surtime;
    @Column(name = "october_overtime")
    private Double october_overtime;
    @Column(name = "october_surtime")
    private Double october_surtime;
    @Column(name = "november_overtime")
    private Double november_overtime;
    @Column(name = "november_surtime")
    private Double november_surtime;
    @Column(name = "december_overtime")
    private Double december_overtime;
    @Column(name = "december_surtime")
    private Double december_surtime;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getFebruary_surtime() {
        return february_surtime;
    }

    public void setFebruary_surtime(Double february_surtime) {
        this.february_surtime = february_surtime;
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

    public Double getJanuary_overtime() {
        return january_overtime;
    }

    public void setJanuary_overtime(Double january_overtime) {
        this.january_overtime = january_overtime;
    }

    public Double getJanuary_surtime() {
        return january_surtime;
    }

    public void setJanuary_surtime(Double january_surtime) {
        this.january_surtime = january_surtime;
    }

    public Double getFebruary_overtime() {
        return february_overtime;
    }

    public void setFebruary_overtime(Double february_overtime) {
        this.february_overtime = february_overtime;
    }

    public Double getMarch_overtime() {
        return march_overtime;
    }

    public void setMarch_overtime(Double march_overtime) {
        this.march_overtime = march_overtime;
    }

    public Double getMarch_surtime() {
        return march_surtime;
    }

    public void setMarch_surtime(Double march_surtime) {
        this.march_surtime = march_surtime;
    }

    public Double getApril_overtime() {
        return april_overtime;
    }

    public void setApril_overtime(Double april_overtime) {
        this.april_overtime = april_overtime;
    }

    public Double getApril_surtime() {
        return april_surtime;
    }

    public void setApril_surtime(Double april_surtime) {
        this.april_surtime = april_surtime;
    }

    public Double getMay_overtime() {
        return may_overtime;
    }

    public void setMay_overtime(Double may_overtime) {
        this.may_overtime = may_overtime;
    }

    public Double getMay_surtime() {
        return may_surtime;
    }

    public void setMay_surtime(Double may_surtime) {
        this.may_surtime = may_surtime;
    }

    public Double getJune_overtime() {
        return june_overtime;
    }

    public void setJune_overtime(Double june_overtime) {
        this.june_overtime = june_overtime;
    }

    public Double getJune_surtime() {
        return june_surtime;
    }

    public void setJune_surtime(Double june_surtime) {
        this.june_surtime = june_surtime;
    }

    public Double getJuly_overtime() {
        return july_overtime;
    }

    public void setJuly_overtime(Double july_overtime) {
        this.july_overtime = july_overtime;
    }

    public Double getJuly_surtime() {
        return july_surtime;
    }

    public void setJuly_surtime(Double july_surtime) {
        this.july_surtime = july_surtime;
    }

    public Double getAugust_overtime() {
        return august_overtime;
    }

    public void setAugust_overtime(Double august_overtime) {
        this.august_overtime = august_overtime;
    }

    public Double getAugust_surtime() {
        return august_surtime;
    }

    public void setAugust_surtime(Double august_surtime) {
        this.august_surtime = august_surtime;
    }

    public Double getSeptember_overtime() {
        return september_overtime;
    }

    public void setSeptember_overtime(Double september_overtime) {
        this.september_overtime = september_overtime;
    }

    public Double getSeptember_surtime() {
        return september_surtime;
    }

    public void setSeptember_surtime(Double september_surtime) {
        this.september_surtime = september_surtime;
    }

    public Double getOctober_overtime() {
        return october_overtime;
    }

    public void setOctober_overtime(Double october_overtime) {
        this.october_overtime = october_overtime;
    }

    public Double getOctober_surtime() {
        return october_surtime;
    }

    public void setOctober_surtime(Double october_surtime) {
        this.october_surtime = october_surtime;
    }

    public Double getNovember_overtime() {
        return november_overtime;
    }

    public void setNovember_overtime(Double november_overtime) {
        this.november_overtime = november_overtime;
    }

    public Double getNovember_surtime() {
        return november_surtime;
    }

    public void setNovember_surtime(Double november_surtime) {
        this.november_surtime = november_surtime;
    }

    public Double getDecember_overtime() {
        return december_overtime;
    }

    public void setDecember_overtime(Double december_overtime) {
        this.december_overtime = december_overtime;
    }

    public Double getDecember_surtime() {
        return december_surtime;
    }

    public void setDecember_surtime(Double december_surtime) {
        this.december_surtime = december_surtime;
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
}
