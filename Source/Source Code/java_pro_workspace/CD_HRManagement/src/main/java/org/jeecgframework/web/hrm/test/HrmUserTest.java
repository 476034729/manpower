package org.jeecgframework.web.hrm.test;

import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.jeecgframework.web.hrm.bean.HrmRoleMenu;
import org.jeecgframework.web.hrm.bean.HrmUser;
import org.jeecgframework.web.hrm.bean.HrmUserRole;
import org.jeecgframework.web.hrm.service.HrmRoleService;
import org.jeecgframework.web.hrm.service.HrmUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class HrmUserTest {
    @Autowired
    private HrmUserService hrmUserService;

    @Autowired
    private HrmRoleService hrmRoleService;
    @Test
    public void checkUser(){
        HrmUser oldUser=new HrmUser();
        oldUser.setJob_num("98765");
        oldUser.setPassword("123456");
        String password = PasswordUtil.encrypt(oldUser.getJob_num(), oldUser.getPassword(), PasswordUtil.getStaticSalt());
        System.out.println(password);
        oldUser.setPassword(password);
        HrmUser hrmUser = hrmUserService.checkUserExits(oldUser);
        System.out.println(hrmUser.getUser_id());
    }

    @Test
    public void addUser(){
        HrmUser hrmUser=new HrmUser();
        hrmUser.setUser_id(UUID.randomUUID().toString());
        hrmUser.setJob_num("11122");
        hrmUser.setUsername("stee");
        hrmUser.setPassword(PasswordUtil.encrypt("9102730","123456",PasswordUtil.getStaticSalt()));
        hrmUser.setRemark("test3");
//        String sql="insert into t_hrm_user(user_id,job_num,username,password,remark )values(?,?,?,?,?)";
//        hrmUserService.executeSql(sql,hrmUser.getUser_id(),hrmUser.getJob_num(),hrmUser.getUsername()
//        ,hrmUser.getPassword(),hrmUser.getRemark());
        System.out.println(hrmUser.getPassword());
    }

    @Test
    public void addRoleToUser(){
        String sql="insert into t_hrm_user_role(id,user_id,role_id) values(?,?,?)";
        String[] roles={
                "001997be-873c-4086-840a-7365c4c6cf00",
                "294f530e-521c-485c-ae3b-219ec219efe3"
//                "64ddd297-532d-4028-b59d-ebecb88fc802"
        };
        String user_id="1";
        for (String role:roles){
            hrmUserService.executeSql(sql,UUID.randomUUID().toString(),user_id,role);
        }
    }

    @Test
    public void getRoleByUser(){
        HrmUser hrmUser=new HrmUser();
        hrmUser.setUser_id("1");
        List<HrmUserRole> list=hrmUserService.getRoleByUser(hrmUser);
        System.out.println(list.get(0).getHrmRole().getRole_name());
    }

    @Test
    public void getMenuByUser(){
        HrmUser hrmUser=new HrmUser();
        hrmUser.setUser_id("1");
        Map<String,HrmMenu> map=new HashMap<String, HrmMenu>();
        List<HrmUserRole> list=hrmUserService.getRoleByUser(hrmUser);
        for (HrmUserRole hrmUserRole:list){
            List<HrmRoleMenu> list1=hrmRoleService.findMenuByRole(hrmUserRole.getHrmRole());
            for (HrmRoleMenu hrmRoleMenu:list1){
                map.put(hrmRoleMenu.getHrmMenu().getMenu_name(),hrmRoleMenu.getHrmMenu());
            }
        }
    }
}
