package org.jeecgframework.web.hrm.test;


import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.jeecgframework.web.hrm.service.HrmMenuService;
import org.jeecgframework.web.system.service.SystemService;
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
public class HrmMenuTest {
    @Autowired
    private HrmMenuService hrmMenuService;

    private SystemService systemService;

    @Test
    public void addMenu(){
        String sql="insert into t_hrm_menu(menu_id,parent_id,menu_name,level,type) values(?,?,?,?,?)";

        String name="员工管理";
        String parent_id="2041f21e-9999-4eba-b6c0-6dbc0f297b35";
        String[] names={"人力统计清单","人力分析图"};
        String level="子菜单";
        String type="menu";
        for (String n:names){
            String id=UUID.randomUUID().toString();
            hrmMenuService.executeSql(sql,id,parent_id,n,level,type);
        }
    }
    @Test
    public void getMenu(){

        String id="7a17fb76-feb6-4292-8795-3dea2d169fb6";
        String sql="select * from t_hrm_menu where menu_id='"+ id +"'";
        String hql="from HrmMenu u where u.menu_id = ? ";

        List<HrmMenu> list=hrmMenuService.findHql(hql,id);

        System.out.println(list.get(0).getMenu_name());
    }

    @Test
    public void Menus(){
        System.out.println(hrmMenuService.findHql("from HrmMenu u where 1=1").size());
    }
}
