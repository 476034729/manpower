package org.jeecgframework.web.hrm.dao;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.web.hrm.bean.HrmAttendanceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("attendanceDao")
public interface AttendanceDao {

    /**
     * 考勤记录列表
     * @param chooseTime
     * @return
     */
    @Arguments({"chooseTime","jobNum"})
    @ResultType(HrmAttendanceRecord.class)
    public List<HrmAttendanceRecord> getAllAttendanceRecordsByDate(String chooseTime, String jobNum);
}
