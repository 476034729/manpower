package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;
import java.util.Date;

public class ApplyCheckWithApply implements Serializable {
    private String id;
    private String apply_id;
    private String applyPerson;
    private String type;
    private String beginTime;
    private String endTime;
    private String status;

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
