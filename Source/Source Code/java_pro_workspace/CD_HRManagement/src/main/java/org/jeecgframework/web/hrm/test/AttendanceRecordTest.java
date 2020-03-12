package org.jeecgframework.web.hrm.test;


import org.jeecgframework.web.hrm.bean.HrmAttendanceRecord;
import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class AttendanceRecordTest {

    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    @Test
    public void insertAttendanceRecordAuto() {
		//查询在职人员的集合
		String hql="from TSUser u where u.userstatus=? and position!=?";
        List<TSUser> list = attendanceRecordService.findHql(hql,"在职","集团总裁");
        //系统当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        //双层循环写入数据
        for(int i=0;i<list.size();i++){
            TSUser tSUser = list.get(i);
            String hql1 = "from HrmAttendanceRecord u where u.user_name=? and u.job_num=? and YEAR(u.creat_time)=? and MONTH(u.creat_time) =?";
            List<HrmAttendanceRecord> list1 = attendanceRecordService.findHql(hql1,tSUser.getRealName(),tSUser.getJobNum(),tSUser.getJobNum(),year,month);
            //当数据库中没有数据的时候 插入数据
            if(list1.size() == 0){
                String sql = "insert into HrmAttendanceRecord(id,user_name,job_num,type,creat_time,oper_time)" +
                        "values(?,?,?,?,?,?)";
                List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);
                for (DateUtil dateUtil:dateUtilList){
                	attendanceRecordService.executeSql(sql,UUID.randomUUID().toString(),tSUser.getRealName(),tSUser.getJobNum(),0,dateUtil.getDate(),new Date());
                }
            }
        }
	}
}
