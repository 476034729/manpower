package org.jeecgframework.web.hrm.service.impl;

import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.dao.AttendanceDao;
import org.jeecgframework.web.hrm.service.IAttendanceRecordService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Service("attendanceRecordService")
@Transactional
public class AttendanceRecordServiceImpl extends CommonServiceImpl implements IAttendanceRecordService {

	private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(AttendanceRecordServiceImpl.class);

	@Autowired
	private AttendanceDao attendanceDao;

	@Override
	public List<AttendanceRecordResult> getAllAttendanceRecords(String chooseTime) {

		logger.info(">>AttendanceRecordServiceImpl:getAllAttendanceRecords: "+new Date().toString());

		List<TSUser> userList = this.commonDao.findByQueryString("from TSUser u where u.site='CD' order by u.jobNum");
		List<AttendanceRecordResult> results = new ArrayList<AttendanceRecordResult>();
		for (TSUser user : userList) {
			AttendanceRecordResult result = new AttendanceRecordResult();
			result.setJobNum(user.getJobNum());
			result.setUserName(user.getUserName());
			List<HrmAttendanceRecord> attendanceRecords = attendanceDao.getAllAttendanceRecordsByDate(chooseTime,
					user.getJobNum());
			logger.info(">>AttendanceRecordServiceImpl:getAllAttendanceRecords:attendanceDao.getAllAttendanceRecordsByDate "+user.getJobNum());
			result.setAttendanceRecords(attendanceRecords);
			results.add(result);
		}
		return results;
	}

	/**
	 * 每年1月1日自动初始化员工月份加班表
	 */
	@Override
	public void insertAllMonthOvertime() {

		logger.info(">>AttendanceRecordServiceImpl:insertAllMonthOvertime: "+new Date().toString());

		/*
		 * String hql="from TSUser u where u.userstatus=?"; List<TSUser> list =
		 * this.findHql(hql,"在职");
		 */
		List<TSUser> list = this.getList(TSUser.class);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for (int i = 0; i < list.size(); i++) {
			HrmMonthOvertime hrmMonthOvertime = new HrmMonthOvertime();
			hrmMonthOvertime.setId(UUID.randomUUID().toString());
			hrmMonthOvertime.setUser_id(list.get(i).getId());
			hrmMonthOvertime.setUser_name(list.get(i).getRealName());
			hrmMonthOvertime.setYear(year);
			hrmMonthOvertime.setJanuary_overtime(0.0);
			hrmMonthOvertime.setJanuary_surtime(0.0);
			hrmMonthOvertime.setFebruary_overtime(0.0);
			hrmMonthOvertime.setFebruary_surtime(0.0);
			hrmMonthOvertime.setMarch_overtime(0.0);
			hrmMonthOvertime.setMarch_surtime(0.0);
			hrmMonthOvertime.setApril_overtime(0.0);
			hrmMonthOvertime.setApril_surtime(0.0);
			hrmMonthOvertime.setMay_overtime(0.0);
			hrmMonthOvertime.setMarch_surtime(0.0);
			hrmMonthOvertime.setJune_overtime(0.0);
			hrmMonthOvertime.setJune_surtime(0.0);
			hrmMonthOvertime.setJuly_overtime(0.0);
			hrmMonthOvertime.setJuly_surtime(0.0);
			hrmMonthOvertime.setAugust_overtime(0.0);
			hrmMonthOvertime.setAugust_surtime(0.0);
			hrmMonthOvertime.setSeptember_overtime(0.0);
			hrmMonthOvertime.setSeptember_surtime(0.0);
			hrmMonthOvertime.setOctober_overtime(0.0);
			hrmMonthOvertime.setOctober_surtime(0.0);
			hrmMonthOvertime.setNovember_overtime(0.0);
			hrmMonthOvertime.setNovember_surtime(0.0);
			hrmMonthOvertime.setDecember_overtime(0.0);
			hrmMonthOvertime.setDecember_surtime(0.0);
			hrmMonthOvertime.setCreate_time(new Date());
			hrmMonthOvertime.setModify_time(new Date());
			this.save(hrmMonthOvertime);
		}
	}

	/**
	 * 根据用户id查询该月的累积加班时间
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Double getSunMonthOvertime(String userId, Integer month) {
		/*
		 * HrmMonthOvertime hrmMonthOvertime =
		 * this.findUniqueByProperty(HrmMonthOvertime.class,"user_id",userId); Double
		 * countOvertime = 0.0; if(month==1){ countOvertime =
		 * hrmMonthOvertime.getJanuary_overtime(); }else if(month==2){ countOvertime =
		 * hrmMonthOvertime.getFebruary_overtime(); }else if(month==3){ countOvertime =
		 * hrmMonthOvertime.getMarch_overtime(); }else if(month==4){ countOvertime =
		 * hrmMonthOvertime.getApril_overtime(); }else if(month==5){ countOvertime =
		 * hrmMonthOvertime.getMay_overtime(); }else if(month==6){ countOvertime =
		 * hrmMonthOvertime.getJune_overtime(); }else if(month==7){ countOvertime =
		 * hrmMonthOvertime.getJuly_overtime(); }else if(month==8){ countOvertime =
		 * hrmMonthOvertime.getAugust_overtime(); }else if(month==9){ countOvertime =
		 * hrmMonthOvertime.getSeptember_overtime(); }else if(month==10){ countOvertime
		 * = hrmMonthOvertime.getOctober_overtime(); }else if(month==11){ countOvertime
		 * = hrmMonthOvertime.getNovember_overtime(); }else if(month==12){ countOvertime
		 * = hrmMonthOvertime.getDecember_overtime(); }
		 */


		Double countOvertime = 0.0;
		String sql = "select sum(count_time) from t_hrm_overtime where MONTH(end_time)=? and user_id=? and status=?";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, month);
		query.setParameter(1, userId);
		query.setParameter(2, 1);
		countOvertime = (Double) query.uniqueResult();
		return countOvertime;
	}


	/**
	 * 保存加班申请
	 *
	 * @param overtimeApply
	 * @param startDate
	 * @param endDate
	 */
