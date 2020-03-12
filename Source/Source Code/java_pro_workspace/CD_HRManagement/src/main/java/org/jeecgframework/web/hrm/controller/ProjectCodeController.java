package org.jeecgframework.web.hrm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.hrm.service.ProjectCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ProjectCodeController")
public class ProjectCodeController {

	@Autowired
	private ProjectCodeService projectCodeService;

	@RequestMapping(params = "goprojectlist")
	public ModelAndView goProjectList() {
		return new ModelAndView("hrm/workhour/projectlist");
	}

	@RequestMapping(params = "getprojectlist")
	@ResponseBody
	public List<ProjectCode> getProject() {
		String hql = "from ProjectCode u where 1=1 and u.status !='关闭' and u.status !='' order by CONVERT(u.rank,SIGNED) asc";
		return projectCodeService.findHql(hql);
	}

	@RequestMapping(params = "queryproject")
	@ResponseBody
	public AjaxJson queryProject(String name, String status) {

		try {
			name = URLDecoder.decode(name, "UTF-8");
			status = URLDecoder.decode(status, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		AjaxJson a = new AjaxJson();

		String order = "and u.enable = 0 order by CONVERT(u.rank,SIGNED) asc";
		String hql = "from ProjectCode u where 1=1 ";

		if (name != null && !"null".equals(name)) {
			hql += "and u.project_name like '%" + name + "%'";
		}

		if (!"all".equals(status)) {
			hql += "and u.status = '" + status + "'";
		}
		hql = hql + order;

		List<ProjectCode> list = projectCodeService.findHql(hql);
		if (list.size() > 0) {
			a.setObj(list);
		} else {
			a.setSuccess(false);
			a.setMsg("没有数据!");
		}

		return a;
	}

	// 跳转新增项目页面
	@RequestMapping(params = "doJumpAddProject")
	public ModelAndView doJumpAddProject(String parentId) {
		ModelAndView modelAndView = new ModelAndView("hrm/workhour/addproject");
		modelAndView.addObject("parentId", parentId);
		return modelAndView;
	}

	// 跳转修改项目页面
	@RequestMapping(params = "doJumpUpdateProject")
	public ModelAndView doJumpUpdateProject(String parentId) {
		String hql = "from ProjectCode  where id=? and enable=0";
		List<ProjectCode> projectCodeList = this.projectCodeService.findHql(hql, parentId);
		ProjectCode projectCodes = projectCodeList.get(0);
		ModelAndView modelAndView = new ModelAndView("hrm/workhour/updateproject");
		modelAndView.addObject("projectCodes", projectCodes);
		return modelAndView;
	}

	// 新增项目
	@RequestMapping(params = "doAddProject",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson doAddProject(ProjectCode projectCode, String parentId) {
		AjaxJson json = new AjaxJson();
		projectCode.setId(UUIDGenerator.generate());
		projectCode.setEnable(0);
		projectCode.setHas_son(0);
		String sql = "select MAX(cast(rank as SIGNED INTEGER))+1 as rank from t_hrm_project_code;";
		projectCode.setRank(this.projectCodeService.findOneForJdbc(sql, null).get("rank").toString());
		String overSql = "select count(1) from t_hrm_project_code  where  enable =0 and `code` ='"
				+ projectCode.getCode() + "'";
		long count = this.projectCodeService.getCountForJdbc(overSql);
		if (count > 0) {
			json.setSuccess(Boolean.FALSE);
			json.setMsg("该项目代号已存在!");
			return json;
		}
		// 判断关联关系id是否存在
		if (null == parentId || "" == parentId) {
			// 不存在就说明新增的是主项目默认值设置为code
			String code = projectCode.getCode();
			projectCode.setCode_contact(code);
			boolean x = StringUtils.contains(code, "@");
			if (x) {
				json.setSuccess(Boolean.FALSE);
				json.setMsg("主项目code不能包含@符号!");
				return json;
			}
		} else {
			// 存在就说明新增的是子项目
			// 修改主项目是否有子项目字段
			String hql = "from ProjectCode  where id=? and enable=0";
			List<ProjectCode> projectCodeList = this.projectCodeService.findHql(hql, parentId);
			if (projectCodeList.size() == 0) {
				json.setSuccess(Boolean.FALSE);
				json.setMsg("该项目不存在或者该项目不可用!");
				return json;
			} else {
				// 主项目有子项目
				ProjectCode projectCodes = projectCodeList.get(0);
				projectCodes.setHas_son(1);
				this.projectCodeService.updateEntitie(projectCodes);
				// 查询主项目下的子项目有几个 编号就是几
				String countsql = "SELECT count(1) from  t_hrm_project_code where code_contact like '"
						+ projectCodes.getCode_contact() + "%' and enable=0 ";
				long counts = this.projectCodeService.getCountForJdbc(countsql);
				if (counts > 9) {
					json.setSuccess(Boolean.FALSE);
					json.setMsg("最多9个子项目");
					return json;
				}
				projectCode.setCode_contact(projectCodes.getCode_contact() + ("@0" + counts));
			}
		}
		this.projectCodeService.save(projectCode);
		return json;
		/* return "redirect:ProjectCodeController.do?goprojectlist"; */

	}

	// 修改项目
	@RequestMapping(params = "doUpdateProject",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson doUpdateProject(ProjectCode projectCode) {
		AjaxJson json = new AjaxJson();
		if (projectCode.getHas_son() == 1) {
			boolean x = StringUtils.contains(projectCode.getCode(), "@");
			if (x) {
				json.setSuccess(Boolean.FALSE);
				json.setMsg("主项目code不能包含@符号!");
				return json;
			}
		}
		String overSql = "select count(1) from t_hrm_project_code  where  enable =0 and `code` ='"
				+ projectCode.getCode() + "' and id !='" + projectCode.getId() + "'";
		long count = this.projectCodeService.getCountForJdbc(overSql);
		if (count > 0) {
			json.setSuccess(Boolean.FALSE);
			json.setMsg("该项目代号已存在!");
			return json;
		}

		this.projectCodeService.updateEntitie(projectCode);
		return json;
		/* return "redirect:ProjectCodeController.do?goprojectlist"; */
	}

	// 通过主键逻辑删除项目
	@RequestMapping(params = "doDeleteProjectCodeById")
	@ResponseBody
	public AjaxJson doDeleteProjectCodeById(String id) {
		AjaxJson json = new AjaxJson();
		if (StringUtils.isEmpty(id)) {
			json.setSuccess(Boolean.FALSE);
			json.setMsg("主键id不存在!");
			return json;
		}
		String hql = "from ProjectCode  where id=? and enable=0";
		List<ProjectCode> projectCodeList = this.projectCodeService.findHql(hql, id);
		if (projectCodeList.size() == 0) {
			json.setSuccess(Boolean.FALSE);
			json.setMsg("该项目不存在或者该项目不可用!");
			return json;
		}
		// 判断该项目是否有子项目 如果有子项目把这个项目的和它所有子项目的状态也要改成不可用状态
		ProjectCode projectCodes = projectCodeList.get(0);
		String codeContact = projectCodes.getCode_contact();
		if (projectCodes.getHas_son() == 1) {
			// 查询它的子项目是否在进行中
			String sql = "SELECT count(1) from  t_hrm_project_code where code_contact like '" + codeContact
					+ "%' and enable=0 and  status!='关闭'  ";
			long count = this.projectCodeService.getCountForJdbc(sql);
			if (count > 1) {
				json.setSuccess(Boolean.FALSE);
				json.setMsg("该项目的子项目正在使用不能删除!");
				return json;
			} else {
				// 说明该子项目没有在运行中的 如果没有运行中的 判断该子项目是不是该主项目的最后一个子项目
				doDeleteSonProject(codeContact);
			}
		} else {
			// 没有子项目也说明可能是最后一个项目 也要判定是否是最后一个子项目
			doDeleteSonProject(codeContact);
		}
		projectCodes.setEnable(1);
		this.projectCodeService.updateEntitie(projectCodes);
		return json;
	}

	// 根据主项目和子项目的关联关系查询到它的所有子项目
	@RequestMapping(params = "getProjectListByCodeContact")
	@ResponseBody
	public List<Map<String, Object>> getProjectListByCodeContact(String codeContact) {
		String sql = "SELECT codes.id,codes.project_name,codes.project_eg_code,codes.`code`,codes.type,codes.`status`,codes.remark,codes.rank,codes.`enable`,codes.has_son,codes.code_contact"
				+ " FROM t_hrm_project_code codes where codes.enable=0 ";
		String limitSql = "";
		if (codeContact != null && !"null".equals(codeContact)) {
			sql += " and codes.code_contact like '" + codeContact + "%' ";
			limitSql += " limit 1,10";
		}
		sql += "   order by CONVERT(codes.rank,SIGNED) asc" + limitSql;
		List<Map<String, Object>> list = this.projectCodeService.findForJdbc(sql);

		return list;
	}

	/**
	 * //没有子项目也说明可能是最后一个项目 也要判定是否是最后一个子项目
	 * 
	 * @param codeContact选中的项目代号
	 */
	private void doDeleteSonProject(String codeContact) {
		//获取主项目的项目代号
		String mainCodeContact = StringUtils.substring(codeContact, 0, codeContact.indexOf("@"));
		//查询当前项目的主项目的子项目是否都关闭 
		String mainsql = "SELECT count(1) from  t_hrm_project_code where code_contact like '" + mainCodeContact
				+ "%' and enable=0 and  status!='关闭'  ";
		long counts = this.projectCodeService.getCountForJdbc(mainsql);
		//如果子项目都关闭的话需要把主项目的是否有子项目的字段设置为0没有
		if (counts == 2) {
			this.projectCodeService.updateBySqlString(
					"update  t_hrm_project_code set has_son=0 where code_contact ='" + mainCodeContact + "'");
		}
	}

}
