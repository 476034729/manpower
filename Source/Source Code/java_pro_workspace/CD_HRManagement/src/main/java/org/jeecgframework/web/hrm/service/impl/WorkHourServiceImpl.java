package org.jeecgframework.web.hrm.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDate;
import org.jeecgframework.web.hrm.bean.WorkHour;
import org.jeecgframework.web.hrm.bean.WorkHourCM;
import org.jeecgframework.web.hrm.service.WorkHourService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("WorkHourService")
@Transactional
public class WorkHourServiceImpl extends CommonServiceImpl implements WorkHourService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(WorkHourServiceImpl.class);

    @Override
    public List<WorkHour>   getWorkHourList(String user_id, int year, int month) {

        logger.info(">>WorkHourServiceImpl:getWorkHourList:");

        String hql="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHour> list=this.findHql(hql,user_id,year,month);

        if (list.size()>0){
            return list;
        }
        else {

            logger.info(">>WorkHourServiceImpl:getWorkHourList:addworkhour");

            String datehql="from HrmDate u where  YEAR(u.work_date)=? and MONTH(u.work_date) =?";
            List<HrmDate> datelist=this.findHql(datehql,year,month);

            String sql="insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm,number)" +
                    "values";


            List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);
            appendsql(sql,dateUtilList,user_id,datelist);
            return this.getWorkHourList(user_id,year,month);
        }
    }

    @Override
    public List<WorkHourCM> getCMWorkHourList(String user_id, int year, int month) {

        logger.info(">>WorkHourServiceImpl:getCMWorkHourList:");

        String hql="from WorkHourCM u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHourCM> list=this.findHql(hql,user_id,year,month);
        if (list.size()>0){
            return list;
        }
        else {

            logger.info(">>WorkHourServiceImpl:getCMWorkHourList:addworkhour_cm");

            String sql="insert into t_hrm_workhour_cm(id,user_id,work_day,update_day,amorpm,number)" +
                    "values";

            List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);

            String datehql="from HrmDate u where  YEAR(u.work_date)=? and MONTH(u.work_date) =?";
            List<HrmDate> datelist=this.findHql(datehql,year,month);

            appendsql(sql,dateUtilList,user_id,datelist);
            return this.getCMWorkHourList(user_id,year,month);
        }
    }


    @Override
    public void insertWorkHourListAuto() {

        logger.info(">>WorkHourServiceImpl:insertWorkHourListAuto:");

        // 查询在职人员的集合
        String hql = "from TSUser u where jobNum != ' ' and u.userstatus=? and position!=?";
        List<TSUser> list = this.findHql(hql, "在职", "集团总裁");
        // 系统当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        
        // 写入数据
        for (int i = 0; i < list.size(); i++) {

            logger.info(">>WorkHourServiceImpl:insertWorkHourListAuto:addworkhour");

            TSUser tSUser = list.get(i);
            String hql1="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
            String hql2="from WorkHourCM u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";

            List<WorkHour> list1=this.findHql(hql1,tSUser.getId(),year,month);

            List<WorkHourCM> list2=this.findHql(hql2,tSUser.getId(),year,month);
            // 当数据库中没有数据的时候 插入数据
            if (list1.size() == 0 &&list2.size()==0) {
                String sql="insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm,number)" +
                        "values ";
                String cmsql="insert into t_hrm_workhour_cm(id,user_id,work_day,update_day,amorpm,number)" +
                        "values ";
                List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);

                String datehql="from HrmDate u where  YEAR(u.work_date)=? and MONTH(u.work_date) =?";
                List<HrmDate> datelist=this.findHql(datehql,year,month);

                //向工时表插数据
                appendsql(sql,dateUtilList,tSUser.getId(),datelist);
                //向cm工时表插数据
                appendsql(cmsql,dateUtilList,tSUser.getId(),datelist);

            }
        }
    }

