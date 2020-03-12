package org.jeecgframework.web.hrm.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_hrm_checkprocess")
public class CheckProcess {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "department")
    private String department;
    @Column(name = "functionary_id")
    private String functionary_id;
    @Column(name = "conductor_id")
    private String conductor_id;
    @Column(name = "personnel_id")
    private String personnel_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFunctionary_id() {
        return functionary_id;
    }

    public void setFunctionary_id(String functionary_id) {
        this.functionary_id = functionary_id;
    }

    public String getConductor_id() {
        return conductor_id;
    }

    public void setConductor_id(String conductor_id) {
        this.conductor_id = conductor_id;
    }

    public String getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(String personnel_id) {
        this.personnel_id = personnel_id;
    }
}
