package org.jeecgframework.web.hrm.bean;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_hrm_workhour_cm")
public class WorkHourCM implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "work_day")
    private Date work_day;
    @Column(name = "update_day")
    private Date update_day;
    @Column(name = "amorpm")
    private String amOrPm;
    @Column(name = "project_code")
    private String project_code;
    @Column(name = "work_details")
    private String work_details;
    @Column(name = "number")
    private Integer number;
    
    
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

    public Date getWork_day() {
        return work_day;
    }

    public void setWork_day(Date work_day) {
        this.work_day = work_day;
    }

    public Date getUpdate_day() {
        return update_day;
    }

    public void setUpdate_day(Date update_day) {
        this.update_day = update_day;
    }

    public String getAmOrPm() {
        return amOrPm;
    }

    public void setAmOrPm(String amOrPm) {
        this.amOrPm = amOrPm;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getWork_details() {
        return work_details;
    }

    public void setWork_details(String work_details) {
        this.work_details = work_details;
    }

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "WorkHour [id=" + id + ", user_id=" + user_id + ", work_day=" + work_day + ", update_day=" + update_day
				+ ", amOrPm=" + amOrPm + ", project_code=" + project_code + ", work_details=" + work_details
				+ ", number=" + number + "]";
	}
    
}
