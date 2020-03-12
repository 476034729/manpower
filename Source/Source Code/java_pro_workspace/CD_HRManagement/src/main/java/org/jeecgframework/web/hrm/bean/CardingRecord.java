package org.jeecgframework.web.hrm.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 打卡记录表
 * @author Administrator
 *
 */
@Entity
@Table(name="t_hrm_cardingrecord")
public class CardingRecord {
	
	@Id
    @Column(name = "id")
    private String id;
	
	@Column(name="T_ACCOUNT")
	private String tAccount;
	
	@Column(name="T_TIME")
	private Timestamp tTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettAccount() {
		return tAccount;
	}

	public void settAccount(String tAccount) {
		this.tAccount = tAccount;
	}

	public Timestamp gettTime() {
		return tTime;
	}

	public void settTime(Timestamp tTime) {
		this.tTime = tTime;
	}

	@Override
	public String toString() {
		return "CardingRecord [id=" + id + ", tAccount=" + tAccount + ", tTime=" + tTime + "]";
	}
	
	
}
