package org.jeecgframework.web.hrm.service.impl;

import org.apache.ibatis.annotations.Many;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.*;
import org.jeecgframework.web.hrm.service.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("assetServiceImpl")
@Transactional
public class AssetServiceImpl extends CommonServiceImpl implements AssetService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(AssetServiceImpl.class);


    @Override
    public List<HrmAssetType> getAssetTypeAll() {


        List<HrmAssetType> hrmAssetTypeList = this.getList(HrmAssetType.class);
        return hrmAssetTypeList;
    }

    @Override
    public List<HrmBrand> getBrandAll() {


        List<HrmBrand> hrmBrandList = this.getList(HrmBrand.class);
        return hrmBrandList;
    }

    @Override
    public List<HrmDepart> getDepartAll() {


        List<HrmDepart> hrmDepartList = this.getList(HrmDepart.class);
        return hrmDepartList;
    }

    /**
     * 保存资产库存
     *
     * @param hrmAssetInventory
     */
    @Override
    public void saveAsset(HrmAssetInventory hrmAssetInventory,Integer hrmAssetTypeId,Integer hrmBrandId,Integer hrmDepartId,String entryPersonId) {

        logger.info(">>AssetServiceImpl:saveAsset: "+new Date().toString());

        hrmAssetInventory.setId(UUID.randomUUID().toString());
        HrmAssetType hrmAssetType = this.get(HrmAssetType.class,hrmAssetTypeId+"");
        HrmBrand hrmBrand = this.get(HrmBrand.class,hrmBrandId+"");
        HrmDepart hrmDepart = this.get(HrmDepart.class,hrmDepartId+"");
        hrmAssetInventory.setHrmAssetType(hrmAssetType);
        hrmAssetInventory.setHrmBrand(hrmBrand);
        hrmAssetInventory.setHrmDepart(hrmDepart);
        hrmAssetInventory.setCreate_date(new Date());
        hrmAssetInventory.setModify_date(new Date());
        //如果库存状态为借出，将领用信息保存到领用记录表
        if("借出".equals(hrmAssetInventory.getAsset_status())){
            HrmCollarRecord hrmCollarRecord = new HrmCollarRecord();
            hrmCollarRecord.setId(UUID.randomUUID().toString());
            if(hrmAssetInventory.getLend_date()!=null){
                hrmCollarRecord.setLend_date(hrmAssetInventory.getLend_date());
            }
            if(hrmAssetInventory.getHrmDepart()!=null){
                hrmCollarRecord.setHrmDepart(hrmAssetInventory.getHrmDepart());
            }
            if(hrmAssetInventory.getUse_person()!=null){
                hrmCollarRecord.setLend_person(hrmAssetInventory.getUse_person());
            }
            if(hrmAssetInventory.getReturn_date()!=null){
                hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
            }
            if(hrmAssetInventory.getRemark()!=null){
                hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
            }
            hrmCollarRecord.setHrmAssetInventory(hrmAssetInventory);
            hrmCollarRecord.setUseStatues(HrmCollarRecord.USING);
            hrmCollarRecord.setCreate_date(new Date());
            hrmCollarRecord.setModify_date(new Date());
            this.save(hrmCollarRecord);
            List<HrmCollarRecord> collarRecordList = new ArrayList<HrmCollarRecord>();
            collarRecordList.add(hrmCollarRecord);
            hrmAssetInventory.setHrmCollarRecords(collarRecordList);
        }
        this.save(hrmAssetInventory);
    }

    /**
     * 查看资产编号是否重复
     *
     * @param asset_no
     * @return
     */
    @Override
    public Boolean findAssetNoRepeat(String asset_no) {


        String hql = "from HrmAssetInventory where asset_no = ?";
        List<HrmAssetInventory> hrmAssetInventoryList = this.findHql(hql,asset_no);
        if(hrmAssetInventoryList.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public List<HrmAssetInventory> getAssetInventoryList() {


        List<HrmAssetInventory> hrmAssetInventoryList = this.getList(HrmAssetInventory.class);
        return hrmAssetInventoryList;
    }

    /**
     * 根据id删除资产库存
     *
     * @param assetId
     * @return
     */
    @Override
    public void deleteAssetById(String assetId) {
        this.deleteEntityById(HrmAssetInventory.class,assetId);
    }

    /**
     * 根据资产库存id，查询库存
     *
     * @param assetId
     * @return
     */
    @Override
    public HrmAssetInventory findAssetById(String assetId) {
        HrmAssetInventory hrmAssetInventory = this.get(HrmAssetInventory.class,assetId);
        return hrmAssetInventory;
    }

    /**
     * 根据条件查询资产库存
     *
     * @param assetType
     * @param assetBrand
     * @param assetStatus
     * @param useDept
     * @param startDate
     * @param endDate
     * @param usePerson
     * @return
     */
    @Override
    public List<HrmAssetInventory> getAssetInventoryByQuery(Integer assetType, Integer assetBrand, String assetStatus, Integer useDept, Date startDate, Date endDate, String usePerson,Integer rows,Integer page) throws UnsupportedEncodingException {
        //服务器端实现分页
        /*String sql = "select * from t_hrm_asset_inventory where 1=1";
        Map map = new HashMap<String,Object>();
        Integer startRow = 0;
        Integer endRow = rows*page;
        if(page!=1){
            startRow = endRow-rows;
        }
        if(assetType!=null){
            sql = sql + " and asset_id=?";
            map.put("asset_id",assetType);
        }
        if(assetBrand!=null){
            sql = sql + " and brand_id=?";
            map.put("brand_id",assetBrand);
        }
        if(assetStatus!=null && assetStatus.trim().length()>0){
            String status=new String(assetStatus.getBytes("iso-8859-1"),"utf-8");
            sql = sql + " and asset_status=?";
            map.put("asset_status",status);
        }
        if(useDept!=null){
            sql = sql + " and dept_id=?";
            map.put("dept_id",useDept);
        }
        if(usePerson!=null && usePerson.trim().length()>0){
            String usePerson1=new String(usePerson.getBytes("iso-8859-1"),"utf-8");
            sql = sql + " and use_person=?";
            map.put("use_person",usePerson1);
        }
        if(startDate!=null){
            sql = sql + " and purchase_date>=?";
            map.put("purchase_date",startDate);
        }
        if(endDate!=null){
            sql = sql + " and purchase_date<=?";
            map.put("purchase_date",endDate);
        }
        sql = sql + " order by modify_date desc limit "+startRow+","+endRow+"";
        Query query = getSession().createSQLQuery(sql).addEntity(HrmAssetInventory.class).setProperties(map);
        List<HrmAssetInventory> hrmAssetInventoryList = query.list();*/
        //客户端实现分页


        String hql = "from HrmAssetInventory where 1=1";
        List<Object> params = new ArrayList<Object>();
        if(assetType!=null){
            hql = hql + " and asset_id=?";
            params.add(assetType);
        }
        if(assetBrand!=null){
            hql = hql + " and brand_id=?";
            params.add(assetBrand);
        }
        if(assetStatus!=null && assetStatus.trim().length()>0){
            String status=new String(assetStatus.getBytes("iso-8859-1"),"utf-8");
            hql = hql + " and asset_status=?";
            params.add(status);
        }
        if(useDept!=null){
            hql = hql + " and dept_id=?";
            params.add(useDept);
        }
        if(usePerson!=null && usePerson.trim().length()>0){
            String usePerson1=new String(usePerson.getBytes("iso-8859-1"),"utf-8");
            hql = hql + " and use_person=?";
            params.add(usePerson1);
        }
        if(startDate!=null){
            hql = hql + " and purchase_date>=?";
            params.add(startDate);
        }
        if(endDate!=null){
            hql = hql + " and purchase_date<=?";
            params.add(endDate);
        }
        hql = hql + " order by modify_date desc";
        List<HrmAssetInventory> hrmAssetInventoryList = this.findHql(hql,params.toArray());
        return hrmAssetInventoryList;
    }

    /**
     * 查询领用记录列表
     *
     * @param assetId
     * @return
     */
    @Override
    public List<HrmCollarRecord> getHrmCollarRecordPageList(String assetId,Integer rows,Integer page) {



        Integer startRow = 0;
        Integer endRow = rows*page;
        if(page!=1){
            startRow = endRow-rows;
        }
        String sql = "select * from t_hrm_collar_record where asset_id='"+assetId+"' order by lend_date desc limit "+startRow+","+endRow+"";
        Query query = getSession().createSQLQuery(sql).addEntity(HrmCollarRecord.class);
        List<HrmCollarRecord> hrmCollarRecordList = query.list();
        return hrmCollarRecordList;
    }

    /**
     * 根据库存id查询领用记录列表
     *
     * @param assetId
     * @return
     */
    @Override
    public List<HrmCollarRecord> getHrmCollarRecordByAssetId(String assetId) {



        String sql = "select * from t_hrm_collar_record where asset_id='"+assetId+"'";
        Query query = getSession().createSQLQuery(sql).addEntity(HrmCollarRecord.class);
        List<HrmCollarRecord> hrmCollarRecordList = query.list();
        return hrmCollarRecordList;
    }

    /**
     * 保存资产修改
     *
     * @param hrmAssetInventory
     * @param hrmAssetTypeId
     * @param hrmBrandId
     * @param hrmDepartId
     */
    @Override
    public void saveAssetEdit(HrmAssetInventory hrmAssetInventory, Integer hrmAssetTypeId, Integer hrmBrandId, Integer hrmDepartId) {

        logger.info(">>AssetServiceImpl:saveAssetEdit: "+new Date().toString());


        //未修改的资产库存记录
        HrmAssetInventory hrmAssetInventory1 = this.get(HrmAssetInventory.class,hrmAssetInventory.getId());
        HrmDepart hrmDepart = this.get(HrmDepart.class,hrmDepartId+"");
        hrmAssetInventory.setHrmAssetType(hrmAssetInventory1.getHrmAssetType());
        hrmAssetInventory.setHrmBrand(hrmAssetInventory1.getHrmBrand());
        hrmAssetInventory.setHrmDepart(hrmDepart);
        hrmAssetInventory.setAsset_no(hrmAssetInventory1.getAsset_no());
        hrmAssetInventory.setEntry_person_id(hrmAssetInventory1.getEntry_person_id());
        hrmAssetInventory.setEntry_person(hrmAssetInventory1.getEntry_person());
        hrmAssetInventory.setCreate_date(hrmAssetInventory1.getCreate_date());
        //查询该资产正在使用的资产记录
        String hql = "from HrmCollarRecord where asset_id=? and useStatues=?";
        List<HrmCollarRecord> hrmCollarRecordList = this.findHql(hql,hrmAssetInventory.getId(),HrmCollarRecord.USING);
        String hql1 =  "from HrmCollarRecord where asset_id=? order by modify_date desc";
        List<HrmCollarRecord> hrmCollarRecordList1 = this.findHql(hql1,hrmAssetInventory.getId());
        if("库存".equals(hrmAssetInventory.getAsset_status())){
            //该资产未被领用过
            if(hrmCollarRecordList1.size()==0){
                hrmAssetInventory.setModify_date(new Date());
                getSession().evict(hrmAssetInventory1);
                this.saveOrUpdate(hrmAssetInventory);
            }else{
                //该资产被领用过并归还
                if("借出".equals(hrmAssetInventory1.getAsset_status())&&"库存".equals(hrmAssetInventory.getAsset_status())){
                    HrmCollarRecord hrmCollarRecord = hrmCollarRecordList.get(0);
                    hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
                    hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
                    hrmCollarRecord.setUseStatues(HrmCollarRecord.RETURN);
                    hrmCollarRecord.setModify_date(new Date());
                    this.saveOrUpdate(hrmCollarRecord);
                    hrmAssetInventory.setHrmCollarRecords(hrmAssetInventory1.getHrmCollarRecords());
                    hrmAssetInventory.setModify_date(new Date());
                    getSession().evict(hrmAssetInventory1);
                    this.saveOrUpdate(hrmAssetInventory);
                }else{
                    HrmCollarRecord hrmCollarRecord = hrmCollarRecordList1.get(0);
                    hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
                    hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
                    hrmCollarRecord.setModify_date(new Date());
                    this.saveOrUpdate(hrmCollarRecord);
                    hrmAssetInventory.setHrmCollarRecords(hrmAssetInventory1.getHrmCollarRecords());
                    hrmAssetInventory.setModify_date(new Date());
                    getSession().evict(hrmAssetInventory1);
                    this.saveOrUpdate(hrmAssetInventory);
                }
            }
        }else if("借出".equals(hrmAssetInventory.getAsset_status())){
            if(hrmCollarRecordList.size()==0){
                //该资产以前未被领用过,第一次领用
                HrmCollarRecord hrmCollarRecord = new HrmCollarRecord();
                hrmCollarRecord.setId(UUID.randomUUID().toString());
                hrmCollarRecord.setHrmAssetInventory(hrmAssetInventory);
                hrmCollarRecord.setLend_date(hrmAssetInventory.getLend_date());
                hrmCollarRecord.setHrmDepart(hrmAssetInventory.getHrmDepart());
                hrmCollarRecord.setLend_person(hrmAssetInventory.getUse_person());
                hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
                hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
                hrmCollarRecord.setUseStatues(HrmCollarRecord.USING);
                hrmCollarRecord.setCreate_date(new Date());
                hrmCollarRecord.setModify_date(new Date());
                this.saveOrUpdate(hrmCollarRecord);
                List<HrmCollarRecord> list = null;
                //该资产被领用但当前状态为库存
                if(hrmCollarRecordList1.size()==0){
                    list = new ArrayList<HrmCollarRecord>();
                    list.add(hrmCollarRecord);
                }else{
                    list = hrmAssetInventory1.getHrmCollarRecords();
                    list.add(hrmCollarRecord);
                }
                hrmAssetInventory.setHrmCollarRecords(list);
                hrmAssetInventory.setModify_date(new Date());
                getSession().evict(hrmAssetInventory1);
                this.saveOrUpdate(hrmAssetInventory);
            }else if(hrmAssetInventory.getUse_person().equals(hrmCollarRecordList.get(0).getLend_person())){
                //资产领用修改
                HrmCollarRecord hrmCollarRecord = hrmCollarRecordList.get(0);
                hrmCollarRecord.setLend_date(hrmAssetInventory.getLend_date());
                hrmCollarRecord.setHrmDepart(hrmAssetInventory.getHrmDepart());
                hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
                hrmCollarRecord.setModify_date(new Date());
                hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
                getSession().evict(hrmAssetInventory1);
                hrmAssetInventory.setHrmCollarRecords(hrmAssetInventory1.getHrmCollarRecords());
                hrmAssetInventory.setModify_date(new Date());
                this.saveOrUpdate(hrmAssetInventory);
                this.saveOrUpdate(hrmCollarRecord);
            }else{
                //他人领用
                //修改先前领用人得领用状态
                HrmCollarRecord hrmCollarRecordOld = hrmCollarRecordList.get(0);
                hrmCollarRecordOld.setReturn_date(new Date());
                hrmCollarRecordOld.setUseStatues(HrmCollarRecord.RETURN);
                hrmCollarRecordOld.setModify_date(new Date());
                this.saveOrUpdate(hrmCollarRecordOld);

                HrmCollarRecord hrmCollarRecord = new HrmCollarRecord();
                hrmCollarRecord.setId(UUID.randomUUID().toString());
                hrmCollarRecord.setHrmAssetInventory(hrmAssetInventory);
                hrmCollarRecord.setLend_date(hrmAssetInventory.getLend_date());
                hrmCollarRecord.setHrmDepart(hrmAssetInventory.getHrmDepart());
                hrmCollarRecord.setLend_person(hrmAssetInventory.getUse_person());
                hrmCollarRecord.setReturn_date(hrmAssetInventory.getReturn_date());
                hrmCollarRecord.setRemark(hrmAssetInventory.getRemark());
                hrmCollarRecord.setUseStatues(HrmCollarRecord.USING);
                hrmCollarRecord.setCreate_date(new Date());
                hrmCollarRecord.setModify_date(new Date());
                this.saveOrUpdate(hrmCollarRecord);
                List<HrmCollarRecord> hrmCollarRecords = hrmAssetInventory1.getHrmCollarRecords();
                hrmCollarRecords.add(hrmCollarRecord);
                hrmAssetInventory.setHrmCollarRecords(hrmCollarRecords);
                hrmAssetInventory.setModify_date(new Date());
                getSession().evict(hrmAssetInventory1);
                this.saveOrUpdate(hrmAssetInventory);
            }
        }
    }

    @Override
    public Boolean findAssetNoNewRepeat(String asset_no_new) {


        String hql = "from HrmAssetInventory where asset_no = ?";
        List<HrmAssetInventory> hrmAssetInventoryList = this.findHql(hql,asset_no_new);
        if (hrmAssetInventoryList.size()>0){
            return false;
        }

        String newhql = "from HrmAssetInventory where asset_no_new = ?";
        List<HrmAssetInventory> hrmAssetInventoryListnew = this.findHql(newhql,asset_no_new);
        if (hrmAssetInventoryListnew.size()>0){
            return false;
        }

        return true;
    }
}
