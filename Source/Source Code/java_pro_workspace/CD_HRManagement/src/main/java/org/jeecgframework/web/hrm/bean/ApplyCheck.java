package org.jeecgframework.web.hrm.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_hrm_apply_check")
public class ApplyCheck {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "apply_id")
    private String apply_id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "remark")
    private String remark;
    @Column(name = "status")
    private String status;
    @Column(name = "check_time")
    private Date checkTime;
    @Column(name = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
