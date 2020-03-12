package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.jeecgframework.web.hrm.bean.HrmRoleMenu;
import org.jeecgframework.web.hrm.bean.HrmUser;
import org.jeecgframework.web.hrm.bean.HrmUserRole;
import org.jeecgframework.web.hrm.service.HrmMenuService;
import org.jeecgframework.web.hrm.service.HrmRoleService;
import org.jeecgframework.web.hrm.service.HrmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("HrmUserService")
@Transactional
public class HrmUserServiceImpl extends CommonServiceImpl implements HrmUserService {

    @Autowired
    private HrmMenuService hrmMenuService;

    @Autowired
    private HrmRoleService hrmRoleService;

    @Override
    public HrmUser checkUserExits(HrmUser hrmUser) {
        return commonDao.checkUser(hrmUser);
    }

    @Override
    public List<HrmUserRole> getRoleByUser(HrmUser hrmUser) {
        String hql="from HrmUserRole u where u.hrmUser.user_id=?";
        List<HrmUserRole> list=commonDao.findHql(hql,hrmUser.getUser_id());
        return list;
    }

    @Override
    public Map<String,List<HrmMenu>> getMenuByUser(HrmUser hrmUser) {
//        HrmRoleService hrmRoleService=new HrmRoleServiceImpl();
//        HrmMenuService hrmMenuService=new HrmMenuServiceImpl();
        Map<String,List<HrmMenu>> map=new HashMap<String, List<HrmMenu>>();
        List<HrmUserRole> list=this.getRoleByUser(hrmUser);
        for (HrmUserRole hrmUserRole:list){
            List<HrmRoleMenu> list1=hrmRoleService.findMenuByRole(hrmUserRole.getHrmRole());
            for (HrmRoleMenu hrmRoleMenu:list1){
                if (hrmRoleMenu.getHrmMenu().getLevel().equals("父级菜单")){
                    map.put(hrmRoleMenu.getHrmMenu().getMenu_name(),hrmMenuService.findMenuByParent(hrmRoleMenu.getHrmMenu().getMenu_id()));
                }
            }
        }
        return map;
    }

}
