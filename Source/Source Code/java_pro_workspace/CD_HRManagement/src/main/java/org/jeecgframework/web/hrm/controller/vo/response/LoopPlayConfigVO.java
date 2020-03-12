package org.jeecgframework.web.hrm.controller.vo.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * 轮播配置对象
 */
public class LoopPlayConfigVO implements Serializable {

    // 配置主键
    private String id;

    // 图片ID
    private String imgId;

    // 图片位置
    private String url;

    // 图片描述
    private String description;

    // 图片标题
    private String title;

    // 图片描述
    private Integer order;

    private Date createDate;

    private String createUser;

    private Date modifyDate;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
}
