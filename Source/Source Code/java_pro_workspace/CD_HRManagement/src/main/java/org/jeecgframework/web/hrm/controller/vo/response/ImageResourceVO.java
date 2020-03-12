package org.jeecgframework.web.hrm.controller.vo.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * 图片实体对象
 */
public class ImageResourceVO implements Serializable {
    // ID
    private String id;

    private String url;

    private String description;

    private String title;

    //上传者姓名
    private String createUser;

    //上传时间
    private Date createDate;

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
