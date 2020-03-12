package org.jeecgframework.web.hrm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author hexiaoling
 * @Description:岗位类型 @Date：2018-11-30 10：52
 */
@Entity
@Table(name = "t_hrm_exam_post")
public class ExamPost implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "post_id")
	private int post_id;
	@Column(name = "post_name")
	private String post_name;

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getPost_name() {
		return post_name;
	}

	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}

}
