package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_hrm_project_code")
public class ProjectCode implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "project_name")
    private String project_name;
    @Column(name = "project_eg_code")
    private String project_eg_code;
    @Column(name = "code")
    private String code;
    @Column(name = "type")
    private String type;
    @Column(name = "status")
    private String status;
    @Column(name = "remark")
    private String remark;
    @Column(name = "rank")
    private String rank;
    
    @Column(name = "enable")
    private Integer enable;//是否可用0可用1不可用
    @Column(name = "has_son")
    private Integer has_son;//'是否有子项目0没有1有'
    @Column(name = "code_contact")
    private String code_contact;//'主项目和子项目关联关系',

    @Column(name = "num_code")
    private String numCode;
    
    
    
    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getHas_son() {
		return has_son;
	}

	public void setHas_son(Integer has_son) {
		this.has_son = has_son;
	}

	public String getCode_contact() {
		return code_contact;
	}

	public void setCode_contact(String code_contact) {
		this.code_contact = code_contact;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_eg_code() {
        return project_eg_code;
    }

    public void setProject_eg_code(String project_eg_code) {
        this.project_eg_code = project_eg_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ProjectCode() {
    }

    public ProjectCode(String project_name, String project_eg_code, String code) {
        this.project_name = project_name;
        this.project_eg_code = project_eg_code;
        this.code = code;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }
}
