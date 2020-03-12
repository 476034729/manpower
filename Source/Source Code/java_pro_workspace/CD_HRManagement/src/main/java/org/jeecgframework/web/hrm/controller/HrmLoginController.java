package org.jeecgframework.web.hrm.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.HrmRoleService;
import org.jeecgframework.web.hrm.service.HrmUserService;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/hrmLoginController")
public class HrmLoginController {

    private Logger log = Logger.getLogger(HrmLoginController.class);

    @Autowired
    private MutiLangServiceI mutiLangService;

    @Autowired
    private HrmUserService hrmUserService;

    @Autowired
    private HrmRoleService hrmRoleService;

    @RequestMapping(params = "checkUser")
    @ResponseBody
    public AjaxJson checkUser(HrmUser hrmUser, HttpServletRequest request){
        HttpSession session = request.getSession();
        AjaxJson j = new AjaxJson();
        //语言选择
        if (request.getParameter("langCode")!=null) {
            request.getSession().setAttribute("lang", request.getParameter("langCode"));
        }
        //验证码
        String randCode = request.getParameter("randCode");
        if (StringUtils.isEmpty(randCode)) {
            j.setMsg(mutiLangService.getLang("common.enter.verifycode"));
            j.setSuccess(false);
        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
            j.setMsg(mutiLangService.getLang("common.verifycode.error"));
            j.setSuccess(false);
        } else {
            //用户登录验证逻辑
            HrmUser u=hrmUserService.checkUserExits(hrmUser);
            if (u == null) {
                j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
                j.setSuccess(false);
                return j;
            }
            saveLoginSuccessInfo(request,u);
        }
        return j;
    }

    /**
     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param  request
     * @param hrmUser 当前登录用户
     *
     */
    private void saveLoginSuccessInfo(HttpServletRequest request, HrmUser hrmUser) {
        String message = null;

        HttpSession session = ContextHolderUtils.getSession();

        session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, hrmUser);

        message = mutiLangService.getLang("common.user") + ": " + hrmUser.getJob_num() + mutiLangService.getLang("common.login.success");

        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
        Client clientOld = ClientManager.getInstance().getClient(session.getId());
        if (clientOld == null || clientOld.getHrmUser() == null || hrmUser.getJob_num().equals(clientOld.getHrmUser().getJob_num())) {
            Client client = new Client();
            client.setIp(IpUtil.getIpAddr(request));
            client.setLogindatetime(new Date());
            client.setHrmUser(hrmUser);
            ClientManager.getInstance().addClinet(session.getId(), client);
        } else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
            ClientManager.getInstance().removeClinet(session.getId());
            session.invalidate();
            session = request.getSession(true);//session初始化
            session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, hrmUser);
            session.setAttribute("randCode", request.getParameter("randCode"));//保存验证码
            checkUser(hrmUser, request);
        }
    }

    @RequestMapping(params = "login")
    public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        HrmUser hrmUser = ResourceUtil.getSessionHrmUserName();
        String roles = "";
        if (hrmUser != null) {

            List<HrmUserRole> hrmUserRoles=hrmUserService.getRoleByUser(hrmUser);

            for (HrmUserRole userRole : hrmUserRoles) {
                HrmRole role=userRole.getHrmRole();
                roles += role.getRole_name() + ",";
            }
            if (roles.length() > 0) {
                roles = roles.substring(0, roles.length() - 1);
            }
            modelMap.put("roleName", roles);
            modelMap.put("userName", hrmUser.getUsername());
            modelMap.put("job_num",hrmUser.getJob_num());

            request.getSession().setAttribute("CKFinder_UserRole", "admin");

            SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
            if("ace".equals(sysTheme.getStyle())||"diy".equals(sysTheme.getStyle())||"acele".equals(sysTheme.getStyle())||"hplus".equals(sysTheme.getStyle())){
                request.setAttribute("menuMap", getFunctionMap(hrmUser));
            }

            Cookie cookie = new Cookie("JEECGINDEXSTYLE", sysTheme.getStyle());
            //设置cookie有效期为一个月
            cookie.setMaxAge(3600*24*30);
            response.addCookie(cookie);

            Cookie zIndexCookie = new Cookie("ZINDEXNUMBER", "1990");
            zIndexCookie.setMaxAge(3600*24);//一天
            response.addCookie(zIndexCookie);

            sysTheme=SysThemesEnum.HRMHPLUS;
            return sysTheme.getIndexPath();
        } else {
            return "login/hrmlogin";
        }

    }
    /**
     * 获取权限的map
     *
     * @param hrmUser
     * @return
     */
    private Map<Integer, List<HrmMenu>> getFunctionMap(HrmUser hrmUser) {
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        if (client.getHrmFunctionMap() == null || client.getHrmFunctionMap().size() == 0) {
            Map<Integer ,List<HrmMenu>> menuMap=new HashMap<Integer, List<HrmMenu>>();
            Map<String, HrmMenu> loginActionList = getUserFunction(hrmUser);
            if (loginActionList.size() > 0) {
                Collection<HrmMenu> allHrmMenus = loginActionList.values();
                for (HrmMenu hrmMenu : allHrmMenus) {

                    if ("button".equals(hrmMenu.getType())){
                        continue;
                    }
                    menuMap.put(0,new ArrayList<HrmMenu>());
                    menuMap.get(0).add(hrmMenu);
//                    if (!menuMap.containsKey(hrmMenu.getFunctionLevel() + 0)) {
//                        menuMap.put(function.getFunctionLevel() + 0,
//                                new ArrayList<TSFunction>());
//                    }
//                    menuMap.get(function.getFunctionLevel() + 0).add(function);
                }
                // 菜单栏排序
                Collection<List<HrmMenu>> c = menuMap.values();
                for (List<HrmMenu> list : c) {
                    Collections.sort(list, new NumberComparator());
                }
            }
              client.setHrmFunctionMap(menuMap);


            //清空变量，降低内存使用
            loginActionList.clear();

            return menuMap;
        }else{
            return client.getHrmFunctionMap();
        }
    }

    /**
     * 获取用户菜单列表
     *
     * @param hrmUser
     * @return
     */
    private Map<String, HrmMenu> getUserFunction(HrmUser hrmUser) {
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());

        if (client.getHrmFunctions() == null || client.getHrmFunctions().size() == 0) {

            Map<String, HrmMenu> loginActionList = new HashMap<String, HrmMenu>();

            List<HrmMenu> list=new ArrayList<HrmMenu>();

            List<HrmUserRole> roleList=hrmUserService.getRoleByUser(hrmUser);

            for (HrmUserRole hrmUserRole :roleList){
                List<HrmRoleMenu> menuList=hrmRoleService.findMenuByRole(hrmUserRole.getHrmRole());
                for (HrmRoleMenu hrmRoleMenu :menuList){
                    list.add(hrmRoleMenu.getHrmMenu());
                }
            }

            for(HrmMenu hrmMenu:list){
                loginActionList.put(hrmMenu.getMenu_id(),hrmMenu);
            }
            client.setHrmFunctions(loginActionList);

            //清空变量，降低内存使用
            list.clear();

        }
        return client.getHrmFunctions();
    }
}
