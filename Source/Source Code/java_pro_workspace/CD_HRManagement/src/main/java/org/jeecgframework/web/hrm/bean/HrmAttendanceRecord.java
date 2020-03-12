package org.jeecgframework.web.hrm.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 考勤员工记录表
 */
@Entity
@Table(name = "t_hrm_attendance_record")
public class HrmAttendanceRecord implements Serializable {
	
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
   
    //用户的ID
    @Column(name = "user_id")
    private String userId;
    
    //考勤姓名
    @Column(name = "user_name")
    private String userName;
    
    //考勤员工工号
    @Column(name = "job_num")
    private String jobNum;

    /*考勤类型(0-正常；（默认值0）；1-年假1；2-年假0.5；3-事假1；4-事假0.5；5-病假1；
    6-病假0.5；7-换休1；8-换休0.5；9-出差1；10-出差0.5)*/
    @Column(name = "type")
    private Integer type;
    
    //小时数
    @Column(name = "hour")
    private Double hour;
    
    //创建时间(每月月初自动生成  每个员工当月的天数的信息）
    @Column(name = "creat_time")
    private Date creatTime;
    
    //操作时间-标记考勤操作的具体时间（yyyy-MM-dd HH:mm:ss）
    @Column(name = "oper_time")
    private Date operTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
    
    
    
}
