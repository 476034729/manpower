package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:luodan
 * @Description:资产类型
 * @Date:2018-11-01 15:13
 */
@Entity
@Table(name="t_hrm_asset_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrmAssetType {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name="name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
