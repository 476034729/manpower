package org.jeecgframework.web.hrm.controller.vo.response;

import java.util.Date;

/**
 * @author Wang Yu
 * 公告详情
 */
public class AnnouncementVO {
    // ID
    private String id;

    // 标题
    private String title;

    // 内容
    private String content;

    // 发布日期
    private Date createDate;

    // 发布人姓名
    private String createUser;

    // 修改日期
    private Date modifyDate;

    // 修改人名称
    private String modifyUser;

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
