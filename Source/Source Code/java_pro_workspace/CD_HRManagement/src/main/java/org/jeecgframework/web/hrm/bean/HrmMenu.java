package org.jeecgframework.web.hrm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "t_hrm_menu")
public class HrmMenu implements Serializable {
    @Id
    @Column(name = "menu_id")
    private String menu_id;
    @Column(name = "parent_id")
    private String parent_id;
    @Column(name = "menu_name")
    private String menu_name;
    @Column(name = "level")
    private String level;
    @Column(name = "menu_url")
    private String menu_url;
    @Column(name = "type")
    private String type;

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
