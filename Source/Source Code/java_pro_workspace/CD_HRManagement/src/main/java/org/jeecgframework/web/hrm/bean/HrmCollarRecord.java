package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author:luodan
 * @Description:资产库存表
 * @Date:2018-11-01 15:30
 */
@Entity
@Table(name = "t_hrm_collar_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrmCollarRecord {
    //使用状态取值
    //使用中
    public static final Integer USING = 1;
    //已归还
    public static final Integer RETURN = 2;

    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private HrmAssetInventory hrmAssetInventory;
    @Column(name = "lend_date")
    private Date lend_date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lend_dept_id")
    private HrmDepart hrmDepart;
    @Column(name = "lend_person")
    private String lend_person;
    @Column(name = "return_date")
    private Date return_date;
    @Column(name = "remark")
    private String remark;
    @Column(name = "useStatues")
    private Integer useStatues;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLend_date() {
        return lend_date;
    }

    public void setLend_date(Date lend_date) {
        this.lend_date = lend_date;
    }

    public HrmDepart getHrmDepart() {
        return hrmDepart;
    }

    public void setHrmDepart(HrmDepart hrmDepart) {
        this.hrmDepart = hrmDepart;
    }

    public String getLend_person() {
        return lend_person;
    }

    public void setLend_person(String lend_person) {
        this.lend_person = lend_person;
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

    public HrmAssetInventory getHrmAssetInventory() {
        return hrmAssetInventory;
    }

    public void setHrmAssetInventory(HrmAssetInventory hrmAssetInventory) {
        this.hrmAssetInventory = hrmAssetInventory;
    }

    public Integer getUseStatues() {
        return useStatues;
    }

    public void setUseStatues(Integer useStatues) {
        this.useStatues = useStatues;
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
}
