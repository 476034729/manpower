package org.jeecgframework.web.hrm.test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.hrm.service.ProjectCodeService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class ProjectCodeTest {
    @Autowired
    private ProjectCodeService projectCodeService;

    @Test
    public void addProject(){
        String sql="insert into t_hrm_project_code(id,project_name,project_eg_code,code,type) values (?,?,?,?,?)";
        List<ProjectCode> list=new ArrayList<ProjectCode>();
//        ProjectCode p1=new ProjectCode("新加坡路灯","STTLT","C");
//        ProjectCode p2=new ProjectCode("沙迦项目","SHARJ","F");
//        ProjectCode p3=new ProjectCode("其他","OTHER","X");
//        ProjectCode p4=new ProjectCode("新都中车HK93项目","HK_93","G");
//        ProjectCode p5=new ProjectCode("重庆来福士项目","CQLFS","H");
//        ProjectCode p6=new ProjectCode("中新局课题","ZXJKT","J");
//        ProjectCode p7=new ProjectCode("成都新能源","CTXNY","K");
//        ProjectCode p8=new ProjectCode("电动车充电桩服务_软件","CDZFW_N1","N1");
//        ProjectCode p9=new ProjectCode("电动车充电桩服务_工程","CDZFW_N2","N2");
//        ProjectCode p10=new ProjectCode("电动车充电桩服务_设备供应","CDZFW_N3","N3");
//        ProjectCode p11=new ProjectCode("行政工作","XZHGZ","M");
//        ProjectCode p12=new ProjectCode("越南项目","VNITS","P");
//        ProjectCode p13=new ProjectCode("云南多语言","YNMLG","BL");
//        ProjectCode p14=new ProjectCode("请假","HLDAY","O");
//        ProjectCode p15=new ProjectCode("离职","LEAVE","L");
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);
//        list.add(p4);
//        list.add(p5);
//        list.add(p6);
//        list.add(p7);
//        list.add(p8);
//        list.add(p9);
//        list.add(p10);
//        list.add(p11);
//        list.add(p12);
//        list.add(p13);
//        list.add(p14);
//        list.add(p15);
//        ProjectCode p1=new ProjectCode("CN3","CN3PR","D1");
//        ProjectCode p2=new ProjectCode("TMS","TMSPR","D2");
//        ProjectCode p3=new ProjectCode("FMS","FMSPR","D3");
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);
        ProjectCode p1=new ProjectCode("公司内部系统","CDHRS","X1");
        ProjectCode p2=new ProjectCode("测试用例","TEST","TEST");
        list.add(p1);
        list.add(p2);
        for (ProjectCode projectCode:list){
            projectCodeService.executeSql(sql,UUID.randomUUID().toString(),projectCode.getProject_name()
                    ,projectCode.getProject_eg_code(),projectCode.getCode(),"执行中项目");
        }
//        int i=projectCodeService.executeSql(sql,UUID.randomUUID().toString(),"云南新路","YNNRD","I","执行中项目");
//        System.out.println(i);
    }

    @Test
    public void add(){
        String sql="insert into t_hrm_project_code(id,project_name,project_eg_code,code,type,status,rank) values (?,?,?,?,?,?,?)";
        int i =projectCodeService.executeSql(sql,UUID.randomUUID().toString(),"换休","DuBai_ITS","Q","执行中项目","执行中","15");
        System.out.println(i);
    }

    @Test
    public void delete(){
        String sql = "delete from t_hrm_project_code where id = '339c3540-4c97-4e77-abe5-33392295d8a9'";
        int i = projectCodeService.executeSql(sql);
        System.out.println(i);
    }

    @Test
    public void  test(){
        String hql = "from TSUser u where 1=1";
        Session session = this.projectCodeService.getSession();
        Query query = session.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(10);
        List<TSUser> list = query.list();
        System.out.println(list.size());
    }

}
