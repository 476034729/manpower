package org.jeecgframework.web.hrm.controller;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.HrmImageResource;
import org.jeecgframework.web.hrm.controller.vo.request.ImageResourceIdVO;
import org.jeecgframework.web.hrm.controller.vo.response.ImageResourceVO;
import org.jeecgframework.web.hrm.service.impl.HrmImageResourceService;
import org.jeecgframework.web.hrm.utils.BeanMapper;
import org.jeecgframework.web.hrm.utils.FileUtils;
import org.jeecgframework.web.hrm.utils.UserUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Wang Yu
 * 图片资源控制
 */
@Controller
@RequestMapping("/image")
public class HrmImageResourceController {

    private final HrmImageResourceService imageResourceService;

    public static final String SEPARATOR = File.separator;

    @Autowired
    public HrmImageResourceController(HrmImageResourceService imageResourceService) {
        this.imageResourceService = imageResourceService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson list(){
        List<HrmImageResource> hrmImageResources = imageResourceService.loadAll(HrmImageResource.class);
        return AjaxJson.success(hrmImageResources);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson upload(@RequestParam("image") CommonsMultipartFile file, HttpServletRequest request){
        File desFile = FileUtils.getUploadFile(file.getOriginalFilename(), request);
        try {
            // 先传输图片，如果传输失败，则不更新数据库
            file.transferTo(desFile);
            TSUser user = UserUtils.getCurrentUser(request);
            if (null == user){ // 如果user为空，则提示身份过期
                return AjaxJson.error("登录身份已过期，请重新登录");
            }
            HrmImageResource image = new HrmImageResource();
            image.setId(null);
            String url= "http:/"+FileUtils.getCurrentIp()+":"+request.getServerPort()+SEPARATOR + "update" + SEPARATOR + "image"+ SEPARATOR +desFile.getName();
            image.setUrl(url);
            HrmImageResource imageResource = imageResourceService.saveImage(image, user);
            ImageResourceVO vo = BeanMapper.map(imageResource, ImageResourceVO.class);
            return AjaxJson.success(vo);

        } catch (IOException e) {
            e.printStackTrace();
            return AjaxJson.error("文件传输失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson delete(@RequestBody @Validated ImageResourceIdVO idVO, HttpServletRequest request){
        HrmImageResource image = BeanMapper.map(idVO, HrmImageResource.class);
        TSUser currentUser = UserUtils.getCurrentUser(request);
      /*  if (currentUser == null){
            return AjaxJson.error("登录身份已过期，请重新登录");
        }*/
        imageResourceService.deleteImage(image, currentUser);
        return AjaxJson.success(Boolean.TRUE);
    }


}