//    insert into t_hrm_workhour (id,user_id,`project_code`,`work_details`)
//    values('0dd7221b-8bf2-42ab-b0ab-b9c87bd6b200','402882b16975ea1f016975ec0b430001','B2','5656'),
//    ('193a76e5-c46d-44a9-bc4a-ce63d070a352','402882b16975ea1f016975ec0b430001','B2','fff')
//    on duplicate key update project_code=values(project_code),work_details=values(work_details);

    @Override
    public void updateWorkHour(String id, String code, String text) {
        String sql="update t_hrm_workhour set project_code = ? ,work_details = ? where id = ?";
        this.executeSql(sql,code,text,id);
    }

    //测试未通过
    @Override
    public void updateWorkHour(List<String> idList,List<String> codeList, List<String> textList,String user_id) {
        logger.info(">>WorkHourServiceImpl:updateWorkHour:");

        String updatesql="insert into t_hrm_workhour (id,user_id,`project_code`,`work_details`) values" ;

        StringBuffer appendsql =new StringBuffer();
        StringBuffer appendsqllater =new StringBuffer();

        for (int i=0;i<idList.size();i++){
            String code = " ";
            if (!"请选择项目编码".equals(codeList.get(i))){
                code = codeList.get(i);
            }
            appendsql.append(" ( '"+idList.get(i));
            appendsql.append(" ','"+user_id);
            appendsql.append(" ','"+code);
            appendsql.append(" ','"+textList.get(i));
            appendsql.append(" '),\n");

        }

        appendsqllater.append("on duplicate key update project_code=values(project_code),work_details=values(work_details)");

//        String sql="update t_hrm_workhour set project_code = ? ,work_details = ? where id = ?";
//        logger.info(updatesql+appendsql.toString().substring(0,appendsql.length()-2)+appendsqllater);

        this.executeSql(updatesql+appendsql.toString().substring(0,appendsql.length()-2)+appendsqllater);
    }


