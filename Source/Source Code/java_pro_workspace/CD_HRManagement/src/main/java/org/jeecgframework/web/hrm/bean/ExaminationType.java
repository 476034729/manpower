package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_hrm_exam_examination_type")
public class ExaminationType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column( name = "examinationType_id")
	private int examinationType_id ;
	
	@Column( name = "examinationType_name")
	private String examinationType_name;
	
	@Column( name = "examination_id")
	private int examination_id;

	public int getExaminationType_id() {
		return examinationType_id;
	}

	public void setExaminationType_id(int examinationType_id) {
		this.examinationType_id = examinationType_id;
	}

	public String getExaminationType_name() {
		return examinationType_name;
	}

	public void setExaminationType_name(String examinationType_name) {
		this.examinationType_name = examinationType_name;
	}

	public int getExamination_id() {
		return examination_id;
	}

	public void setExamination_id(int examination_id) {
		this.examination_id = examination_id;
	}
	
}
