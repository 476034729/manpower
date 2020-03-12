package org.jeecgframework.web.hrm.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.hrm.bean.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public interface AssetService extends CommonService {
    /**
     * 获取所有的资产类型
     * @return
     */
    List<HrmAssetType> getAssetTypeAll();

    /**
     * 获取所有的品牌
     * @return
     */
    List<HrmBrand> getBrandAll();

    /**
     * 获取所有的部门
     * @return
     */
    List<HrmDepart> getDepartAll();

    /**
     * 保存资产库存
     * @param hrmAssetInventory
     */
    void saveAsset(HrmAssetInventory hrmAssetInventory,Integer hrmAssetTypeId,Integer hrmBrandId,Integer hrmDepartId,String entryPersonId);

    /**
     * 查看资产编号是否重复
     * @param asset_no
     * @return
     */
    Boolean findAssetNoRepeat(String asset_no);

    List<HrmAssetInventory> getAssetInventoryList();

    /**
     * 根据id删除资产库存
     * @param assetId
     * @return
     */
    void deleteAssetById(String assetId);

    /**
     * 根据资产库存id，查询库存
     * @param assetId
     * @return
     */
    HrmAssetInventory findAssetById(String assetId);

    /**
     * 根据条件查询资产库存
     * @param assetType
     * @param assetBrand
     * @param assetStatus
     * @param useDept
     * @param startDate
     * @param endDate
     * @param usePerson
     * @return
     */
    List<HrmAssetInventory> getAssetInventoryByQuery(Integer assetType, Integer assetBrand, String assetStatus, Integer useDept, Date startDate, Date endDate, String usePerson,Integer rows,Integer page) throws UnsupportedEncodingException;

    /**
     * 分页查询领用记录列表
     * @param assetId
     * @return
     */
    List<HrmCollarRecord> getHrmCollarRecordPageList(String assetId,Integer rows,Integer page);

    /**
     * 根据库存id查询领用记录列表
     * @param assetId
     * @return
     */
    List<HrmCollarRecord> getHrmCollarRecordByAssetId(String assetId);

    /**
     * 保存资产修改
     * @param hrmAssetInventory
     * @param hrmAssetTypeId
     * @param hrmBrandId
     * @param hrmDepartId
     */
    void saveAssetEdit(HrmAssetInventory hrmAssetInventory,Integer  hrmAssetTypeId,Integer hrmBrandId,Integer hrmDepartId);

    /**
     * 新资产编号是否重复
     * @param asset_no_new
     * @return
     */
    Boolean findAssetNoNewRepeat(String asset_no_new);
}
