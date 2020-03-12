package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @authortangzhen
 * @createDate2019-08-19
 */

@Entity
@Table(name = "t_s_expense_project")
public class ExpenseProject implements Serializable {
    private static final long serialVersionUID = -3940962884805143195L;
    @Id
    private int id;

    private String attribution;

    @Column(name = "project_name")
    private String projectName;
    @Column(name = "project_leader")
    private String projectLeader;

    private String code;

    @Column(name = "code_status")
    private int codeStatus;

    @Column(name = "update_time")
    private Date updateTime;

    private String customer;

    private BigDecimal value;

    private String comments;

    @Column(name = "contract_status")
    private String contractStatus;

    private String area;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "comtact_person")
    private String comtactPerson;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComtactPerson() {
        return comtactPerson;
    }

    public void setComtactPerson(String comtactPerson) {
        this.comtactPerson = comtactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(int codeStatus) {
        this.codeStatus = codeStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
