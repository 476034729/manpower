package org.jeecgframework.web.hrm.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:luodan
 * @Description:部门类型
 * @Date:2018-11-01 15:15
 */
@Entity
@Table(name="t_hrm_depart")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrmDepart {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="name")
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parentdepartid")
//    private HrmDepart HrmPDepart;//上级部门
//
//    @Column(name = "description", length = 500)
//    private String description;//部门描述
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "HrmPDepart")
//    private List<HrmDepart> HrmPDeparts = new ArrayList<HrmDepart>();//下属部门

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

//    public HrmDepart getHrmPDepart() {
//        return HrmPDepart;
//    }
//
//    public void setHrmPDepart(HrmDepart hrmPDepart) {
//        HrmPDepart = hrmPDepart;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public List<HrmDepart> getHrmPDeparts() {
//        return HrmPDeparts;
//    }
//
//    public void setHrmPDeparts(List<HrmDepart> hrmPDeparts) {
//        HrmPDeparts = hrmPDeparts;
//    }
}
