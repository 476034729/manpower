package org.jeecgframework.web.hrm.utils;

import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Wang Yu
 * 获取当前登录用户
 */
public class UserUtils {
    private UserUtils(){}

    public static TSUser getCurrentUser(HttpServletRequest request){
        Client client = ClientManager.getInstance().getClient(request.getSession().getId());
        TSUser user = (client != null) ? client.getUser() : null;
        if (user == null){
            user = new TSUser();
            user.setRealName("test User");
            user.setId("testID");
        }
        return user;
    }

    public static TSUser getCurrentUser(HttpSession session){
        Client client = ClientManager.getInstance().getClient(session.getId());
        return (client != null) ? client.getUser() : null;
    }
}
