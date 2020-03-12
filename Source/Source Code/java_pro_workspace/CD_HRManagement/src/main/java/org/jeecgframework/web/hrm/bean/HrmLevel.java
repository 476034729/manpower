//package org.jeecgframework.web.hrm.bean;
//
//
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.jeecgframework.core.common.entity.IdEntity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.io.Serializable;
//
///**
// *
// * 级别表
// */
//
//@Entity
//@Table(name = "t_hrm_level")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class HrmLevel extends IdEntity implements Serializable {
//
//    @Column(name = "level")
//    private String level;//级别
//
//    @Column(name = "salary")
//    private Double salary;//月成本(薪资)
//
//    @Column(name = "remark")
//    private String remark;//备注
//
//    public HrmLevel() {
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public Double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(Double salary) {
//        this.salary = salary;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//}
