package org.jeecgframework.web.hrm.service.impl;


import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDepart;
import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.service.ProjectInputTotalService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("ProjectInputTotalService")
@Transactional
public class ProjectInputTotalServiceImpl extends CommonServiceImpl implements ProjectInputTotalService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(ProjectInputTotalServiceImpl.class);

    @Override
    public List<TSUser> getUserList() {
        String hql = "from TSUser u where u.userstatus = '在职' and u.jobNum != ' ' and u.level != 'M100000' order by u.level desc";
        return this.findHql(hql);
    }

    @Override
    public List<Map<String, Object>> getWorkHourByProject(String code, int year, int month) {
        String userHql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.project_code = ?";
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        List<WorkHour> list=this.findHql(userHql,year,month,code);
        Set<String> stringSet = new HashSet<String>();
        for (WorkHour workHour : list){
            stringSet.add(workHour.getUser_id());
        }

        for (String s :stringSet){
            mapList.add(this.getUserWorkHour(code,s,year,month));
        }

        return mapList;
    }

    @Override
    public Map<String, Object> getUserWorkHour(String code, String id,int year ,int month) {
        String hql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.project_code =? and u.user_id =?";
        Map<String,Object> map = new HashMap<String, Object>();
        List<WorkHour> list = this.findHql(hql,year,month,code,id);
        map.put("id",id);
        map.put("time",(double)list.size()/2.0);
        return map;
    }

    @Override
    public Map<String, Object> getWorkHourCount(String code, int year, int month) {

        logger.info(">>ProjectInputTotalServiceImpl:getWorkHourCount:");

        double E6 = 0;
        double E5 = 0;
        double E4 = 0;
        double E3 = 0;
        double E2 = 0;
        double E1 = 0;
        double NE = 0;


        Map<String,Object> map = new HashMap<String, Object>(16);

        String userHql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";

        String hql = "from TSUser u where u.id = ? and u.jobNum != ' ' and u.site = 'CD'";

        List<WorkHour> list=this.findHql(userHql,year,month);
        Set<String> stringSet = new HashSet<String>();
        Set<TSUser> tsUserSet=new HashSet<TSUser>();
        for (WorkHour workHour : list){
            stringSet.add(workHour.getUser_id());
        }
        for (String s :stringSet){
            List<TSUser> userList=this.findHql(hql,s);
            if (userList.size()>0){
                tsUserSet.add(userList.get(0));
            }
        }

        for (TSUser user:tsUserSet){
            if ("E6".equals(user.getLevel())){
                E6+=getUserWorkHourConut(code,year,month,user);
            }
            if ("E5".equals(user.getLevel())){
                E5+=getUserWorkHourConut(code,year,month,user);
            }
            if ("E4".equals(user.getLevel())){
                E4+=getUserWorkHourConut(code,year,month,user);
            }
            if ("E3".equals(user.getLevel())){
                E3+=getUserWorkHourConut(code,year,month,user);
            }
            if ("E2".equals(user.getLevel())){
                E2+=getUserWorkHourConut(code,year,month,user);
            }
            if ("E1".equals(user.getLevel())){
                E1+=getUserWorkHourConut(code,year,month,user);
            }
            if ("实习".equals(user.getLevel())){
                NE+=getUserWorkHourConut(code,year,month,user);
            }
        }


        map.put("E6",E6/2.0);
        map.put("E5",E5/2.0);
        map.put("E4",E4/2.0);
        map.put("E3",E3/2.0);
        map.put("E2",E2/2.0);
        map.put("E1",E1/2.0);
        map.put("NE",NE/2.0);

        String dateSql = "select * from t_hrm_date where status = '0' and YEAR(work_date)=" + year + " and MONTH(work_date) = " + month;
        double workday = this.findListbySql(dateSql).size();

        String departhql = "from HrmDepart d where 1=1";
        List<HrmDepart> departs=this.findHql(departhql);

        String[] strings = {"开发部","测试部","系统部"};
        for (HrmDepart depart :departs) {
            String sql = "select count(1) from t_hrm_workhour a " +
                    "join t_hrm_project_code b  on a.project_code = b.code " +
                    "join t_s_user c on c.id = a.user_id  where YEAR(a.work_day)=" + year + " and MONTH(a.work_day) = " + month + " and c.department = '" + depart.getName() + "' and b.code =  '"+ code +"'";
            List<Object> o = this.findListbySql(sql);
            String rate = String.format("%.2f",Integer.parseInt(String.valueOf(o.get(0)))/(2.0*workday));
            map.put(depart.getName(),rate);
        }


        return map;
    }

    public int getUserWorkHourConut(String code, int year, int month,TSUser user) {
        String hql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.project_code=? and u.user_id =?";
        return this.findHql(hql,year,month,code,user.getId()).size();
    }

}