//    @Override
//    public void updateWorkHour(String id, String code, String text) {
//        String sql="update t_hrm_workhour set project_code = ? ,work_details = ? where id = ?";
//        this.executeSql(sql,code,text,id);
//    }

    @Override
    public void updateWorkHourCM(String id, String code, String text) {
        String sql="update t_hrm_workhour_cm set project_code = ? ,work_details = ? where id = ?";
        this.executeSql(sql,code,text,id);
    }

    @Override
    public Set<TSUser> getUserList(String department,int year,int month) {

        String userHql = "from WorkHour u where 1=1 and YEAR(u.work_day)=? and MONTH(u.work_day) =?";

//        String hql = "from TSUser u where u.id = ? and u.jobNum != ' ' and u.site = 'CD'";
//        if (!"all".equals(department)){
//            hql = " from TSUser u where u.id = ? and u.jobNum != ' ' and u.site = 'CD' and u.department = '"+department+"'";
//        }
        String hql = "from TSUser u where  u.jobNum != ' ' and u.site = 'CD'";
        if (!"all".equals(department)){
            hql = " from TSUser u where  u.jobNum != ' ' and u.site = 'CD' and u.department = '"+department+"'";
        }


        List<WorkHour> list=this.findHql(userHql,year,month);
        Set<String> stringSet = new HashSet<String>();
        Set<TSUser> tsUserSet=new HashSet<TSUser>();
        for (WorkHour workHour : list){
            stringSet.add(workHour.getUser_id());
        }

        List<TSUser> userList=this.findHql(hql);



        for (String s :stringSet){
            List<TSUser> userLists=new ArrayList<TSUser>();
            for (TSUser user : userList) {
                if (s.equals(user.getId())){
                    userLists.add(user);
                }
            }
            if (userLists.size()>0){
                tsUserSet.add(userLists.get(0));
            }
            userLists.clear();
        }


        return tsUserSet;
    }

    @Override
    public boolean haveRecord(String user_id, int year, int month) {
        String hql="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHour> list=this.findHql(hql,user_id,year,month);
        return list.size() > 0;
    }

    @Override
    public TSUser getUserById(String id) {
        String hql="from TSUser u where u.id = ?";
        List<TSUser> list=this.findHql(hql,id);
        if (list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<HrmDate> getTimeList(int year, int month) {
        String hql = "from HrmDate u where 1=1 and YEAR(u.work_date)=? and MONTH(u.work_date)=? order by u.work_date asc";
        return this.findHql(hql,year,month);
    }

    @Override
    public void insertAllWorkHour() {

    }

    /**
     * 为新增员工添加工时信息
     *
     * @param user_id
     */
    @Override
    public void insertNewEmpWorkHour(String user_id) {

        logger.info(">>WorkHourServiceImpl:insertNewEmpWorkHour:");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;


        String cmsql="insert into t_hrm_workhour_cm(id,user_id,work_day,update_day,amorpm,number)" +
                "values";
        String sql="insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm,number)" +
                "values";

        List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);

        String datehql="from HrmDate u where  YEAR(u.work_date)=? and MONTH(u.work_date) =?";
        List<HrmDate> datelist=this.findHql(datehql,year,month);

        appendsql(sql,dateUtilList,user_id,datelist);
        appendsql(cmsql,dateUtilList,user_id,datelist);

    }

    @Override
    public Map<String, List<WorkHour>> getCMCheckWorkHourList(List<Integer> statusList, List<String> projectList,
                                                              List<String> idList, int year, int month) {

        logger.info(">>WorkHourServiceImpl:getCMCheckWorkHourList:");

        Map<String,List<WorkHour>> map=new HashMap<String, List<WorkHour>>();

        String hql="from WorkHour u where u.user_id=? and YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.number=?";

        String hql_number="from WorkHour u where  YEAR(u.work_day)=? and MONTH(u.work_day) =? and u.number=?";
        List<WorkHour> list1=this.findHql(hql_number,year,month,0);

        String hql_none="from WorkHour u where YEAR(u.work_day)=? and MONTH(u.work_day) =?";
        List<WorkHour> list3=this.findHql(hql_none,year,month);

        //第一步判断 statusList (0:未填写满，1:已填写满 是否勾选)
        if(statusList != null && !statusList.isEmpty()) {
            if(projectList != null && !projectList.isEmpty()) {
                for(Integer status:statusList) {
                    for(String code : projectList) {
                        for(String id : idList) {
                            if(status==0) {
                                //根据userid和项目code查询工时
                                List<WorkHour> list_workhour=new ArrayList<WorkHour>();
                                for (WorkHour workhour:list1) {
                                    if (id.equals(workhour.getUser_id()) && code.equals(workhour.getProject_code())){
                                        list_workhour.add(workhour);
                                    }
                                }
                                Boolean flag = true;
                                if(!list_workhour.isEmpty()){

                                    //根据userid查询工时
                                    List<WorkHour> list21=new ArrayList<WorkHour>();
                                    for (WorkHour workhour:list1) {
                                        if (id.equals(workhour.getUser_id())){
                                            list21.add(workhour);
                                        }
                                    }

                                    for(WorkHour workHour : list21) {
                                        String code1 = workHour.getProject_code();
                                        String details = workHour.getWork_details();
                                        if(code1 == null || details == null || "".equals(code1) || "".equals(details)) {
                                            flag = false;
                                        }
                                    }
                                }
                                int count = 1;
                                if(!list_workhour.isEmpty() && count == 1 && !flag){
                                    List<WorkHour> list31=new ArrayList<WorkHour>();
                                    //根据userid查询工时
                                    for (WorkHour workhour:list3) {
                                        if (id.equals(workhour.getUser_id())){
                                            list31.add(workhour);
                                        }
                                    }

                                    map.put(this.getUserById(id).getUserName(),list31);
                                    count++;
                                }
                            }else if(status == 1) {

                                //根据userid和项目code查询工时
                                List<WorkHour> list11=new ArrayList<WorkHour>();
                                for (WorkHour workhour:list1) {
                                    if (id.equals(workhour.getUser_id()) && code.equals(workhour.getProject_code())){
                                        list11.add(workhour);
                                    }
                                }

                                Boolean flag = true;
                                if(!list11.isEmpty()) {

                                    //根据userid和项目code查询工时
                                    List<WorkHour> list21=new ArrayList<WorkHour>();
                                    for (WorkHour workhour:list1) {
                                        if (id.equals(workhour.getUser_id())){
                                            list21.add(workhour);
                                        }
                                    }

                                    for (WorkHour workHour : list21) {
                                        String code1 = workHour.getProject_code();
                                        String details = workHour.getWork_details();
                                        if(code1 == null || details == null || "".equals(code1) || "".equals(details)) {
                                            flag = false;
                                        }
                                    }
                                }
                                int count = 1;
                                if(!list11.isEmpty() && count == 1 && flag){

                                    //根据userid查询工时
                                    List<WorkHour> list31=new ArrayList<WorkHour>();
                                    for (WorkHour workhour:list3) {
                                        if (id.equals(workhour.getUser_id())){
                                            list31.add(workhour);
                                        }
                                    }
                                    map.put(this.getUserById(id).getUserName(),list31);
                                    count++;
                                }
                            }
                        }
                    }
                }
            }else {
                for(Integer status:statusList) {
                    for(String id:idList) {
                        if(status==0) {

                            //根据userid查询工时
                            List<WorkHour> list11=new ArrayList<WorkHour>();
                            for (WorkHour workhour:list1) {
                                if (id.equals(workhour.getUser_id())){
                                    list11.add(workhour);
                                }
                            }

                            Boolean flag = false;
                            for(WorkHour workHour : list11) {
                                String code = workHour.getProject_code();
                                String details = workHour.getWork_details();
                                if(code == null || details == null || "".equals(code) || "".equals(details)) {
                                    flag = true;
                                }
                            }
                            int count = 1;
                            if( count == 1&& flag){

                                //根据userid和项目code查询工时
                                List<WorkHour> list21=new ArrayList<WorkHour>();
                                for (WorkHour workhour:list3) {
                                    if (id.equals(workhour.getUser_id())){
                                        list21.add(workhour);
                                    }
                                }

                                map.put(this.getUserById(id).getUserName(),list21);
                                count++;
                            }
                        }else if(status == 1) {

                            //根据useride查询工时
                            List<WorkHour> list21=new ArrayList<WorkHour>();
                            for (WorkHour workhour:list1) {
                                if (id.equals(workhour.getUser_id())){
                                    list21.add(workhour);
                                }
                            }

                            Boolean flag = true;
                            for (WorkHour workHour : list21) {
                                String code = workHour.getProject_code();
                                String details = workHour.getWork_details();
                                if(code == null || details == null || "".equals(code) || "".equals(details)) {
                                    flag = false;
                                }
                            }
                            int count = 1;
                            if(list1.size()>0 && count == 1 && flag){

                                //根据userid查询工时
                                List<WorkHour> list31=new ArrayList<WorkHour>();
                                for (WorkHour workhour:list3) {
                                    if (id.equals(workhour.getUser_id())){
                                        list31.add(workhour);
                                    }
                                }

                                map.put(this.getUserById(id).getUserName(),list31);
                                count++;
                            }
                        }
                    }
                }
            }
        }else {
            //未勾选  是否填满
            //如果项目为已勾选
            if(projectList!=null&&!projectList.isEmpty()) {
                for(String code:projectList) {
                    for(String id:idList) {

                        //根据userid查询工时
                        List<WorkHour> list11=new ArrayList<WorkHour>();
                        for (WorkHour workhour:list3) {
                            if (id.equals(workhour.getUser_id()) && code.equals(workhour.getProject_code())){
                                list11.add(workhour);
                            }
                        }

                        int count = 1;
                        if(!list11.isEmpty()&&count == 1){

                            //根据userid查询工时
                            List<WorkHour> list21=new ArrayList<WorkHour>();
                            for (WorkHour workhour:list3) {
                                if (id.equals(workhour.getUser_id())){
                                    list21.add(workhour);
                                }
                            }

                            map.put(this.getUserById(id).getUserName(),list21);
                            count++;
                        }
                    }
                }
            }else {
                //项目未勾选
                for(String id:idList) {

                    //根据userid查询工时
                    List<WorkHour> list=new ArrayList<WorkHour>();
                    for (WorkHour workhour:list3) {
                        if (id.equals(workhour.getUser_id())){
                            list.add(workhour);
                        }
                    }

                    if (list.size()>0){
                        map.put(this.getUserById(id).getUserName(),list);
                    }else {

                        String sql="insert into t_hrm_workhour(id,user_id,work_day,update_day,amorpm,number)" +
                                "values";
                        List<DateUtil> dateUtilList=new DateUtil().getDate(year,month);

                        String datehql="from HrmDate u where  YEAR(u.work_date)=? and MONTH(u.work_date) =?";
                        List<HrmDate> datelist=this.findHql(datehql,year,month);

                        appendsql(sql,dateUtilList,id,datelist);

                        List<WorkHour> newlist=this.findHql(hql,id,year,month);
                        map.put(this.getUserById(id).getUserName(),newlist);
                    }
                }
            }

        }
        return map;
    }

    /**
     * 拼接Sql,执行插入方法
     * @param sql
     * @param dateUtilList
     * @param user_id
     */
    private void appendsql(String sql,List<DateUtil> dateUtilList,String user_id,List<HrmDate> datelist){

        StringBuffer appendsql =new StringBuffer();
        StringBuffer appendsql1 =new StringBuffer();


        for (DateUtil dateUtil:dateUtilList){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            List<HrmDate> hrmDateList=null;
            for (HrmDate hrmdate:datelist){

                Date getData = java.sql.Date.valueOf(dateUtil.getDate());
                //返回节假日类型
                if (sdf.format(getData).equals(sdf.format(hrmdate.getWork_date()))){
                    hrmDateList=new ArrayList<HrmDate>();
                    hrmDateList.add(hrmdate);
                }

            }


            appendsql.append("( ' "+UUID.randomUUID().toString());
            appendsql.append("','"+user_id);
            appendsql.append("','"+dateUtil.getDate());
            appendsql.append("','"+sdf.format(new Date()));
            appendsql.append("','"+"am");
            appendsql.append("','"+hrmDateList.get(0).getStatus());
            appendsql.append(" '),\n");

            appendsql1.append("('"+UUID.randomUUID().toString());
            appendsql1.append("','"+user_id);
            appendsql1.append("','"+dateUtil.getDate());
            appendsql1.append("','"+sdf.format(new Date()));
            appendsql1.append("','"+"pm");
            appendsql1.append("',"+hrmDateList.get(0).getStatus());
            appendsql1.append("),\n");

        }
                logger.info(">>WorkHourServiceImpl:appendsql:appendsql");

        this.executeSql(sql+appendsql.toString().substring(0,appendsql.length()-2));
        this.executeSql(sql+appendsql1.toString().substring(0,appendsql1.length()-2));

    }

}
