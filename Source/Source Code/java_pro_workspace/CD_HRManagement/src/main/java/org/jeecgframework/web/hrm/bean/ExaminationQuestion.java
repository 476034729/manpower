package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "t_hrm_exam_examination_question")
public class ExaminationQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "examinationQuestion_id")
	private String examinationQuestion_id;
	
	@Column(name = "examinationQuestion_title")
	private String examinationQuestion_title;

	@Column(name = "examinationQuestion_score")
	private String examinationQuestion_score;

	@Column(name = "examinationQuestion_content")
	private String examinationQuestion_content;

	@Column(name = "examinationQuestion_num")
	private int examinationQuestion_num;// 题目的序号
	
	@Column(name = "examination_answer")
	private String examination_answer;

	@Column(name = "examinationType_id")
	private int examinationType_id;

	@Column(name = "examination_id")
	private int examination_id;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "examinationQuestion_id ")
	private List<ExaminationQuestionOption> options;
	
	public List<ExaminationQuestionOption> getOptions() {
		return options;
	}

	public void setOptions(List<ExaminationQuestionOption> options) {
		this.options = options;
	}

	public String getExamination_answer() {
		return examination_answer;
	}

	public void setExamination_answer(String examination_answer) {
		this.examination_answer = examination_answer;
	}

	public String getExaminationQuestion_title() {
		return examinationQuestion_title;
	}

	public void setExaminationQuestion_title(String examinationQuestion_title) {
		this.examinationQuestion_title = examinationQuestion_title;
	}

	public String getExaminationQuestion_score() {
		return examinationQuestion_score;
	}

	public void setExaminationQuestion_score(String examinationQuestion_score) {
		this.examinationQuestion_score = examinationQuestion_score;
	}

	public String getExaminationQuestion_content() {
		return examinationQuestion_content;
	}

	public void setExaminationQuestion_content(String examinationQuestion_content) {
		this.examinationQuestion_content = examinationQuestion_content;
	}

	public int getExaminationQuestion_num() {
		return examinationQuestion_num;
	}

	public void setExaminationQuestion_num(int examinationQuestion_num) {
		this.examinationQuestion_num = examinationQuestion_num;
	}

	public String getExaminationQuestion_answer() {
		return examination_answer;
	}

	public void setExaminationQuestion_answer(String examinationQuestion_answer) {
		this.examination_answer = examinationQuestion_answer;
	}

	public int getExaminationType_id() {
		return examinationType_id;
	}

	public void setExaminationType_id(int examinationType_id) {
		this.examinationType_id = examinationType_id;
	}

	public int getExamination_id() {
		return examination_id;
	}

	public void setExamination_id(int examination_id) {
		this.examination_id = examination_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getExaminationQuestion_id() {
		return examinationQuestion_id;
	}

	public void setExaminationQuestion_id(String examinationQuestion_id) {
		this.examinationQuestion_id = examinationQuestion_id;
	}

	@Override
	public String toString() {
		return "ExaminationQuestion [examinationQuestion_id=" + examinationQuestion_id + ", examinationQuestion_title="
				+ examinationQuestion_title + ", examinationQuestion_score=" + examinationQuestion_score
				+ ", examinationQuestion_content=" + examinationQuestion_content + ", examinationQuestion_num="
				+ examinationQuestion_num + ", examination_answer=" + examination_answer + ", examinationType_id="
				+ examinationType_id + ", examination_id=" + examination_id + ", options=" + options + "]";
	}
	
}
