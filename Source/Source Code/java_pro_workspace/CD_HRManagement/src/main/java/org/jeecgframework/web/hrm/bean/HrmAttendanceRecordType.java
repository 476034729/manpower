package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 考勤记录类型表 
 * @author 刘一
 */
@Entity
@Table(name = "t_hrm_attenrecord_type")
public class HrmAttendanceRecordType {
	@Id
	@Column(name = "id")
	private String id;
	//0-10
	@Column(name = "type")
	private Integer type;
	 /*考勤类型(0-正常；（默认值0）；1-年假1；2-年假0.5；3-事假1；4-事假0.5；5-病假1；
    6-病假0.5；7-换休1；8-换休0.5；9-出差1；10-出差0.5)*/
	@Column(name = "status")
	private String  status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
