package org.jeecgframework.web.hrm.bean;

import java.util.List;

/**
 * 返回给前端考勤记录的实体封装
 */
public class AttendanceRecordResult {
    //工号
    private String jobNum;
    //员工姓名
    private String userName;
    //考勤记录
    private List<HrmAttendanceRecord> attendanceRecords;

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<HrmAttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<HrmAttendanceRecord> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }
}
