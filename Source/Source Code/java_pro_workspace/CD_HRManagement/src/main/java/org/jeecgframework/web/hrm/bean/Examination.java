package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_hrm_exam_examination")
public class Examination implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "examination_id")
	private int examination_id;
	
	@Column( name = "examination_type")
	private String examination_type;
	
	@Column( name = "post_id")
	private int post_id;
	
	public int getExamination_id() {
		return examination_id;
	}

	public void setExamination_id(int examination_id) {
		this.examination_id = examination_id;
	}

	public String getExamination_type() {
		return examination_type;
	}

	public void setExamination_type(String examination_type) {
		this.examination_type = examination_type;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

}
