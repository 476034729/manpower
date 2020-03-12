package org.jeecgframework.web.hrm.test;

import org.jeecgframework.web.hrm.bean.HrmRole;
import org.jeecgframework.web.hrm.bean.HrmRoleMenu;
import org.jeecgframework.web.hrm.service.HrmRoleService;
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
public class HrmRoleTest {

    @Autowired
    private HrmRoleService hrmRoleService;

    @Test
    public void addRole(){
        String sql="insert into t_hrm_role(role_id,role_name,remark) values(?,?,?)";
        String id=UUID.randomUUID().toString();
        String name="工程师";
        String remark="普通员工";
        int i=hrmRoleService.executeSql(sql,id,name,remark);
        System.out.println(i);
    }
    @Test
    public void deleteRole(){
        String sql="delete from t_hrm_role where role_id=?";
        String id="04d69f8e-96bd-4140-bfb7-9d3dcc2f280f";
        int i=hrmRoleService.executeSql(sql,id);
        System.out.println(i);
    }

    @Test
    public void addMenuToRole(){

        String sql="insert into t_hrm_role_menu(id,role_id,menu_id) values(?,?,?)";

        String[] menus={
                "50b09954-f3c3-4e07-a891-857a51d787d7",
                "aa16b698-f640-446b-abbe-7c0b7d0d1f1e",
                "d64394f6-e546-4a73-8cf0-5863c70f5b2d",
                "ed67c959-52ab-451b-81a5-46eef627e57a"
        };
        String role_id="001997be-873c-4086-840a-7365c4c6cf00";
        for (String menu:menus){
            hrmRoleService.executeSql(sql,UUID.randomUUID().toString(),role_id,menu);
        }

    }

    @Test
    public void getMenuByRole(){
        HrmRole hrmRole=new HrmRole();
        hrmRole.setRole_id("001997be-873c-4086-840a-7365c4c6cf00");
        List<HrmRoleMenu> list= hrmRoleService.findMenuByRole(hrmRole);
        System.out.println(list.get(0).getHrmMenu().getMenu_name());
    }
}
