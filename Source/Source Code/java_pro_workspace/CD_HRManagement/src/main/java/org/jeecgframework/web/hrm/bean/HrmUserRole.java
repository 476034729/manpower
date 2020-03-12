package org.jeecgframework.web.hrm.bean;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_hrm_user_role")
public class HrmUserRole extends IdEntity implements Serializable {
    private HrmUser hrmUser;
    private HrmRole hrmRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public HrmUser getHrmUser() {
        return hrmUser;
    }

    public void setHrmUser(HrmUser hrmUser) {
        this.hrmUser = hrmUser;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public HrmRole getHrmRole() {
        return hrmRole;
    }

    public void setHrmRole(HrmRole hrmRole) {
        this.hrmRole = hrmRole;
    }
}
