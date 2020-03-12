package org.jeecgframework.web.hrm.bean;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_hrm_role")
public class HrmRole implements Serializable {
    @Id
    @Column(name = "role_id")
    private String role_id;
    @Column(name = "role_name")
    private String role_name;
    @Column(name = "remark")
    private String remark;

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
