package org.jeecgframework.web.hrm.controller;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.HrmAnnouncement;
import org.jeecgframework.web.hrm.controller.vo.request.AnnouncementIdVO;
import org.jeecgframework.web.hrm.controller.vo.request.AnnouncementSaveVO;
import org.jeecgframework.web.hrm.controller.vo.request.AnnouncementUpdateVO;
import org.jeecgframework.web.hrm.controller.vo.response.AnnouncementVO;
import org.jeecgframework.web.hrm.service.HrmAnnouncementService;
import org.jeecgframework.web.hrm.utils.BeanMapper;
import org.jeecgframework.web.hrm.utils.UserUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.aspectj.AspectJAsyncConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Wang Yu
 * 公告管理
 */
@Controller
@RequestMapping("/announcement")
public class HrmAnnouncementController {

    private final HrmAnnouncementService announcementService;

    @Autowired
    public HrmAnnouncementController(HrmAnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson list(){
        // 只返回最新的十条
        List<HrmAnnouncement> announcementList = announcementService.listLimit(0, 10);
        List<AnnouncementVO> voList = BeanMapper.mapList(announcementList, HrmAnnouncement.class, AnnouncementVO.class);
        return AjaxJson.success(voList);
    }

    @RequestMapping(value = "/id", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getById(@RequestBody @Validated AnnouncementIdVO vo){
        HrmAnnouncement announcement = announcementService.get(HrmAnnouncement.class, vo.getId());
        AnnouncementVO announcementVO = BeanMapper.map(announcement, AnnouncementVO.class);
        return AjaxJson.success(announcementVO);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(@RequestBody @Validated AnnouncementSaveVO saveVO, HttpServletRequest request){
        HrmAnnouncement announcement = BeanMapper.map(saveVO, HrmAnnouncement.class);
        TSUser user = UserUtils.getCurrentUser(request);
        if (null == user){
            return AjaxJson.error("登录身份已过期，请重新登录");

        }
        announcementService.save(announcement, user);
        return AjaxJson.success(Boolean.TRUE);
    }

    /**
     * 更新公告
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(@RequestBody @Validated AnnouncementUpdateVO updateVO, HttpServletRequest request){
        HrmAnnouncement announcement = BeanMapper.map(updateVO, HrmAnnouncement.class);
        TSUser user = UserUtils.getCurrentUser(request);
        if (null == user){
            return AjaxJson.error("登录身份已过期，请重新登录");
        }
        Boolean update = announcementService.update(announcement, user);
        return AjaxJson.success(update);
    }

    /**
     * 删除公告
     * @param idVO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson delete(@RequestBody @Validated AnnouncementIdVO idVO){
        announcementService.deleteEntityById(HrmAnnouncement.class, idVO.getId());
        return AjaxJson.success(Boolean.TRUE);
    }

}
