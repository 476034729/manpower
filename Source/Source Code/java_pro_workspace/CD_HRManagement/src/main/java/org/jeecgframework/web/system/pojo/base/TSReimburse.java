package org.jeecgframework.web.system.pojo.base;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @authortangzhen
 * @createDate2019-07-17
 */
@Entity
@Table(name = "t_s_reimburse")
@PrimaryKeyJoinColumn(name = "id")
public class TSReimburse extends IdEntity implements Serializable {
    private static final long serialVersionUID = -4120909099594768773L;


    private String userId;

    private String projectNmae;

    private String expendName;

    private Timestamp applyDate;

    private Timestamp startTime;

    private Timestamp endTime;

    private String costCenterCode;

    private BigDecimal allowanceFee;
    private String currency;

    private String countryName;
    private Timestamp update_time;

    @Column(name = "allowance_fee")
    public BigDecimal getAllowanceFee() {
        return allowanceFee;
    }

    public void setAllowanceFee(BigDecimal allowanceFee) {
        this.allowanceFee = allowanceFee;
    }
    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Column(name = "project_name")
    public String getProjectNmae() {
        return projectNmae;
    }

    public void setProjectNmae(String projectNmae) {
        this.projectNmae = projectNmae;
    }
    @Column(name = "expend_name")
    public String getExpendName() {
        return expendName;
    }

    public void setExpendName(String expendName) {
        this.expendName = expendName;
    }
    @Column(name = "apply_date")
    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    @Column(name = "costcenter_code")
    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }
    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }



    public TSReimburse(){

    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
