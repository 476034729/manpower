package org.jeecgframework.web.hrm.controller;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.HrmLoopPlayConfig;
import org.jeecgframework.web.hrm.controller.vo.request.LoopPlayConfigUpdateVO;
import org.jeecgframework.web.hrm.controller.vo.response.LoopPlayConfigVO;
import org.jeecgframework.web.hrm.bean.dto.LoopPlayConfigDTO;
import org.jeecgframework.web.hrm.service.HrmLoopPlayService;
import org.jeecgframework.web.hrm.utils.BeanMapper;
import org.jeecgframework.web.hrm.utils.UserUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Wang Yu
 * 轮播配置控制
 */
@Controller
@RequestMapping("/loopPlay")
public class HrmLoopPlayController {
    private final HrmLoopPlayService loopPlayService;

    @Autowired
    public HrmLoopPlayController(HrmLoopPlayService loopPlayService) {
        this.loopPlayService = loopPlayService;
    }

    /**
     * 查询轮播配置规则
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson list(){
        List<LoopPlayConfigDTO> dtoList = loopPlayService.listInOrder();
        List<LoopPlayConfigVO> voList = BeanMapper.mapList(dtoList, LoopPlayConfigDTO.class, LoopPlayConfigVO.class);
        return AjaxJson.success(voList);
    }

    /**
     * 更新
     * @param voList
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(@RequestBody @Validated List<LinkedHashMap<String, String>> map, HttpServletRequest request){
    	List<LoopPlayConfigUpdateVO> voList = new ArrayList<LoopPlayConfigUpdateVO>();
    	for (LinkedHashMap<String, String> loop : map) {
    		LoopPlayConfigUpdateVO vo = new LoopPlayConfigUpdateVO();
    		if(StringUtils.isEmpty(loop.get("imgId"))){
    			return AjaxJson.error("imgId 不能为空");
    		}
    		vo.setDescription(loop.get("description"));
    		vo.setImgId(loop.get("imgId"));
    		vo.setTitle(loop.get("title"));
    		voList.add(vo);
		}
        List<HrmLoopPlayConfig> beanList = BeanMapper.mapList(voList, LoopPlayConfigUpdateVO.class, HrmLoopPlayConfig.class);
        TSUser currentUser = UserUtils.getCurrentUser(request);
        if (null == currentUser){
            return AjaxJson.error("登录身份已过期，请重新登录");
        }
        Boolean saveConfig = loopPlayService.saveConfig(beanList, currentUser);
        return AjaxJson.success(saveConfig);
    }

}
 