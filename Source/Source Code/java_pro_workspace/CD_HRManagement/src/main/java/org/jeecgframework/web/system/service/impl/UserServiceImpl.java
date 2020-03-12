package org.jeecgframework.web.system.service.impl;

import org.hibernate.Query;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}


	//从前台传过来的username即是工号
	@Override
	public TSUser checkUserExitsInHrm(TSUser tsUser) {
		String password = PasswordUtil.encrypt(tsUser.getUserName(), tsUser.getPassword(), PasswordUtil.getStaticSalt());
		String query = "from TSUser u where u.jobNum = :username and u.password=:passowrd and u.userstatus = '在职'";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", tsUser.getUserName());
		queryObject.setParameter("passowrd", password);
		List<TSUser> users = queryObject.list();

		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			queryObject = getSession().createQuery(query);
			queryObject.setParameter("username", tsUser.getUserName());
			queryObject.setParameter("passowrd", tsUser.getPassword());
			users = queryObject.list();
			if(users != null && users.size() > 0){
				return users.get(0);
			}
		}

		return null;
	}


	//删除用户，包括用户关联中的所以信息
	@Override
	public int delUserInfo(TSUser user) {

		String workHourSql = "delete from t_hrm_workhour where user_id = ?";
		String roleUserSql = "delete from t_s_role_user where userid = ?";
		String userOrgSql = "delete from t_s_user_org where user_id = ?";
		try {
			this.executeSql(workHourSql,user.getId());
			this.executeSql(roleUserSql,user.getId());
			this.executeSql(userOrgSql,user.getId());
		}catch (Exception e){
			return 0;
		}
		return 1;
	}

}
