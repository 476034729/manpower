package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:luodan
 * @Description:资产库存表
 * @Date:2018-11-01 15:23
 */
@Entity
@Table(name = "t_hrm_asset_inventory")
@JsonIgnoreProperties({"hrmCollarRecords","hibernateLazyInitializer", "handler"})
public class HrmAssetInventory implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private HrmAssetType hrmAssetType;
    @Column(name = "asset_no")
    private String asset_no;
    @Column(name = "entry_person_id")
    private String entry_person_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private HrmBrand hrmBrand;
    @Column(name = "hard_memory_size")
    private String hard_memory_size;
    @Column(name = "disk_size")
    private String disk_size;
    @Column(name = "model_specifiction")
    private String model_specifiction;
    @Column(name = "detailed_configuration")
    private String detailed_configuration;
    @Column(name = "purchase_unit_price")
    private Double purchase_unit_price;
    @Column(name = "purchase_date")
    private Date purchase_date;
    @Column(name = "use_date")
    private Date use_date;
    @Column(name = "asset_status")
    private String asset_status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private HrmDepart hrmDepart;
    @Column(name = "use_person")
    private String use_person;
    @Column(name = "use_type")
    private String use_type;
    @Column(name = "lend_date")
    private Date lend_date;
    @Column(name = "return_date")
    private Date return_date;
    @Column(name = "remark")
    private String remark;
    @OneToMany
    @JoinColumn(name = "asset_id")
    private List<HrmCollarRecord> hrmCollarRecords;
    @Column(name = "depreciation_years")
    private Integer depreciation_years;
    @Column(name = "entry_person")
    private String entry_person;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;
    @Column(name = "host_no")
    private String host_no;

    @Column(name = "ip")
    private String ip;//ip

    @Column(name = "asset_no_new")
    private String asset_no_new;//新资产编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsset_no() {
        return asset_no;
    }

    public void setAsset_no(String asset_no) {
        this.asset_no = asset_no;
    }

    public String getEntry_person_id() {
        return entry_person_id;
    }

    public void setEntry_person_id(String entry_person_id) {
        this.entry_person_id = entry_person_id;
    }

    public String getEntry_person() {
        return entry_person;
    }

    public void setEntry_person(String entry_person) {
        this.entry_person = entry_person;
    }

    public String getHard_memory_size() {
        return hard_memory_size;
    }

    public void setHard_memory_size(String hard_memory_size) {
        this.hard_memory_size = hard_memory_size;
    }

    public String getDisk_size() {
        return disk_size;
    }

    public void setDisk_size(String disk_size) {
        this.disk_size = disk_size;
    }

    public String getModel_specifiction() {
        return model_specifiction;
    }

    public void setModel_specifiction(String model_specifiction) {
        this.model_specifiction = model_specifiction;
    }

    public String getDetailed_configuration() {
        return detailed_configuration;
    }

    public void setDetailed_configuration(String detailed_configuration) {
        this.detailed_configuration = detailed_configuration;
    }

    public Double getPurchase_unit_price() {
        return purchase_unit_price;
    }

    public void setPurchase_unit_price(Double purchase_unit_price) {
        this.purchase_unit_price = purchase_unit_price;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Date getUse_date() {
        return use_date;
    }

    public void setUse_date(Date use_date) {
        this.use_date = use_date;
    }

    public String getAsset_status() {
        return asset_status;
    }

    public void setAsset_status(String asset_status) {
        this.asset_status = asset_status;
    }

    public HrmAssetType getHrmAssetType() {
        return hrmAssetType;
    }

    public void setHrmAssetType(HrmAssetType hrmAssetType) {
        this.hrmAssetType = hrmAssetType;
    }

    public HrmBrand getHrmBrand() {
        return hrmBrand;
    }

    public void setHrmBrand(HrmBrand hrmBrand) {
        this.hrmBrand = hrmBrand;
    }

    public HrmDepart getHrmDepart() {
        return hrmDepart;
    }

    public void setHrmDepart(HrmDepart hrmDepart) {
        this.hrmDepart = hrmDepart;
    }

    public String getUse_person() {
        return use_person;
    }

    public void setUse_person(String use_person) {
        this.use_person = use_person;
    }

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }

    public Date getLend_date() {
        return lend_date;
    }

    public void setLend_date(Date lend_date) {
        this.lend_date = lend_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<HrmCollarRecord> getHrmCollarRecords() {
        return hrmCollarRecords;
    }

    public void setHrmCollarRecords(List<HrmCollarRecord> hrmCollarRecords) {
        this.hrmCollarRecords = hrmCollarRecords;
    }

    public Integer getDepreciation_years() {
        return depreciation_years;
    }

    public void setDepreciation_years(Integer depreciation_years) {
        this.depreciation_years = depreciation_years;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public String getHost_no() {
        return host_no;
    }

    public void setHost_no(String host_no) {
        this.host_no = host_no;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAsset_no_new() {
        return asset_no_new;
    }

    public void setAsset_no_new(String asset_no_new) {
        this.asset_no_new = asset_no_new;
    }
}
