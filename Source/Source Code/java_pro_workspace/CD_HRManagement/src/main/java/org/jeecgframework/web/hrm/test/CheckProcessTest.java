package org.jeecgframework.web.hrm.test;


import org.jeecgframework.web.hrm.service.ApplyCheckService;
import org.jeecgframework.web.hrm.service.CheckProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class CheckProcessTest {
    @Autowired
    private CheckProcessService checkProcessService;

    @Autowired
    private ApplyCheckService applyCheckService;

    @Test
    public void add(){
        String sql = "insert into t_hrm_checkprocess(id,department,functionary_id,conductor_id) values(?,?,?,?)";
        checkProcessService.executeSql(sql,UUID.randomUUID().toString(),"测试部","8a8080ca64d017fc0164d01d1f30000e","8a8080ca64d017fc0164d01e60020014");
    }
    @Test
    public void test(){
        String sql ="SELECT a.`user_id` AS check_id,b.id as apply_id,c.functionary_id,c.conductor_id,c.personnel_id " +
                "FROM t_hrm_apply_check a JOIN t_hrm_vacation_apply b ON a.`apply_id` = b.`id` JOIN t_hrm_checkprocess c ON b.`department`=c.`department`" +
                " WHERE a.id ='3af15d3d-e1c4-4611-bed0-678b5e12fb31' ";
        List list = checkProcessService.findListbySql(sql);
//        CheckBean checkBean = (CheckBean) list.get(0);
        Object[] object = (Object[]) list.get(0);
        String s = String.valueOf(object[0]);

        for (Object o :object){
            System.out.println(o.toString());
        }
    }

    @Test
    public void test1(){
        applyCheckService.check("3af15d3d-e1c4-4611-bed0-678b5e12fb31","1","1");
    }
}
