package org.jeecgframework.web.hrm.bean.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * LoopPlayConfig 和 ImageResource 的组合对象
 */
@Entity
public class LoopPlayConfigDTO implements Serializable {
    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "img_id")
    private String imgId;

    @Column(name = "url")
    private String url;

    // 轮播标题
    @Column(name = "title")
    private String title;
    // 轮播描述
    @Column(name = "description")
    private String description;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(name = "modify_user")
    private String modifyUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
