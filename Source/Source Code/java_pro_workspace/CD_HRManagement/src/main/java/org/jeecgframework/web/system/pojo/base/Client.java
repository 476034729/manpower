package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.web.hrm.bean.HrmMenu;
import org.jeecgframework.web.hrm.bean.HrmUser;

import java.util.List;
import java.util.Map;

/**
 * 在线用户对象
 * 
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private TSUser user;

	private HrmUser hrmUser;

	private Map<String, TSFunction> functions;
	private Map<Integer, List<TSFunction>> functionMap;

	//hrm
	private Map<String , HrmMenu> hrmFunctions;
	private Map<Integer ,List<HrmMenu>> hrmFunctionMap;


	public Map<Integer, List<TSFunction>> getFunctionMap() {
		return functionMap;
	}

	public void setFunctionMap(Map<Integer, List<TSFunction>> functionMap) {
		this.functionMap = functionMap;
	}

	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	/**
	 *登录时间
	 */
	private java.util.Date logindatetime;

	public TSUser getUser() {
		return user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}


	public Map<String, TSFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, TSFunction> functions) {
		this.functions = functions;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}


	public HrmUser getHrmUser() {
		return hrmUser;
	}

	public void setHrmUser(HrmUser hrmUser) {
		this.hrmUser = hrmUser;
	}

	public Map<String, HrmMenu> getHrmFunctions() {
		return hrmFunctions;
	}

	public void setHrmFunctions(Map<String, HrmMenu> hrmFunctions) {
		this.hrmFunctions = hrmFunctions;
	}

	public Map<Integer, List<HrmMenu>> getHrmFunctionMap() {
		return hrmFunctionMap;
	}

	public void setHrmFunctionMap(Map<Integer, List<HrmMenu>> hrmFunctionMap) {
		this.hrmFunctionMap = hrmFunctionMap;
	}
}
