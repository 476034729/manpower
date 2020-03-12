package org.jeecgframework.web.system.pojo.base;


import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @authortangzhen
 * @createDate2019-07-17
 */
@Entity
@Table(name = "t_s_reim_details")
@PrimaryKeyJoinColumn(name = "id")
public class TSReimDetails extends IdEntity implements Serializable {
    private static final long serialVersionUID = -4515023207078955053L;
    private String reimburseId;


    private Date createDate;
    private Integer expenseType;

    private String details;

    private BigDecimal costFee;

    private Timestamp update_time;

    @Column(name = "reimburse_id")
    public String getReimburseId() {
        return reimburseId;
    }

    public void setReimburseId(String reimburseId) {
        this.reimburseId = reimburseId;
    }
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name = "expense_type")
    public Integer getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(Integer expenseType) {
        this.expenseType = expenseType;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Column(name = "cost_fee")
    public BigDecimal getCostFee() {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public TSReimDetails(){

    }
}
