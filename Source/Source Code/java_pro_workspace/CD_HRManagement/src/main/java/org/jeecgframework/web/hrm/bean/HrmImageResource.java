package org.jeecgframework.web.hrm.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * 图片资源表
 */
@Entity
@Table(name = "t_hrm_image_resource")
public class HrmImageResource implements Serializable {
    @Id
    @Column(name = "id", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "url")
    private String url;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modify_user")
    private String modifyUser;

    @Column(name = "modify_id")
    private String modifyId;

    @Column(name = "modify_date")
    private Date modifyDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
