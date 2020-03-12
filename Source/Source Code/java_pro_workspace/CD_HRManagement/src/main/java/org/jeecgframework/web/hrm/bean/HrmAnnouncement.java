package org.jeecgframework.web.hrm.bean;

import com.hp.hpl.sparta.xpath.Step;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * 公告表
 */
@Entity
@Table(name = "t_hrm_announcement")
@DynamicUpdate(true)
public class HrmAnnouncement implements Serializable {
    @Id
    @Column(name = "id", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(name = "modify_user")
    private String modifyUser;

    @Column(name = "modify_id")
    private String modifyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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
}
