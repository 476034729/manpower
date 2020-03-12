package org.jeecgframework.web.hrm.bean;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_hrm_role_menu")
public class HrmRoleMenu extends IdEntity implements Serializable {
    private HrmRole hrmRole;
    private HrmMenu hrmMenu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public HrmRole getHrmRole() {
        return hrmRole;
    }

    public void setHrmRole(HrmRole hrmRole) {
        this.hrmRole = hrmRole;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    public HrmMenu getHrmMenu() {
        return hrmMenu;
    }

    public void setHrmMenu(HrmMenu hrmMenu) {
        this.hrmMenu = hrmMenu;
    }
}