/*	@Override
	public void saveOvertimeApply(HrmOvertimeApply overtimeApply, String[] startDate, String[] endDate,
			String[] countTime, String[] surplusTime, TSUser tsUser, Integer copyType,
			Integer immediateSupervisorExamine) {
		// 判断是否为修改
		System.out.println("ssssssssssss");
		Boolean flag = true;
		if (overtimeApply.getId() == null || overtimeApply.getId().trim().length() == 0) {
			overtimeApply.setId(UUID.randomUUID().toString());
			flag = false;
		}
		if (immediateSupervisorExamine != null) {
			overtimeApply.setImmediate_supervisor_examine(immediateSupervisorExamine);
		}
		if (overtimeApply.getImmediate_supervisor_examine() == null) {
			overtimeApply.setImmediate_supervisor_examine(HrmOvertimeApply.IMEXAMINE);
			overtimeApply.setStatus(HrmOvertimeApply.IMEXAMINE);
		} else {
			overtimeApply.setStatus(HrmOvertimeApply.EXAMINEING);
			if (overtimeApply.getDivision_manager_examine() != null) {
				if (overtimeApply.getDivision_manager_examine() == 2) {
					overtimeApply.setStatus(HrmOvertimeApply.DISAGREE);
				}
			}
		}
		overtimeApply.setPersonnel_matters_status(HrmOvertime.UNAUDITED);
		overtimeApply.setCreate_date(new Date());
		overtimeApply.setModify_date(new Date());
		if (overtimeApply.getCopy_type() != null) {
			if (overtimeApply.getCopy_type() == 1) {
				overtimeApply.setDivision_manager_examine(HrmOvertimeApply.NOAUDIT);
			} else if (overtimeApply.getCopy_type() == 2) {
				overtimeApply.setDivision_manager_examine(HrmOvertimeApply.IMEXAMINE);
			}
		}
		overtimeApply.setUser_status(HrmOvertimeApply.UNCONFIRM);
=======*/
    /**
     * 保存加班申请
     *
     * @param overtimeApply
     * @param startDate
     * @param endDate
     */
    @Override
    public void saveOvertimeApply(HrmOvertimeApply overtimeApply, String[] startDate, String[] endDate,String[] countTime,String[] surplusTime,TSUser tsUser,Integer copyType,Integer immediateSupervisorExamine) {
        //判断是否为修改

		logger.info(">>AttendanceRecordServiceImpl:saveOvertimeApply: "+new Date().toString());

		Boolean flag = true;
        if(overtimeApply.getId()==null||overtimeApply.getId().trim().length()==0){
            overtimeApply.setId(UUID.randomUUID().toString());
            flag = false;
        }
        if(immediateSupervisorExamine!=null){
            overtimeApply.setImmediate_supervisor_examine(immediateSupervisorExamine);
        }
        if(overtimeApply.getImmediate_supervisor_examine()==null){
            overtimeApply.setImmediate_supervisor_examine(HrmOvertimeApply.IMEXAMINE);
            overtimeApply.setStatus(HrmOvertimeApply.IMEXAMINE);
        }else{
            overtimeApply.setStatus(HrmOvertimeApply.EXAMINEING);
            if(overtimeApply.getDivision_manager_examine()!=null){
                if(overtimeApply.getDivision_manager_examine()==2){
                    overtimeApply.setStatus(HrmOvertimeApply.DISAGREE);
                }
            }
        }
        overtimeApply.setPersonnel_matters_status(HrmOvertime.UNAUDITED);
        overtimeApply.setCreate_date(new Date());
        overtimeApply.setModify_date(new Date());
        if(overtimeApply.getCopy_type()!=null){
            if(overtimeApply.getCopy_type()==1){
                overtimeApply.setDivision_manager_examine(HrmOvertimeApply.NOAUDIT);
            }else if(overtimeApply.getCopy_type()==2){
                overtimeApply.setDivision_manager_examine(HrmOvertimeApply.IMEXAMINE);
            }
        }
        overtimeApply.setUser_status(HrmOvertimeApply.UNCONFIRM);

		// 如果为修改，则删除以前的加班时间，保存现在的
		if (flag) {
			String sql = "delete from t_hrm_overtime where overtime_apply_id=?";
			Query query = this.getSession().createSQLQuery(sql).setString(0, overtimeApply.getId());
			query.executeUpdate();
			overtimeApply.setStatus(HrmOvertimeApply.DISAGREE);
			overtimeApply.setCopy_type(copyType);
			overtimeApply.setModify_date(new Date());
		}
		this.saveOrUpdate(overtimeApply);

		List<HrmOvertime> hrmOvertimeList = new ArrayList<HrmOvertime>();
		// 保存加班时间
		for (int i = 0; i < startDate.length; i++) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			HrmOvertime hrmOvertime = new HrmOvertime();
			hrmOvertime.setId(UUID.randomUUID().toString());
			hrmOvertime.setHrmOvertimeApply(overtimeApply);
			Date sdate = null;
			Date edate = null;
			try {
				sdate = simpleDateFormat.parse(startDate[i].replace("T", " "));
				edate = simpleDateFormat.parse(endDate[i].replace("T", " "));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			Double countTime1 = Double.parseDouble(countTime[i]);
			Double surlpusTime1 = Double.parseDouble(surplusTime[i]);
			System.out.println(sdate+"455444444444455555555");
			hrmOvertime.setStart_time(sdate);
			hrmOvertime.setEnd_time(edate);
			hrmOvertime.setStatus(HrmOvertime.UNAUDITED);
			hrmOvertime.setUser_id(tsUser.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timeStr = sdf.format(sdate);
			Date byTime = null;
			try {
				byTime = sdf.parse(timeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			byTime.setMonth(byTime.getMonth() + 3);
			hrmOvertime.setBy_time(byTime);
			hrmOvertime.setCount_time(countTime1);
			hrmOvertime.setSurplus_time(surlpusTime1);
			hrmOvertime.setCreate_time(new Date());
			hrmOvertime.setModify_time(new Date());
			this.save(hrmOvertime);
			System.out.println(hrmOvertime.getStart_time()+"ssssssssssssssssssss");
			hrmOvertimeList.add(hrmOvertime);
		}
		overtimeApply.setHrmOvertimes(hrmOvertimeList);
		this.saveOrUpdate(overtimeApply);
	}

	/**
	 * 查询加班申请列表
	 *
	 * @param tsUser
	 * @param roleType
	 * @return
	 */
	@Override
	public List<HrmOvertimeApply> findOvertimeApplyList(TSUser tsUser, Integer roleType, Integer status,
			String userName, Date startDate, Date endDate) {


		String user_name = "";
		try {
			if (userName != null && userName.trim().length() > 0) {
				user_name = new String(userName.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<HrmOvertimeApply> overtimeApplieList = new ArrayList<HrmOvertimeApply>();
		if (roleType == 1) {
			// 部门负责人
			List<Object> params1 = new ArrayList<Object>();
				// overtimeApplies =
				// this.findByProperty(HrmOvertimeApply.class,"user_id",tsUser.getId());
				String hql3 = "from HrmOvertimeApply where dept=?";
				List<Object> params2 = new ArrayList<Object>();
				params2.add(tsUser.getDepartment());
				if (status != null && status >= 0) {
					hql3 = hql3 + " and status=?";
					params2.add(status);
				}
				if (startDate != null) {
					hql3 = hql3 + " and create_date>=?";
					params2.add(startDate);
				}
				if (endDate != null) {
					hql3 = hql3 + " and create_date<=?";
					params2.add(endDate);
				}
				if (user_name != null && user_name.trim().length() > 0) {
					hql3 = hql3 +  " and user_name=?";
					params2.add(user_name);
				}
				hql3 = hql3 + " order by create_date desc,immediate_supervisor_examine asc";
			System.out.println(hql3+"ssdddd");
				overtimeApplieList = this.findHql(hql3, params2.toArray());
		} else if (roleType == 2) {
			// 部门经理
			String hql = "from HrmOvertimeApply where copy_type=?";
			List<Object> params = new ArrayList<Object>();
			params.add(HrmOvertimeApply.MANAGER);
			if (status != null && status >= 0) {
				hql = hql + " and status=?";
				params.add(status);
			}
			if (startDate != null) {
				hql = hql + " and create_date>=?";
				params.add(startDate);
			}
			if (endDate != null) {
				hql = hql + " and create_date<=?";
				params.add(endDate);
			}
			if (user_name != null && user_name.trim().length() > 0) {
				hql = hql + " and user_name=?";
				params.add(user_name);
			}
			hql = hql + "order by create_date desc,division_manager_examine asc";
			overtimeApplieList = this.findHql(hql, params.toArray());
		} else if (roleType == 3) {
			// 行政
			String hql = "from HrmOvertimeApply where user_status=?";
			// and personnel_matters_name=?
			List<Object> params = new ArrayList<Object>();
			params.add(HrmOvertimeApply.CONFIRM);
			if (status != null && status >= 0) {
				hql = hql + " and status=?";
				params.add(status);
			}
			if (startDate != null) {
				hql = hql + " and create_date>=?";
				params.add(startDate);
			}
			if (endDate != null) {
				hql = hql + " and create_date<=?";
				params.add(endDate);
			}
			if (user_name != null && user_name.trim().length() > 0) {
				hql = hql + " and user_name=?";
				params.add(user_name);
			}
			hql = hql + "order by create_date desc,personnel_matters_status asc";
			overtimeApplieList = this.findHql(hql, params.toArray());
		} else if (roleType == 0) {
			// 普通员工
			String hql2 = "from HrmOvertimeApply where user_id=?";
			List<Object> params3 = new ArrayList<Object>();
			params3.add(tsUser.getId());
			if (status != null && status >= 0) {
				hql2 = hql2 + " and status=?";
				params3.add(status);
			}
			if (startDate != null) {
				hql2 = hql2 + " and create_date>=?";
				params3.add(startDate);
			}
			if (endDate != null) {
				hql2 = hql2 + " and create_date<=?";
				params3.add(endDate);
			}
			hql2 = hql2 + "order by create_date desc";
			overtimeApplieList = this.findHql(hql2, params3.toArray());
		}
		return overtimeApplieList;
	}

	/**
	 * 为新员工添加该年的加班时间记录
	 *
	 * @param user
	 */
	@Override
	public void insertNewEmpMonthOvertime(TSUser user) {

		logger.info(">>AttendanceRecordServiceImpl:insertNewEmpMonthOvertime: "+new Date().toString());

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		HrmMonthOvertime hrmMonthOvertime = new HrmMonthOvertime();
		hrmMonthOvertime.setId(UUID.randomUUID().toString());
		hrmMonthOvertime.setUser_id(user.getId());
		hrmMonthOvertime.setUser_name(user.getRealName());
		hrmMonthOvertime.setYear(year);
		hrmMonthOvertime.setJanuary_overtime(0.0);
		hrmMonthOvertime.setJanuary_surtime(0.0);
		hrmMonthOvertime.setFebruary_overtime(0.0);
		hrmMonthOvertime.setFebruary_surtime(0.0);
		hrmMonthOvertime.setMarch_overtime(0.0);
		hrmMonthOvertime.setMarch_surtime(0.0);
		hrmMonthOvertime.setApril_overtime(0.0);
		hrmMonthOvertime.setApril_surtime(0.0);
		hrmMonthOvertime.setMay_overtime(0.0);
		hrmMonthOvertime.setMarch_surtime(0.0);
		hrmMonthOvertime.setJune_overtime(0.0);
		hrmMonthOvertime.setJune_surtime(0.0);
		hrmMonthOvertime.setJuly_overtime(0.0);
		hrmMonthOvertime.setJuly_surtime(0.0);
		hrmMonthOvertime.setAugust_overtime(0.0);
		hrmMonthOvertime.setAugust_surtime(0.0);
		hrmMonthOvertime.setSeptember_overtime(0.0);
		hrmMonthOvertime.setSeptember_surtime(0.0);
		hrmMonthOvertime.setOctober_overtime(0.0);
		hrmMonthOvertime.setOctober_surtime(0.0);
		hrmMonthOvertime.setNovember_overtime(0.0);
		hrmMonthOvertime.setNovember_surtime(0.0);
		hrmMonthOvertime.setDecember_overtime(0.0);
		hrmMonthOvertime.setDecember_surtime(0.0);
		hrmMonthOvertime.setCreate_time(new Date());
		hrmMonthOvertime.setModify_time(new Date());
		this.save(hrmMonthOvertime);
	}

	/**
	 * 根据id查询加班申请记录
	 *
	 * @param id
	 * @return
	 */
	@Override
	public HrmOvertimeApply getOvertimeApplyById(String id) {
		HrmOvertimeApply hrmOvertimeApply = this.get(HrmOvertimeApply.class, id);
		return hrmOvertimeApply;
	}

	/**
	 * 加班申请审批保存
	 *
	 * @param overtimeApplyId
	 * @param roleType
	 * @param immediateSupervisorExamine
	 * @param immediateSupervisorSign
	 * @param copyType
	 * @param personnelMattersName
	 * @param divisionManagerExamine
	 * @param divisionManagerSign
	 * @param personnelMattersStatus
	 */
	@Override
	public void saveOvertimeApplyExamination(String overtimeApplyId, Integer roleType,
			Integer immediateSupervisorExamine, String immediateSupervisorSign, Integer copyType,
			String personnelMattersName, Integer divisionManagerExamine, String divisionManagerSign,
			Integer personnelMattersStatus, String adminName) {

		logger.info(">>AttendanceRecordServiceImpl:saveOvertimeApplyExamination: "+new Date().toString());

		HrmOvertimeApply hrmOvertimeApply = this.get(HrmOvertimeApply.class, overtimeApplyId);
		if (roleType == 1) {
			// 上级领导审批保存
			hrmOvertimeApply.setImmediate_supervisor_examine(immediateSupervisorExamine);
			hrmOvertimeApply.setImmediate_supervisor_sign(immediateSupervisorSign);
			hrmOvertimeApply.setCopy_type(copyType);
			if (immediateSupervisorExamine == 1) {
				hrmOvertimeApply.setStatus(HrmOvertimeApply.EXAMINEING);
				if (copyType == 1) {
					// 行政
					hrmOvertimeApply.setPersonnel_matters_name(personnelMattersName);
					hrmOvertimeApply.setDivision_manager_examine(HrmOvertimeApply.NOAUDIT);
				} else if (copyType == 2) {
					// 部门经理
					hrmOvertimeApply.setDivision_manager_sign(divisionManagerSign);
					hrmOvertimeApply.setDivision_manager_examine(HrmOvertimeApply.IMEXAMINE);
				}
			} else if (immediateSupervisorExamine == 2) {
				hrmOvertimeApply.setStatus(HrmOvertimeApply.DISAGREE);
			}
		} else if (roleType == 2) {
			// 部门经理审批保存
			hrmOvertimeApply.setDivision_manager_examine(divisionManagerExamine);
			hrmOvertimeApply.setPersonnel_matters_name(adminName);
			if (divisionManagerExamine == 1) {
				hrmOvertimeApply.setStatus(HrmOvertimeApply.EXAMINEING);
			} else if (divisionManagerExamine == 2) {
				hrmOvertimeApply.setStatus(HrmOvertimeApply.DISAGREE);
			}
		} else if (roleType == 3) {
			// 行政人员关闭
			hrmOvertimeApply.setPersonnel_matters_status(personnelMattersStatus);
			if (personnelMattersStatus == 1) {
				hrmOvertimeApply.setStatus(HrmOvertimeApply.AGREE);
			}
		}
		this.saveOrUpdate(hrmOvertimeApply);
	}

	/***
	 * 保存用户加班申请确认
	 * 
	 * @param overtimeApplyId
	 */
	@Override
	public Boolean saveUserConfirm(String overtimeApplyId) {


		Boolean flag = false;
		HrmOvertimeApply hrmOvertimeApply = this.get(HrmOvertimeApply.class, overtimeApplyId);
		if (hrmOvertimeApply != null) {
			hrmOvertimeApply.setUser_status(HrmOvertimeApply.CONFIRM);
			hrmOvertimeApply.setModify_date(new Date());
			this.saveOrUpdate(hrmOvertimeApply);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 保存人事加班申请确认
	 *
	 * @param overtimeApplyId
	 * @return
	 */
	@Override
	public Boolean saveAdminConfirm(String overtimeApplyId) {

		logger.info(">>AttendanceRecordServiceImpl:saveAdminConfirm: "+new Date().toString());

		Boolean flag = false;
		HrmOvertimeApply hrmOvertimeApply = this.get(HrmOvertimeApply.class, overtimeApplyId);
		if (hrmOvertimeApply != null) {
			hrmOvertimeApply.setPersonnel_matters_status(HrmOvertimeApply.CONFIRM);
			hrmOvertimeApply.setStatus(HrmOvertimeApply.CONFIRM);
			hrmOvertimeApply.setModify_date(new Date());
			this.saveOrUpdate(hrmOvertimeApply);

			String hql = "from HrmOvertime where overtime_apply_id=?";
			List<HrmOvertime> hrmOvertimeList = this.findHql(hql,hrmOvertimeApply.getId());
			if(hrmOvertimeList!=null && hrmOvertimeList.size()>0){
				for(int i=0;i<hrmOvertimeList.size();i++){
					HrmOvertime hrmOvertime = hrmOvertimeList.get(i);
					hrmOvertime.setStatus(HrmOvertime.AGREE);
					this.saveOrUpdate(hrmOvertime);
				}
			}
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取用户的当月可换休的时间
	 *
	 * @param userId
	 * @param date
	 * @return
	 */
	@Override
	public Double countBreakOff(String userId, String[] date) {
		/*
		 * String hql = "from HrmMonthOvertime where user_id=? and year=?";
		 * List<HrmMonthOvertime> hrmMonthOvertimes1 = this.findHql(hql,userId,year);
		 * List<HrmMonthOvertime> hrmMonthOvertimes2 = this.findHql(hql,userId,year-1);
		 * Double countTime = 0.0; if(hrmMonthOvertimes1.size()>0){ HrmMonthOvertime
		 * hrmMonthOvertime1 = hrmMonthOvertimes1.get(0); //1月 if(month==1){
		 * if(hrmMonthOvertimes2.size()>0){ HrmMonthOvertime hrmMonthOvertime2 =
		 * hrmMonthOvertimes2.get(0); //去年的12月份
		 * if(hrmMonthOvertime2.getDecember_surtime()==null){ countTime = countTime+0;
		 * }else{ countTime = hrmMonthOvertime2.getDecember_surtime(); } //去年的11月份
		 * if(hrmMonthOvertime2.getNovember_surtime()==null){ countTime = countTime + 0;
		 * }else{ countTime = countTime + hrmMonthOvertime2.getNovember_surtime(); } }
		 * //今年的1月份 if(hrmMonthOvertime1.getJanuary_surtime()==null){ countTime =
		 * countTime + 0; }else{ countTime = countTime +
		 * hrmMonthOvertime1.getJanuary_surtime(); } } //2月 if(month==2){
		 * if(hrmMonthOvertimes2.size()>0){ HrmMonthOvertime hrmMonthOvertime2 =
		 * hrmMonthOvertimes2.get(0); //去年的12月份
		 * if(hrmMonthOvertime2.getDecember_surtime()==null){ countTime = countTime+0;
		 * }else{ countTime = hrmMonthOvertime2.getDecember_surtime(); } } //今年1月
		 * if(hrmMonthOvertime1.getJanuary_surtime()==null){ countTime = countTime + 0;
		 * }else{ countTime = countTime + hrmMonthOvertime1.getJanuary_surtime(); }
		 * //今年2月 if(hrmMonthOvertime1.getFebruary_surtime()==null){ countTime =
		 * countTime + 0; }else{ countTime = countTime +
		 * hrmMonthOvertime1.getFebruary_surtime(); } } //3月 if(month==3){ //今年1月
		 * if(hrmMonthOvertime1.getJanuary_surtime()==null){ countTime = countTime + 0;
		 * }else { countTime = countTime + hrmMonthOvertime1.getJanuary_surtime(); }
		 * //今年2月 if(hrmMonthOvertime1.getFebruary_surtime()==null){ countTime =
		 * countTime + 0; }else{ countTime = countTime +
		 * hrmMonthOvertime1.getFebruary_surtime(); } //今年3月
		 * if(hrmMonthOvertime1.getMarch_surtime()==null){ countTime = countTime + 0;
		 * }else{ countTime = countTime + hrmMonthOvertime1.getMarch_surtime(); } } }
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		try {
			date1 = simpleDateFormat.parse(date[0].replace("T", " "));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String sql = "select sum(surplus_time) from t_hrm_overtime where user_id=? and status=? and by_time>=?";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, userId);
		query.setParameter(1, 1);
		query.setParameter(2, date1);
		Double countTime = (Double) query.uniqueResult();
		return countTime;
	}

	/**
	 * 获取当前用户的年假使用天数
	 *
	 * @param userId
	 * @param year
	 * @return
	 */
	@Override
	public Double countAnnualLeave(String userId, Integer year) {
		System.out.println(userId+"--------------"+year);
		String hql = "from HrmAnnualLeaveStatistics where user_id=? and year=?";
		List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatistics = this.findHql(hql, userId,year);
		System.out.println(hrmAnnualLeaveStatistics);
		Double countDays = 0.0;
		if (hrmAnnualLeaveStatistics.size() > 0) {
			HrmAnnualLeaveStatistics hrmAnnualLeaveStatistics1 = hrmAnnualLeaveStatistics.get(0);
			countDays = hrmAnnualLeaveStatistics1.getCount_annual_leave();
		}
		return countDays;
	}

	/**
	 * 获取用户的当年的病假剩余天数
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Double countSickLeave(String userId, Integer year) {
		String hql = "from HrmSickLeave where user_id=? and year=?";
		List<HrmSickLeave> hrmSickLeaves = this.findHql(hql, userId, year);
		Double countDays = 0.0;
		if (hrmSickLeaves.size() > 0) {
			HrmSickLeave hrmSickLeave = hrmSickLeaves.get(0);
			countDays = hrmSickLeave.getDays_remaining();
		}
		return countDays;
	}

	/**
	 * 保存换休申请
	 *
	 * @param changeOfRestApply
	 * @param user
	 * @param startDate
	 * @param endDate
	 */
	@Override
	public void saveVacationapply(HrmChangeOfRestApply changeOfRestApply, TSUser user, String[] startDate,
			String[] endDate) {
		// 判断是否为修改

		logger.info(">>AttendanceRecordServiceImpl:saveVacationapply: "+new Date().toString());

		Boolean flag = true;
		if (changeOfRestApply.getId() == null || changeOfRestApply.getId() == "") {
			changeOfRestApply.setId(UUID.randomUUID().toString());
			changeOfRestApply.setCreate_date(new Date());
			flag = false;
		}
		changeOfRestApply.setUser_id(user.getId());
		changeOfRestApply.setUser_name(user.getUserName());
		if (changeOfRestApply.getImmediate_supervisor_examine() == null) {
			changeOfRestApply.setImmediate_supervisor_examine(HrmChangeOfRestApply.IMEXAMINE);
		}
		if (changeOfRestApply.getCount_day() != null) {
			Double countDays = changeOfRestApply.getCount_day();
			if (countDays > 3) {
				if(!flag){
					changeOfRestApply.setDivision_manager_examine(HrmChangeOfRestApply.IMEXAMINE);
				}
			} else {
				changeOfRestApply.setDivision_manager_examine(HrmChangeOfRestApply.NOHANDEL);
			}
		}
		changeOfRestApply.setModify_date(new Date());
		if (changeOfRestApply.getImmediate_supervisor_examine() == HrmChangeOfRestApply.IMEXAMINE) {
			changeOfRestApply.setStatus(HrmChangeOfRestApply.IMEXAMINE);
		} else {
			changeOfRestApply.setStatus(HrmChangeOfRestApply.EXAMING);
		}
		changeOfRestApply.setPersonnel_matters_status(HrmChangeOfRestApply.IMEXAMINE);
		changeOfRestApply.setUser_status(HrmChangeOfRestApply.IMEXAMINE);

		// 如果为修改，则删除以前的换休时间，保存现在的
		if (flag) {
			String sql = "delete from t_hrm_changofrest_time where changofrest_id=?";
			Query query = this.getSession().createSQLQuery(sql).setString(0, changeOfRestApply.getId());
			query.executeUpdate();
			changeOfRestApply.setStatus(HrmChangeOfRestApply.DISAGREE);
			changeOfRestApply.setModify_date(new Date());
		}
		this.saveOrUpdate(changeOfRestApply);

		// 保存换休时间
		List<HrmChangofrestTime> hrmChangofrestTimes = new ArrayList<HrmChangofrestTime>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i = 0; i < startDate.length; i++) {
			HrmChangofrestTime hrmChangofrestTime = new HrmChangofrestTime();
			hrmChangofrestTime.setId(UUID.randomUUID().toString());
			hrmChangofrestTime.setHrmChangeOfRestApply(changeOfRestApply);
			Date sdate = null;
			Date edate = null;
			try {
				sdate = simpleDateFormat.parse(startDate[i].replace("T", " "));
				edate = simpleDateFormat.parse(endDate[i].replace("T", " "));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hrmChangofrestTime.setStart_time(sdate);
			hrmChangofrestTime.setEnd_time(edate);
			hrmChangofrestTime.setStatus(HrmChangofrestTime.IMEXAMINE);
			hrmChangofrestTime.setVacation_type(changeOfRestApply.getVacation_type());
			hrmChangofrestTime.setCreate_time(new Date());
			hrmChangofrestTime.setModify_time(new Date());
			this.save(hrmChangofrestTime);
			hrmChangofrestTimes.add(hrmChangofrestTime);
		}
		changeOfRestApply.setHrmChangofrestTimes(hrmChangofrestTimes);
		this.saveOrUpdate(changeOfRestApply);
	}

	/**
	 * 查询换休申请列表
	 *
	 * @param tsUser
	 * @param roleType
	 * @param status
	 * @param userName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<HrmChangeOfRestApply> findVacationApplyList(TSUser tsUser, Integer roleType, Integer status,
			String userName, Date startDate, Date endDate) {


		String user_name = "";
		try {
			if (userName != null && userName.trim().length() > 0) {
				user_name = new String(userName.getBytes("iso-8859-1"), "utf-8");
				System.out.println(user_name);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<HrmChangeOfRestApply> hrmChangeOfRestApplyList = new ArrayList<HrmChangeOfRestApply>();
		if (roleType == 1) {
			// 部门负责人
				String hql3 = "from HrmChangeOfRestApply where dept=?";
				List<Object> params2 = new ArrayList<Object>();
				params2.add(tsUser.getDepartment());
				if (status != null && status >= 0) {
					hql3 = hql3 + " and status=?";
					params2.add(status);
				}
				if (startDate != null) {
					hql3 = hql3 + " and create_date>=?";
					params2.add(startDate);
				}
				if (endDate != null) {
					hql3 = hql3 + " and create_date<=?";
					params2.add(endDate);
				}if (user_name != null && user_name.trim().length() > 0) {
					hql3 = hql3 + " and user_name=?";
					params2.add(user_name);
				}
				hql3 = hql3 + " order by create_date desc,immediate_supervisor_examine asc";
				hrmChangeOfRestApplyList = this.findHql(hql3, params2.toArray());
				
		} else if (roleType == 2) {
			// 部门经理
			String hql = "from HrmChangeOfRestApply where division_manager_examine!=?";
			List<Object> params = new ArrayList<Object>();
			params.add(HrmChangeOfRestApply.NOHANDEL);
			if (status != null && status >= 0) {
				hql = hql + " and status=?";
				params.add(status);
			}
			if (startDate != null) {
				hql = hql + " and create_date>=?";
				params.add(startDate);
			}
			if (endDate != null) {
				hql = hql + " and create_date<=?";
				params.add(endDate);
			}
			if (user_name != null && user_name.trim().length() > 0) {
				hql = hql + " and user_name=?";
				params.add(user_name);
			}
			hql = hql + "order by create_date desc,division_manager_examine asc";
			hrmChangeOfRestApplyList = this.findHql(hql, params.toArray());
		} else if (roleType == 3) {
			// 行政
			String hql = "from HrmChangeOfRestApply where (user_status=? or user_id=?)";
			List<Object> params = new ArrayList<Object>();
			params.add(HrmChangeOfRestApply.AGREE);
			params.add(tsUser.getId());
			if (status != null && status >= 0) {
				hql = hql + " and status=?";
				params.add(status);
			}
			if (startDate != null) {
				hql = hql + " and create_date>=?";
				params.add(startDate);
			}
			if (endDate != null) {
				hql = hql + " and create_date<=?";
				params.add(endDate);
			}
			if (user_name != null && user_name.trim().length() > 0) {
				hql = hql + " and user_name=?";
				params.add(user_name);
			}
			hql = hql + "order by create_date desc,personnel_matters_status asc";
			hrmChangeOfRestApplyList = this.findHql(hql, params.toArray());
		} else if (roleType == 0) {
			// 普通员工
			String hql2 = "from HrmChangeOfRestApply where user_id=?";
			List<Object> params3 = new ArrayList<Object>();
			params3.add(tsUser.getId());
			if (status != null && status >= 0) {
				hql2 = hql2 + " and status=?";
				params3.add(status);
			}
			if (startDate != null) {
				hql2 = hql2 + " and create_date>=?";
				params3.add(startDate);
			}
			if (endDate != null) {
				hql2 = hql2 + " and create_date<=?";
				params3.add(endDate);
			}
			hql2 = hql2 + "order by create_date desc";
			hrmChangeOfRestApplyList = this.findHql(hql2, params3.toArray());
		}
		return hrmChangeOfRestApplyList;
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public HrmChangeOfRestApply getVacationApplyById(String id) {
		HrmChangeOfRestApply hrmChangeOfRestApply = this.get(HrmChangeOfRestApply.class, id);
		return hrmChangeOfRestApply;
	}

	/**
	 * 保存换休审批
	 *
	 * @param vacationId
	 * @param roleType
	 * @param immediate_supervisor_examine
	 * @param immediate_supervisor_advice
	 * @param division_manager_examine
	 * @param division_manager_advice
	 */
	@Override
	public void saveVacationapplyExamination(String vacationId, Integer roleType, Integer immediate_supervisor_examine,
			String immediate_supervisor_advice, Integer division_manager_examine, String division_manager_advice,String immediate_supervisor_sign,String division_manager_sign) {

		logger.info(">>AttendanceRecordServiceImpl:saveVacationapplyExamination: "+new Date().toString());

		HrmChangeOfRestApply hrmChangeOfRestApply = this.get(HrmChangeOfRestApply.class, vacationId);
		if (hrmChangeOfRestApply != null) {
			// 上级领导审批
			if (roleType == 1) {
				hrmChangeOfRestApply.setImmediate_supervisor_examine(immediate_supervisor_examine);
				hrmChangeOfRestApply.setImmediate_supervisor_advice(immediate_supervisor_advice);
				if (immediate_supervisor_examine == 1) {
					if (hrmChangeOfRestApply.getDivision_manager_examine() != null) {
						if (hrmChangeOfRestApply.getDivision_manager_examine() == 3) {
							hrmChangeOfRestApply.setStatus(HrmChangeOfRestApply.AGREE);
						} else {
							hrmChangeOfRestApply.setStatus(HrmChangeOfRestApply.EXAMING);
						}
					}
				} else if (immediate_supervisor_examine == 2) {
					hrmChangeOfRestApply.setStatus(HrmChangeOfRestApply.DISAGREE);
				}
				hrmChangeOfRestApply.setImmediate_supervisor_sign(immediate_supervisor_sign);
			} else if (roleType == 2) {
				// 部门经理审批
				hrmChangeOfRestApply.setDivision_manager_examine(division_manager_examine);
				hrmChangeOfRestApply.setDivision_manager_advice(division_manager_advice);
				if (division_manager_examine == 1) {
					hrmChangeOfRestApply.setStatus(HrmChangeOfRestApply.AGREE);
				} else if (division_manager_examine == 2) {
					hrmChangeOfRestApply.setStatus(HrmChangeOfRestApply.DISAGREE);
				}
				hrmChangeOfRestApply.setDivision_manager_sign(division_manager_sign);
			}
			hrmChangeOfRestApply.setModify_date(new Date());
			this.saveOrUpdate(hrmChangeOfRestApply);
		}
	}

	/**
	 * 换休申请用户确认
	 *
	 * @param vacationId
	 * @return
	 */
	@Override
	public Boolean saveUserConfirmVacationApply(String vacationId) {
		HrmChangeOfRestApply hrmChangeOfRestApply = this.get(HrmChangeOfRestApply.class, vacationId);
		Boolean flag = false;
		if (hrmChangeOfRestApply != null) {
			hrmChangeOfRestApply.setUser_status(HrmChangeOfRestApply.AGREE);
			hrmChangeOfRestApply.setModify_date(new Date());
			this.saveOrUpdate(hrmChangeOfRestApply);
			flag = true;
		}
		return flag;
	}

	/**
	 * 人事换休关闭
	 *
	 * @param vacationId
	 * @return
	 */
	@Override
	public Boolean saveAdminConfirmVacationApply(String vacationId, String staff) {

		logger.info(">>AttendanceRecordServiceImpl:saveAdminConfirmVacationApply: "+new Date().toString());

		//System.out.println("vacationId---------->" + vacationId);
		HrmChangeOfRestApply hrmChangeOfRestApply = this.get(HrmChangeOfRestApply.class, vacationId);
		//请假者的用户ID 和 请假类型
		String userId = hrmChangeOfRestApply.getUser_id();
		String vacationType = hrmChangeOfRestApply.getVacation_type();
		//请假时间
		Double countday  = hrmChangeOfRestApply.getCount_day();
		//申请日期
		Date date = hrmChangeOfRestApply.getApply_date();
		SimpleDateFormat  fmt = new SimpleDateFormat("yyyy-MM-dd");
		String  applydate = fmt.format(date);
		String hql = "from TSUser u where u.id =?";
		List<TSUser> tSUser = this.findHql(hql, userId);
		TSUser user = tSUser.get(0);
		//System.out.println("------------------------------->"+user);
		Integer type = 0;
		if("年假".equals(vacationType)) {
			type = 1;
		}else if("事假".equals(vacationType)) {
			type = 2;
		}else if("病假".equals(vacationType)) {
			type = 3;
		}else if("换休".equals(vacationType)) {
			type = 4;
		}else if("婚嫁".equals(vacationType)) {
			type = 5;
		}else if("丧假".equals(vacationType)) {
			type = 6;
		}else if("出差".equals(vacationType)) {
			type = 7;
		}
		List<HrmChangofrestTime> hrmtimes = hrmChangeOfRestApply.getHrmChangofrestTimes();
		//System.out.println("----------------------------->" + hrmtimes);
		// 写入数据库 将vacationId 对应的请假时间，保存进考勤记录表
		/*
		 * for (HrmChangofrestTime htmtime : hrmtimes) { //得到时间集合 Date start =
		 * htmtime.getStart_time(); Date end = htmtime.getEnd_time(); }
		 */
		Map<String, Double> timemap = new HashMap<String,Double>();
		for (int i = 0; i < hrmtimes.size(); i++) {
			//System.out.println(i+"-------------------------------进入了---------");
			Date start = hrmtimes.get(i).getStart_time();
			Date end = hrmtimes.get(i).getEnd_time();
			//System.out.println("start----->" + start);
			//System.out.println("end------->" + end);
			try {
				// 转化下防止将数据更新了数据库中的时间
				SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startYMDString = sdfYearMonthDay.format(start);
				String endYMDString = sdfYearMonthDay.format(end);
				String startAllString = sdfAll.format(start);
				String endAllString = sdfAll.format(end);
				
				Date startYMD = sdfYearMonthDay.parse(startYMDString);
				Date endYMD = sdfYearMonthDay.parse(endYMDString);
				Date startAll = sdfAll.parse(startAllString);
				Date endAll = sdfAll.parse(endAllString);
				Date startuse = sdfAll.parse(startAllString);
				Date enduse = sdfAll.parse(endAllString);
				
				// 将开始时间和结束时间转化为yyyy-MM-dd的形式 方便判断是否为同一天
				String s = startAllString;
				String sdate = s.substring(0, s.indexOf(" "));
				String e = endAllString;
				String edate = e.substring(0, e.indexOf(" "));
				// 取出开始和结束的小时
				String startnext = s.substring(s.indexOf(" "));
				Integer startHour = Integer.parseInt(startnext.substring(0, startnext.indexOf(":")).trim());
				String endnext = e.substring(e.indexOf(" "));
				Integer endHour = Integer.parseInt(endnext.substring(0, endnext.indexOf(":")).trim());
				// 如果开始时间小于9
				if (startHour <= 9) {
					//System.out.println("-----进来了");
					// 重置开始时间 为yyyy-MM-dd 9:00:00;
					startAll.setHours(9);
					startAll.setMinutes(0);
					startAll.setSeconds(0);
				}
				// 如果结束时间大于18
				if (endHour > 18) {
					// 重置结束时间为yyyy-MM-dd 18:00:00
					endAll.setHours(18);
					endAll.setMinutes(0);
					endAll.setSeconds(0);
				}
				Integer at = 9;      
				Integer bt = 1;
				Integer ct = 6;
				Integer dt = 16;
				//总小时
				//System.out.println(startAll +"_--------_"+endAll);
				double res = (endAll.getTime() - startAll.getTime()) / 1000 / 3600;
				//System.out.println("-------------------" + res);
				// 判断 start 和 end是否为同一天 如果为同一天
				if (sdate.equals(edate)) {
					// 计算小时 (当天的小时数)
					// 去掉一个小时午饭时间
					if (startHour < 12 && endHour > 12) {
						res = res - bt;
					}
					timemap.put(sdate, res);

				} else {
					// 不是同一天 等于说跨天这种请假的情况
					int day = (int) ((endYMD.getTime() - startYMD.getTime()) / 1000 / 3600 / 24);
					if (day == 1) {
						// 相差一天
						// 算出第一天的 小时数
						Date startlast = startuse;
						startlast.setHours(18);
						startlast.setMinutes(0);
						startlast.setSeconds(0);
						double resFirst = (startlast.getTime() - startAll.getTime()) / 1000 / 3600;
						if (startHour < 12) {
							resFirst = resFirst - bt;
						}
						// 算出第二天的小时数
						Date endBegin = enduse;
						endBegin.setHours(9);
						endBegin.setMinutes(0);
						endBegin.setSeconds(0);
						double resScond = (end.getTime() - endBegin.getTime()) / 1000 / 3600;
						if (endHour > 12) {
							resScond = resScond - 1;
						}
						timemap.put(sdate, resFirst);
						timemap.put(edate, resScond);
					} else {
						// 相差多天
						// 算出第一天的 小时数
						Date startlast = startuse;
						startlast.setHours(18);
						startlast.setMinutes(0);
						startlast.setSeconds(0);
						double resFirst = (startlast.getTime() - startAll.getTime()) / 1000 / 3600;
						//System.out.println(resFirst+"-----startAll"+startAll+"-----startlast"+startlast);
						if (startHour < 12) {
							resFirst = resFirst - bt;
						}
						if(resFirst>0) {
							timemap.put(sdate, resFirst);
						}
						for(int j=1;j<=day;j++) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(startYMD);
							calendar.add(Calendar.DAY_OF_MONTH, j);
							String contiTime = sdfYearMonthDay.format(calendar.getTime());
							timemap.put(contiTime, 8.0);
						}
						// 算出最后一天的小时数
						Date endBegin = enduse;
						endBegin.setHours(9);
						endBegin.setMinutes(0);
						endBegin.setSeconds(0);
						double resScond = (endAll.getTime() - endBegin.getTime()) / 1000 / 3600;
						//System.out.println(resScond+"-----endAll"+endAll+"-----endBegin"+endBegin);
						if (endHour > 12) {
							resScond = resScond - 1;
						}
						if(resScond>0) {
							timemap.put(edate, resScond);
						}
						
					}

				}
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		//System.out.println("timemap------------->" + timemap);
		//年假、病假天数更改
		if(type == 1) {
			
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(calendar.YEAR);
			String hql1 = "from HrmAnnualLeaveStatistics where  user_id=? and year=?";
			List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = this.findHql(hql1,userId,year);
			HrmAnnualLeaveStatistics hrmals = hrmAnnualLeaveStatisticsList.get(0);
			if(hrmals!=null) {
				String id = hrmals.getId();
				//剩余
				Double presyd = hrmals.getSurplus_days();
				//已使用
				Double preused = hrmals.getUse_days();
				//使用记录
				String prechangofresttime = hrmals.getChangofrest_time();
				if(prechangofresttime==null) {
					prechangofresttime = "";
				}
				//now 剩余
				Double nowsyd = presyd - countday; 
				//now 已使用
				Double nowused = preused + countday;
				//now 使用记录
				String nowchangofresttime = prechangofresttime+"/r/n"+applydate+"申请使用了"+countday+"天年假";
				//String sql2 = "update  t_hrm_attendance_record set hour="+hour+", type="+type+" where user_id='"+userId+"' and creat_time='"+key+"'";
				String sql = "update  t_hrm_annual_leave_statistics set use_days="+nowused+", surplus_days="+nowsyd+", changofrest_time='"+nowchangofresttime+"' where id='"+id+"' and year='"+year+"'";
				this.executeSql(sql);
			}
			
		}
		//将userID 对应的 请假类型，某一天的请假小时写入考勤记录表
		Set<String> keys = timemap.keySet();
		for (String key : keys) {
			Double hour = timemap.get(key);
			//检查考勤记录表中是否有该条数据
			String []a = key.split("-");
			Integer year =Integer.parseInt(a[0]);
			Integer month = Integer.parseInt(a[1]);
			
			String hql1 = "from HrmAttendanceRecord u where u.userId=?  and YEAR(u.creatTime)=? and MONTH(u.creatTime) =?";
			List<HrmAttendanceRecord> list = this.findHql(hql1, userId, year, month);
			// 当数据库中没有数据的时候 插入数据
			if (list.size() == 0) {
				String sql = "insert into t_hrm_attendance_record(id,user_id,user_name,job_num,type,creat_time,oper_time)"
						+ "values(?,?,?,?,?,?,?)";
				List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
				for(int i=0;i<dateUtilList.size();i++) {
					this.executeSql(sql, UUID.randomUUID().toString(),userId, user.getUserName(), user.getJobNum(), 0,
							dateUtilList.get(i).getDate(), new Date());
				}
			}
			//写入数据库 更新操作
			String sql2 = "update  t_hrm_attendance_record set hour="+hour+", type="+type+" where user_id='"+userId+"' and creat_time='"+key+"'";
			this.executeSql(sql2);
			
		}

		Boolean flag = false;
		if (hrmChangeOfRestApply != null) {
			hrmChangeOfRestApply.setPersonnel_matters_status(HrmChangeOfRestApply.AGREE);
			hrmChangeOfRestApply.setPersonnel_matters_name(staff);
			hrmChangeOfRestApply.setModify_date(new Date());
			this.saveOrUpdate(hrmChangeOfRestApply);
			flag = true;
		}
		return flag;
	}

	/**
	 * 每年一月一号自动插入正式员工的该年的年假记录
	 */
	@Override
	public void insertAnnualLeaveRecordAuto() {

		logger.info(">>AttendanceRecordServiceImpl:insertAnnualLeaveRecordAuto: "+new Date().toString());

		String hql = "from TSUser where userstatus='在职' and type='正式'";
		List<TSUser> users = this.findHql(hql);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for (int i = 0; i < users.size(); i++) {
			HrmAnnualLeaveStatistics hrmAnnualLeaveStatistics = new HrmAnnualLeaveStatistics();
			// 查询该用户是否有去年的年假记录
			String hql1 = "from HrmAnnualLeaveStatistics where user_id=? and year=?";
			List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = this.findHql(hql1, users.get(i).getId(),
					year - 1);
			HrmAnnualLeaveStatistics hrmAnnualLeaveStatisticsBefore = null;
			if (hrmAnnualLeaveStatisticsList.size() > 0) {
				hrmAnnualLeaveStatisticsBefore = hrmAnnualLeaveStatisticsList.get(0);
			}
			hrmAnnualLeaveStatistics.setId(UUID.randomUUID().toString());
			hrmAnnualLeaveStatistics.setUser_name(users.get(i).getUserName());
			hrmAnnualLeaveStatistics.setUser_id(users.get(i).getId());
			if (hrmAnnualLeaveStatisticsBefore != null) {
				hrmAnnualLeaveStatistics.setPrevious_year(hrmAnnualLeaveStatisticsBefore.getThis_surplus_days());
				hrmAnnualLeaveStatistics
						.setPrevious_surplus_days(hrmAnnualLeaveStatisticsBefore.getThis_surplus_days());
			} else {
				hrmAnnualLeaveStatistics.setPrevious_year(0.0);
			}
			hrmAnnualLeaveStatistics.setThis_year(0.0);
			hrmAnnualLeaveStatistics.setCount_annual_leave(hrmAnnualLeaveStatistics.getPrevious_year());
			hrmAnnualLeaveStatistics.setUse_days(0.0);
			hrmAnnualLeaveStatistics.setSurplus_days(hrmAnnualLeaveStatistics.getPrevious_year());
			hrmAnnualLeaveStatistics.setCreate_time(new Date());
			hrmAnnualLeaveStatistics.setModify_time(new Date());
			hrmAnnualLeaveStatistics.setThis_surplus_days(0.0);
			hrmAnnualLeaveStatistics.setYear(year);
			this.save(hrmAnnualLeaveStatistics);
		}
	}

	/**
	 * 每月最后一天自动为正式员工增加一天的年假数
	 */
	@Override
	public void updateAnnualLeaveRecordAuto() {

		logger.info(">>AttendanceRecordServiceImpl:updateAnnualLeaveRecordAuto: "+new Date().toString());

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		String sql = "update t_hrm_annual_leave_statistics set count_annual_leave=count_annual_leave+1,surplus_days=surplus_days+1,this_surplus_days=this_surplus_days+1 where year=?";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, year);
		query.executeUpdate();
	}

	/**
	 * 为新增的正式工增加
	 *
	 * @param user
	 */
	@Override
	public void insertAnnunalLeaveForNewUser(TSUser user) {

		logger.info(">>AttendanceRecordServiceImpl:insertAnnunalLeaveForNewUser: "+new Date().toString());

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		String hql = "from HrmAnnualLeaveStatistics where user_id=? and year=?";
		List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatistics = this.findHql(hql, user.getId(), year);
		if (hrmAnnualLeaveStatistics.size() == 0) {
			HrmAnnualLeaveStatistics hrmAnnualLeaveStatistics1 = new HrmAnnualLeaveStatistics();
			hrmAnnualLeaveStatistics1.setId(UUID.randomUUID().toString());
			hrmAnnualLeaveStatistics1.setUser_name(user.getUserName());
			hrmAnnualLeaveStatistics1.setUser_id(user.getId());
			hrmAnnualLeaveStatistics1.setPrevious_year(0.0);
			hrmAnnualLeaveStatistics1.setThis_year(0.0);
			hrmAnnualLeaveStatistics1.setCount_annual_leave(0.0);
			hrmAnnualLeaveStatistics1.setUse_days(0.0);
			hrmAnnualLeaveStatistics1.setSurplus_days(0.0);
			hrmAnnualLeaveStatistics1.setCreate_time(new Date());
			hrmAnnualLeaveStatistics1.setModify_time(new Date());
			hrmAnnualLeaveStatistics1.setPrevious_surplus_days(0.0);
			hrmAnnualLeaveStatistics1.setThis_surplus_days(0.0);
			hrmAnnualLeaveStatistics1.setYear(year);
			this.save(hrmAnnualLeaveStatistics1);
		}
	}

	/**
	 * 查询所有员工的年假统计
	 *
	 * @return
	 */
	@Override
	public List<HrmAnnualLeaveStatistics> findAllAnnualLeaveStatistics() {


		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		/*String hql = "from HrmAnnualLeaveStatistics where  year=?";*/
		
		String hql = "select h from HrmAnnualLeaveStatistics as h,TSUser as t where h.year=? and h.user_id=t.id and t.userstatus='在职'";
		List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = this.findHql(hql,year);
		//List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = this.getList(HrmAnnualLeaveStatistics.class);
		return hrmAnnualLeaveStatisticsList;
	}

	/**
	 * 保存年假统计编辑
	 *
	 * @param idList
	 * @param beforeList
	 * @param thisYearList
	 * @param sumDayList
	 * @param surplusDayList
	 * @param remarkList
	 */
	@Override
	public String saveAnnualVacation(List<String> idList, List<Double> beforeList, List<Double> thisYearList,
			List<Double> sumDayList, List<Double> surplusDayList, List<String> remarkList) {

		logger.info(">>AttendanceRecordServiceImpl:saveAnnualVacation: "+new Date().toString());

		Calendar calendar = Calendar.getInstance();
		System.out.println(beforeList.size()+"ssssssssssssss");
		System.out.println(idList.size()+"ssssssssseeeeeeee");
		int year = calendar.get(Calendar.YEAR);
		for (int i = 0; i < idList.size(); i++) {
			String hql = "From HrmAnnualLeaveStatistics where id=? and year=?";
			List<HrmAnnualLeaveStatistics> hrmAnnualLeaveStatisticsList = this.findHql(hql, idList.get(i), year);
			if (hrmAnnualLeaveStatisticsList.size() > 0) {
				HrmAnnualLeaveStatistics hrmAnnualLeaveStatistics = hrmAnnualLeaveStatisticsList.get(0);
				Double oldPreviousYear = hrmAnnualLeaveStatistics.getPrevious_year();
				Double oldThisYear = hrmAnnualLeaveStatistics.getThis_year();
				hrmAnnualLeaveStatistics.setPrevious_year(beforeList.get(i));
				hrmAnnualLeaveStatistics.setThis_year(thisYearList.get(i));
				hrmAnnualLeaveStatistics.setCount_annual_leave(sumDayList.get(i));
				hrmAnnualLeaveStatistics.setSurplus_days(sumDayList.get(i));
				hrmAnnualLeaveStatistics.setRemark(remarkList.get(i));
				if (hrmAnnualLeaveStatistics.getPrevious_surplus_days() == null) {
					hrmAnnualLeaveStatistics
							.setPrevious_surplus_days(hrmAnnualLeaveStatistics.getPrevious_year() - oldPreviousYear);
				} else {
					hrmAnnualLeaveStatistics
							.setPrevious_surplus_days(hrmAnnualLeaveStatistics.getPrevious_surplus_days()
									+ hrmAnnualLeaveStatistics.getPrevious_year() - oldPreviousYear);
				}
				if (hrmAnnualLeaveStatistics.getThis_surplus_days() == null) {
					hrmAnnualLeaveStatistics
							.setThis_surplus_days(hrmAnnualLeaveStatistics.getThis_year() - oldThisYear);
				} else {
					hrmAnnualLeaveStatistics.setThis_surplus_days(hrmAnnualLeaveStatistics.getThis_surplus_days()
							+ hrmAnnualLeaveStatistics.getThis_year() - oldThisYear);
				}
				hrmAnnualLeaveStatistics.setModify_time(new Date());
				this.save(hrmAnnualLeaveStatistics);
			}
		}
		return "ok";
	}

	/**
	 * 每月月初第一天自动为正式员工初始化考勤记录数据
	 */
	@Override
	public void insertAttendanceRecordAuto() {

		logger.info(">>AttendanceRecordServiceImpl:insertAttendanceRecordAuto: "+new Date().toString());

		// 查询在职人员的集合
		String hql = "from TSUser u where u.userstatus=? and position!=?";
		List<TSUser> list = this.findHql(hql, "在职", "集团总裁");
		// 系统当前时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 双层循环写入数据
		for (int i = 0; i < list.size(); i++) {
			TSUser tSUser = list.get(i);
			String hql1 = "from HrmAttendanceRecord u where u.userId=?  and YEAR(u.creatTime)=? and MONTH(u.creatTime) =?";
			List<HrmAttendanceRecord> list1 = this.findHql(hql1, tSUser.getId(), year, month);
			// 当数据库中没有数据的时候 插入数据
			if (list1.size() == 0) {
				String sql = "insert into t_hrm_attendance_record(id,user_id,user_name,job_num,type,creat_time,oper_time)"
						+ "values(?,?,?,?,?,?,?)";
				List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
				for (DateUtil dateUtil : dateUtilList) {
					this.executeSql(sql, UUID.randomUUID().toString(),tSUser.getId(), tSUser.getUserName(), tSUser.getJobNum(), 0,
							dateUtil.getDate(), new Date());
				}
			}
		}
	}
	 	
	/**
	 * 考勤记录右边的状态数据查询 某年某月
	 */
	@Override
	public List<HrmAttendanceRecord> getAttendanceRecordList(int year, int month) {
		String hql = "from HrmAttendanceRecord u where  YEAR(u.creatTime)=? and MONTH(u.creatTime) =?";
		List<HrmAttendanceRecord> list = this.findHql(hql, year, month);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}
	/**
	 *   添加考勤记录(从入职当天时间添加该月考勤记录表)
	 */
	@Override
	public void insertNewEmpAttendanceRecord(TSUser user) {

		logger.info(">>AttendanceRecordServiceImpl:insertNewEmpAttendanceRecord: "+new Date().toString());

		// 系统当前时间
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		
			String hql1 = "from HrmAttendanceRecord u where u.userId=?  and YEAR(u.creatTime)=? and MONTH(u.creatTime) =?";
			List<HrmAttendanceRecord> list = this.findHql(hql1, user.getId(), year, month);
			// 当数据库中没有数据的时候 插入数据
			if (list.size() == 0) {
				String sql = "insert into t_hrm_attendance_record(id,user_id,user_name,job_num,type,creat_time,oper_time)"
						+ "values(?,?,?,?,?,?,?)";
				List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
				for(int i=day-1;i<dateUtilList.size();i++) {
					this.executeSql(sql, UUID.randomUUID().toString(),user.getId(), user.getUserName(), user.getJobNum(), 0,
							dateUtilList.get(i).getDate(), new Date());
				}
			}
	}
	
	
	/**
	 * 出差显示申请出差的个人信息
	 */
	 @Override
	    public TSUser getUserById(String id) {
	        String hql = "from TSUser u where u.id = ?";
	        List<TSUser> list = this.findHql(hql, id);
	        if (list.size() > 0) {
	            return list.get(0);
	        }
	        return null;
	    }

	@Override
	public void saveBusinessApply(MultipartFile file, HttpServletRequest request,TSUser user, HrmBusinessApply hrmBusinessApply, Date sdate,
			Date edate) {

		logger.info(">>AttendanceRecordServiceImpl:saveBusinessApply: "+new Date().toString());

		//文件操作 文件保存
    	String path ="upload/";
    	String fileName = file.getOriginalFilename();
		String realPath = request.getSession().getServletContext().getRealPath("/") + path ;// 文件的硬盘真实路径
//		System.out.println("upload->文件的硬盘真实路径"+realPath);
		String savePath = realPath + fileName;// 文件保存全路径
//		System.out.println("upload->文件保存全路径"+savePath);
		try {
		FileCopyUtils.copy(file.getBytes(),new File(savePath));
		System.out.println("文件上传成功");
		} catch (IOException e) {
			System.out.println("文件保存失败");
			e.printStackTrace();
		}
		hrmBusinessApply.setId(UUID.randomUUID().toString());
		hrmBusinessApply.setUserId(user.getId());
    	hrmBusinessApply.setLeaveStart(sdate);
    	hrmBusinessApply.setLeaveEnd(edate);
    	hrmBusinessApply.setFileAddress(savePath);
    	hrmBusinessApply.setFileName(fileName);
    	hrmBusinessApply.setPersonnelMattersStatus(0);
    	hrmBusinessApply.setUserStatus(0);
    	hrmBusinessApply.setCreateTime(new Timestamp(System.currentTimeMillis()));
		this.save(hrmBusinessApply);
	}
	/**
	 * 获取出差列表
	 */
	@Override
	public List<HrmBusinessApply> findBusinessApplyList(TSUser tsUser, Integer roleType, Integer status, String applyName,
			Date startDate, Date endDate) {


		String user_name = "";
		try {
			if (applyName != null && applyName.trim().length() > 0) {
				user_name = new String(applyName.getBytes("iso-8859-1"), "utf-8");
				System.out.println(user_name);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<HrmBusinessApply> hrmBusinessApplyList = new ArrayList<HrmBusinessApply>();
		if (roleType == 1 ||roleType == 2 ) {
			// 部门负责人
				String hql3 = "from HrmBusinessApply where dept=?";
				List<Object> params2 = new ArrayList<Object>();
				params2.add(tsUser.getDepartment());
				if (status != null && status >= 0) {
					hql3 = hql3 + " and personnelMattersStatus=?";
					params2.add(status);
				}
				if (startDate != null) {
					hql3 = hql3 + " and applyDate>=?";
					params2.add(startDate);
				}
				if (endDate != null) {
					hql3 = hql3 + " and applyDate<=?";
					params2.add(endDate);
				}
				if (user_name != null && user_name.trim().length() > 0) {
					hql3 = hql3 + " and userName=?";
					params2.add(user_name);
				}
				hql3 = hql3 +"order by createTime desc";
				hrmBusinessApplyList = this.findHql(hql3, params2.toArray());
				
		} else if (roleType == 3) {
			// 行政
			String hql = "from HrmBusinessApply where (user_status=? or user_id=?)";
			List<Object> params = new ArrayList<Object>();
			params.add(HrmBusinessApply.AGREE);
			params.add(tsUser.getId());
			if (startDate != null) {
				hql = hql + " and applyDate>=?";
				params.add(startDate);
			}
			if (endDate != null) {
				hql = hql + " and applyDate<=?";
				params.add(endDate);
			}
			if (user_name != null && user_name.trim().length() > 0) {
				hql = hql + " and userName=?";
				params.add(user_name);
			}
			hql = hql + "order by personnelMattersStatus asc,createTime desc";
			hrmBusinessApplyList = this.findHql(hql, params.toArray());
		} else if (roleType == 0) {
			// 普通员工
			String hql2 = "from HrmBusinessApply where user_id=?";
			List<Object> params3 = new ArrayList<Object>();
			params3.add(tsUser.getId());
			if (status != null && status >= 0) {
				hql2 = hql2 + " and personnelMattersStatus=?";
				params3.add(status);
			}
			if (startDate != null) {
				hql2 = hql2 + " and applyDate>=?";
				params3.add(startDate);
			}
			if (endDate != null) {
				hql2 = hql2 + " and applyDate<=?";
				params3.add(endDate);
			}
			hql2 = hql2 + "order by createTime desc";
			hrmBusinessApplyList = this.findHql(hql2, params3.toArray());
		}
		return hrmBusinessApplyList;
	}
	/**
	 * 查询出差详细信息 by id
	 */
	@Override
	public HrmBusinessApply findHrmBusinessById(String businessId) {
		String hql = "from HrmBusinessApply where id=?";
		List<HrmBusinessApply> hrmBusinessApply = this.findHql(hql, businessId);
		if(hrmBusinessApply!=null) {
			System.out.println(hrmBusinessApply.get(0));
			return hrmBusinessApply.get(0);
		}
		return null;
	}

	@Override
	public Boolean saveUserConfirmBusinessApply(String businessId) {
		HrmBusinessApply hrmBusinessApply = this.get(HrmBusinessApply.class, businessId);
			Boolean flag = false;
			if (hrmBusinessApply != null) {
				hrmBusinessApply.setUserStatus(HrmBusinessApply.AGREE);
				hrmBusinessApply.setOwnconfireTime(new Date());
				this.saveOrUpdate(hrmBusinessApply);
				flag = true;
			}
			return flag;
		}
	/**
	 * 出差人事关闭
	 */
	@Override
	public Boolean saveAdminConfirmBusinessApply(String businessId, String userName) {
			//System.out.println("vacationId---------->" + vacationId);
		logger.info(">>AttendanceRecordServiceImpl:saveAdminConfirmBusinessApply: "+new Date().toString());

		HrmBusinessApply hrmBusinessApply = this.get(HrmBusinessApply.class, businessId);
			//请假者的用户ID 和 请假类型
			String userId = hrmBusinessApply.getUserId();
			String vacationType = hrmBusinessApply.getVacationType();
			String hql = "from TSUser u where u.id =?";
			List<TSUser> tSUser = this.findHql(hql, userId);
			TSUser user = tSUser.get(0);
			//System.out.println("------------------------------->"+user);
			Integer type = 0;
			if("出差".equals(vacationType)) {
				type = 7;
			}
			Map<String, Double> timemap = new HashMap<String,Double>();
				//System.out.println(i+"-------------------------------进入了---------");
				Date start = hrmBusinessApply.getLeaveStart();
				Date end =hrmBusinessApply.getLeaveEnd();
				//System.out.println("start----->" + start);
				//System.out.println("end------->" + end);
				try {
					// 转化下防止将数据更新了数据库中的时间
					SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String startYMDString = sdfYearMonthDay.format(start);
					String endYMDString = sdfYearMonthDay.format(end);
					String startAllString = sdfAll.format(start);
					String endAllString = sdfAll.format(end);
					
					Date startYMD = sdfYearMonthDay.parse(startYMDString);
					Date endYMD = sdfYearMonthDay.parse(endYMDString);
					Date startAll = sdfAll.parse(startAllString);
					Date endAll = sdfAll.parse(endAllString);
					Date startuse = sdfAll.parse(startAllString);
					Date enduse = sdfAll.parse(endAllString);
					
					// 将开始时间和结束时间转化为yyyy-MM-dd的形式 方便判断是否为同一天
					String s = startAllString;
					String sdate = s.substring(0, s.indexOf(" "));
					String e = endAllString;
					String edate = e.substring(0, e.indexOf(" "));
					// 取出开始和结束的小时
					String startnext = s.substring(s.indexOf(" "));
					Integer startHour = Integer.parseInt(startnext.substring(0, startnext.indexOf(":")).trim());
					String endnext = e.substring(e.indexOf(" "));
					Integer endHour = Integer.parseInt(endnext.substring(0, endnext.indexOf(":")).trim());
					// 如果开始时间小于9
					if (startHour <= 9) {
						//System.out.println("-----进来了");
						// 重置开始时间 为yyyy-MM-dd 9:00:00;
						startAll.setHours(9);
						startAll.setMinutes(0);
						startAll.setSeconds(0);
					}
					// 如果结束时间大于18
					if (endHour > 18) {
						// 重置结束时间为yyyy-MM-dd 18:00:00
						endAll.setHours(18);
						endAll.setMinutes(0);
						endAll.setSeconds(0);
					}
					Integer at = 9;      
					Integer bt = 1;
					Integer ct = 6;
					Integer dt = 16;
					//总小时
					//System.out.println(startAll +"_--------_"+endAll);
					double res = (endAll.getTime() - startAll.getTime()) / 1000 / 3600;
					//System.out.println("-------------------" + res);
					// 判断 start 和 end是否为同一天 如果为同一天
					if (sdate.equals(edate)) {
						// 计算小时 (当天的小时数)
						// 去掉一个小时午饭时间
						if (startHour < 12 && endHour > 12) {
							res = res - bt;
						}
						timemap.put(sdate, res);

					} else {
						// 不是同一天 等于说跨天这种请假的情况
						int day = (int) ((endYMD.getTime() - startYMD.getTime()) / 1000 / 3600 / 24);
						if (day == 1) {
							// 相差一天
							// 算出第一天的 小时数
							Date startlast = startuse;
							startlast.setHours(18);
							startlast.setMinutes(0);
							startlast.setSeconds(0);
							double resFirst = (startlast.getTime() - startAll.getTime()) / 1000 / 3600;
							if (startHour < 12) {
								resFirst = resFirst - bt;
							}
							// 算出第二天的小时数
							Date endBegin = enduse;
							endBegin.setHours(9);
							endBegin.setMinutes(0);
							endBegin.setSeconds(0);
							double resScond = (end.getTime() - endBegin.getTime()) / 1000 / 3600;
							if (endHour > 12) {
								resScond = resScond - 1;
							}
							timemap.put(sdate, resFirst);
							timemap.put(edate, resScond);
						} else {
							// 相差多天
							// 算出第一天的 小时数
							Date startlast = startuse;
							startlast.setHours(18);
							startlast.setMinutes(0);
							startlast.setSeconds(0);
							double resFirst = (startlast.getTime() - startAll.getTime()) / 1000 / 3600;
							//System.out.println(resFirst+"-----startAll"+startAll+"-----startlast"+startlast);
							if (startHour < 12) {
								resFirst = resFirst - bt;
							}
							if(resFirst>0) {
								timemap.put(sdate, resFirst);
							}
							for(int j=1;j<=day;j++) {
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(startYMD);
								calendar.add(Calendar.DAY_OF_MONTH, j);
								String contiTime = sdfYearMonthDay.format(calendar.getTime());
								timemap.put(contiTime, 8.0);
							}
							// 算出最后一天的小时数
							Date endBegin = enduse;
							endBegin.setHours(9);
							endBegin.setMinutes(0);
							endBegin.setSeconds(0);
							double resScond = (endAll.getTime() - endBegin.getTime()) / 1000 / 3600;
							//System.out.println(resScond+"-----endAll"+endAll+"-----endBegin"+endBegin);
							if (endHour > 12) {
								resScond = resScond - 1;
							}
							if(resScond>0) {
								timemap.put(edate, resScond);
							}
						}
					}
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			//System.out.println("timemap-------------business--------------->" + timemap);
			//将userID 对应的 请假类型，某一天的请假小时写入考勤记录表
			Set<String> keys = timemap.keySet();
			for (String key : keys) {
				Double hour = timemap.get(key);
				//检查考勤记录表中是否有该条数据
				String []a = key.split("-");
				Integer year =Integer.parseInt(a[0]);
				Integer month = Integer.parseInt(a[1]);
				
				String hql1 = "from HrmAttendanceRecord u where u.userId=?  and YEAR(u.creatTime)=? and MONTH(u.creatTime) =?";
				List<HrmAttendanceRecord> list = this.findHql(hql1, userId, year, month);
				// 当数据库中没有数据的时候 插入数据
				if (list.size() == 0) {
					String sql = "insert into t_hrm_attendance_record(id,user_id,user_name,job_num,type,creat_time,oper_time)"
							+ "values(?,?,?,?,?,?,?)";
					List<DateUtil> dateUtilList = new DateUtil().getDate(year, month);
					for(int i=0;i<dateUtilList.size();i++) {
						this.executeSql(sql, UUID.randomUUID().toString(),userId, user.getUserName(), user.getJobNum(), 0,
								dateUtilList.get(i).getDate(), new Date());
					}
				}
				//写入数据库 更新操作
				String sql2 = "update  t_hrm_attendance_record set hour="+hour+", type="+type+" where user_id='"+userId+"' and creat_time='"+key+"'";
				this.executeSql(sql2);
				
			}
			Boolean flag = false;
			if (hrmBusinessApply != null) {
				hrmBusinessApply.setPersonnelMattersStatus(HrmChangeOfRestApply.AGREE);
				hrmBusinessApply.setPersonnelMattersName(userName);
				hrmBusinessApply.setHrconfireTime(new Date());
				this.saveOrUpdate(hrmBusinessApply);
				flag = true;
			}
			return flag;
		}
	
}