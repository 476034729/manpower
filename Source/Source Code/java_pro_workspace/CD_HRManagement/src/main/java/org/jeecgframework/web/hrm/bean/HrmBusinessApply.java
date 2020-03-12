package org.jeecgframework.web.hrm.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LIUYI
 *
 *
 */
@Entity
@Table(name = "t_hrm_business_apply")
public class HrmBusinessApply {

	 //休假地点(1:国内,2:国外)
    public static final Integer DOMESTIC = 1;
    public static final Integer ABROAD = 2;
    //用户状态为 确认
    public static final Integer AGREE = 1;
    
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "dept")
    private String dept;
    @Column(name = "vacation_type")
    private String vacationType;
    @Column(name = "leave_start")
    private Date leaveStart;
    @Column(name = "leave_end")
    private Date leaveEnd;
    @Column(name = "count_hour")
    private Double countHour;
    @Column(name = "count_day")
    private Double countDay;
    @Column(name = "leave_reason")
    private String leaveReason;
    @Column(name = "vacation_place")
    private Integer vacationPlace;
    @Column(name = "contact_information")
    private String contactInformation;
    @Column(name = "instruction")
    private String instruction;
    @Column(name = "applicant_sign")
    private String applicantSign;
    @Column(name = "apply_date")
    private Date applyDate;
    @Column(name = "file_address")
    private String fileAddress;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "personnel_matters_status")
    private Integer personnelMattersStatus;
    @Column(name = "personnel_matters_name")
    private String personnelMattersName;
    @Column(name = "user_status")
    private Integer userStatus;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "ownconfire_time")
    private Date ownconfireTime;
    @Column(name = "hrconfire_time")
    private Date hrconfireTime;
    
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
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getVacationType() {
		return vacationType;
	}
	public void setVacationType(String vacationType) {
		this.vacationType = vacationType;
	}
	public Date getLeaveStart() {
		return leaveStart;
	}
	public void setLeaveStart(Date leaveStart) {
		this.leaveStart = leaveStart;
	}
	public Date getLeaveEnd() {
		return leaveEnd;
	}
	public void setLeaveEnd(Date leaveEnd) {
		this.leaveEnd = leaveEnd;
	}
	public Double getCountHour() {
		return countHour;
	}
	public void setCountHour(Double countHour) {
		this.countHour = countHour;
	}
	public Double getCountDay() {
		return countDay;
	}
	public void setCountDay(Double countDay) {
		this.countDay = countDay;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public Integer getVacationPlace() {
		return vacationPlace;
	}
	public void setVacationPlace(Integer vacationPlace) {
		this.vacationPlace = vacationPlace;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getApplicantSign() {
		return applicantSign;
	}
	public void setApplicantSign(String applicantSign) {
		this.applicantSign = applicantSign;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getPersonnelMattersStatus() {
		return personnelMattersStatus;
	}
	public void setPersonnelMattersStatus(Integer personnelMattersStatus) {
		this.personnelMattersStatus = personnelMattersStatus;
	}
	public String getPersonnelMattersName() {
		return personnelMattersName;
	}
	public void setPersonnelMattersName(String personnelMattersName) {
		this.personnelMattersName = personnelMattersName;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public static Integer getDomestic() {
		return DOMESTIC;
	}
	public static Integer getAbroad() {
		return ABROAD;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getOwnconfireTime() {
		return ownconfireTime;
	}
	public void setOwnconfireTime(Date ownconfireTime) {
		this.ownconfireTime = ownconfireTime;
	}
	public Date getHrconfireTime() {
		return hrconfireTime;
	}
	public void setHrconfireTime(Date hrconfireTime) {
		this.hrconfireTime = hrconfireTime;
	}
	@Override
	public String toString() {
		return "HrmBusinessApply [id=" + id + ", userId=" + userId + ", userName=" + userName + ", dept=" + dept
				+ ", vacationType=" + vacationType + ", leaveStart=" + leaveStart + ", leaveEnd=" + leaveEnd
				+ ", countHour=" + countHour + ", countDay=" + countDay + ", leaveReason=" + leaveReason
				+ ", vacationPlace=" + vacationPlace + ", contactInformation=" + contactInformation + ", instruction="
				+ instruction + ", applicantSign=" + applicantSign + ", applyDate=" + applyDate + ", fileAddress="
				+ fileAddress + ", fileName=" + fileName + ", personnelMattersStatus=" + personnelMattersStatus
				+ ", personnelMattersName=" + personnelMattersName + ", userStatus=" + userStatus + ", createTime="
				+ createTime + ", ownconfireTime=" + ownconfireTime + ", hrconfireTime=" + hrconfireTime + "]";
	}
    
    
	
}
