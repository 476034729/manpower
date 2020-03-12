package org.jeecgframework.web.system.pojo.base;

import java.math.BigDecimal;


/**
 * @authortangzhen
 * @createDate2019-07-19
 */
public class TSReimDetailsVO {
    private String reimburseId;


    private String createDate;
    private Integer expenseType;

    private String details;

    private BigDecimal costFee;

    public String getReimburseId() {
        return reimburseId;
    }

    public void setReimburseId(String reimburseId) {
        this.reimburseId = reimburseId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

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

    public BigDecimal getCostFee() {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee;
    }

    @Override
    public String toString() {
        return "TSReimDetailsVO{" +
                "reimburseId='" + reimburseId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", expenseType=" + expenseType +
                ", details='" + details + '\'' +
                ", costFee=" + costFee +
                '}';
    }
}
