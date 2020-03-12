package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_hrm_exam_examination_question_option")
public class ExaminationQuestionOption implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "examinationQuestion_option_id")
	private int examinationQuestion_option_id;

	@Column(name = "examinationQuestion_key")
	private String examinationQuestion_key;

	@Column(name = "examinationQuestion_value")
	private String examinationQuestion_value;

	@Column(name = "examinationQuestion_id")
	private String examinationQuestion_id;
	
	public String getExaminationQuestion_id() {
		return examinationQuestion_id;
	}

	public void setExaminationQuestion_id(String examinationQuestion_id) {
		this.examinationQuestion_id = examinationQuestion_id;
	}

	public int getExaminationQuestion_option_id() {
		return examinationQuestion_option_id;
	}

	public void setExaminationQuestion_option_id(int examinationQuestion_option_id) {
		this.examinationQuestion_option_id = examinationQuestion_option_id;
	}

	public String getExaminationQuestion_key() {
		return examinationQuestion_key;
	}

	public void setExaminationQuestion_key(String examinationQuestion_key) {
		this.examinationQuestion_key = examinationQuestion_key;
	}

	public String getExaminationQuestion_value() {
		return examinationQuestion_value;
	}

	public void setExaminationQuestion_value(String examinationQuestion_value) {
		this.examinationQuestion_value = examinationQuestion_value;
	}
	
}
