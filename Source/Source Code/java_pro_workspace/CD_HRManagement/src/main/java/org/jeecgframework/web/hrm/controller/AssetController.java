package org.jeecgframework.web.hrm.controller;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.AssetService;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/AssetController")
public class AssetController {
    @Autowired
    private AssetService assetService;

    /**
     * 资产录入跳转页面
     * @param session
     * @return
     */
    @RequestMapping(params="assetEntry")
    public ModelAndView assetEntry(HttpSession session){
        List<HrmAssetType> hrmAssetTypeList = assetService.getAssetTypeAll();
        List<HrmBrand> hrmBrandList = assetService.getBrandAll();
        List<HrmDepart> hrmDepartList = assetService.getDepartAll();
        //获取当前登陆用户信息
        Client client = ClientManager.getInstance().getClient(session.getId());
        TSUser user =client.getUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hrm/asset/assetEntry");
        modelAndView.addObject("hrmAssetTypeList",hrmAssetTypeList);
        modelAndView.addObject("hrmBrandList",hrmBrandList);
        modelAndView.addObject("hrmDepartList",hrmDepartList);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    /**
     * 资产录入保存
     * @param hrmAssetInventory
     * @param hrmAssetTypeId
     * @param hrmBrandId
     * @param hrmDepartId
     * @param entryPersonId
     * @return
     */
    @RequestMapping(params = "saveAsset")
    public ModelAndView saveAsset(HrmAssetInventory hrmAssetInventory,Integer  hrmAssetTypeId,Integer hrmBrandId,Integer hrmDepartId,String entryPersonId){
        assetService.saveAsset(hrmAssetInventory,hrmAssetTypeId,hrmBrandId,hrmDepartId,entryPersonId);
        return new ModelAndView("hrm/asset/assetList");
    }

    /**
     * 判断资产编号是否重复
     * @param asset_no
     * @return
     */
    @RequestMapping(params = "assetNoIsRepeat",method = RequestMethod.GET)
    @ResponseBody
    public AjaxJson assetNoIsRepeat(String asset_no){
        AjaxJson json = new AjaxJson();
        Boolean flag = false;
        if(asset_no!=null){
             flag = assetService.findAssetNoRepeat(asset_no);
        }
        if(flag){
            json.setMsg("ok");
        }else{
            json.setMsg("fail");
        }
        return json;
    }

    /**
     * 判断新资产是否重复
     * @param asset_no_new
     * @return
     */
    @RequestMapping(params = "assetNoNewIsRepeat",method = RequestMethod.GET)
    @ResponseBody
    public AjaxJson assetNoNewIsRepeat(String asset_no_new){
        AjaxJson json = new AjaxJson();
        Boolean noflag = false;
        if(asset_no_new!=null){
            noflag = assetService.findAssetNoNewRepeat(asset_no_new);
        }
        if(noflag){
            json.setMsg("ok");
        }else{
            json.setMsg("fail");
        }
        return json;
    }

    /**
     * 资产查询列表
     * @return
     */
    @RequestMapping(params = "assetList",method = RequestMethod.GET)
    @ResponseBody
    public List<HrmAssetInventory> assetList(Integer assetType, Integer assetBrand, String assetStatus, Integer useDept, Date startDate,Date endDate,String usePerson,Integer rows,Integer page) throws UnsupportedEncodingException {
        List<HrmAssetInventory> hrmAssetInventoryList = assetService.getAssetInventoryByQuery(assetType,assetBrand,assetStatus,useDept,startDate,endDate,usePerson,rows,page);
        return hrmAssetInventoryList;
    }

    /**
     * 资产查询页面跳转
     * @return
     */
    @RequestMapping(params = "queryAssetInventory",method = RequestMethod.GET)
    public ModelAndView assetListPageJump(){
        List<HrmAssetType> hrmAssetTypeList = assetService.getAssetTypeAll();
        List<HrmBrand> hrmBrandList = assetService.getBrandAll();
        List<HrmDepart> hrmDepartList = assetService.getDepartAll();
        ModelAndView modelAndView = new ModelAndView("hrm/asset/assetList");
        modelAndView.addObject("hrmAssetTypeList",hrmAssetTypeList);
        modelAndView.addObject("hrmBrandList",hrmBrandList);
        modelAndView.addObject("hrmDepartList",hrmDepartList);
        return modelAndView;
    }

    /**
     * 资产仅查询页面
     * @return
     */
    @RequestMapping(params = "queryAssetOnlyList",method = RequestMethod.GET)
    public ModelAndView queryAssetOnlyList(){
        List<HrmAssetType> hrmAssetTypeList = assetService.getAssetTypeAll();
        List<HrmBrand> hrmBrandList = assetService.getBrandAll();
        List<HrmDepart> hrmDepartList = assetService.getDepartAll();
        ModelAndView modelAndView = new ModelAndView("hrm/asset/assetOnlyList");
        modelAndView.addObject("hrmAssetTypeList",hrmAssetTypeList);
        modelAndView.addObject("hrmBrandList",hrmBrandList);
        modelAndView.addObject("hrmDepartList",hrmDepartList);
        return modelAndView;
    }

    /**
     * 根据资产id删除资产库存
     * @param assetId
     * @return
     */
    @RequestMapping(params = "delAsset",method = RequestMethod.GET)
    @ResponseBody
    public AjaxJson delAsset(String assetId){
        AjaxJson json = new AjaxJson();
        Boolean flag = false;
        if(assetId!=null){
            assetService.deleteAssetById(assetId);
            flag = true;
        }
        if(flag){
            json.setMsg("ok");
        }else{
            json.setMsg("fail");
        }
        return json;
    }

    /**
     * 资产详情
     * @param assetId
     * @return
     */
    @RequestMapping(params = "assetDetail",method = RequestMethod.GET)
    public ModelAndView assetDetail(String assetId){
        HrmAssetInventory hrmAssetInventory = assetService.findAssetById(assetId);
        ModelAndView modelAndView = new ModelAndView("hrm/asset/assetDetail");
        modelAndView.addObject("hrmAssetInventory",hrmAssetInventory);
        return modelAndView;
    }

    /**
     * 资产领用记录详情
     * @param assetId
     * @return
     */
    @RequestMapping(params = "collarRecordDetail",method = RequestMethod.GET)
    @ResponseBody
    public List<HrmCollarRecord> collarRecordDetail(String assetId,Integer rows,Integer page){
        List<HrmCollarRecord> hrmCollarRecordList = assetService.getHrmCollarRecordPageList(assetId,rows,page);
        return hrmCollarRecordList;
    }
    /**
     * 服务器端进行分页查询
     */
    /*@RequestMapping(params = "collarRecordDetail",method = RequestMethod.GET)
    @ResponseBody
    public void collarRecordDetail(String assetId, Integer rows, Integer page, HttpServletResponse response){
        Map map = new HashMap();
        map.put("total",assetService.getHrmCollarRecordByAssetId(assetId).size());
        List<HrmCollarRecord> hrmCollarRecordList = assetService.getHrmCollarRecordPageList(assetId,rows,page);
        map.put("rows",hrmCollarRecordList);
        String jsonString = JSON.toJSONString(map);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 资产修改页面跳转
     * @param assetId
     * @return
     */
    @RequestMapping(params = "assetEdit",method = RequestMethod.GET)
    public ModelAndView assetEdit(String assetId){
        HrmAssetInventory hrmAssetInventory = assetService.findAssetById(assetId);
        List<HrmDepart> hrmDepartList = assetService.getDepartAll();
        ModelAndView modelAndView = new ModelAndView("hrm/asset/assetUpdate");
        modelAndView.addObject("assetInventory",hrmAssetInventory);
        modelAndView.addObject("hrmDepartList",hrmDepartList);
        return modelAndView;
    }

    /**
     * 资产修改保存
     * @param hrmAssetInventory
     * @param hrmAssetTypeId
     * @param hrmBrandId
     * @param hrmDepartId
     * @return
     */
    @RequestMapping(params = "saveAssetEdit",method = RequestMethod.POST)
    public String saveAssetEdit(HrmAssetInventory hrmAssetInventory,Integer  hrmAssetTypeId,Integer hrmBrandId,Integer hrmDepartId){
        assetService.saveAssetEdit(hrmAssetInventory,hrmAssetTypeId,hrmBrandId,hrmDepartId);
        return "redirect:AssetController.do?queryAssetInventory";
    }
}
