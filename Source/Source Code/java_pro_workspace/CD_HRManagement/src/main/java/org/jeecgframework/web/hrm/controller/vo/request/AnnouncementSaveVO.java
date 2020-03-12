package org.jeecgframework.web.hrm.controller.vo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wang Yu
 * 公告保存
 */
public class AnnouncementSaveVO implements Serializable {

    private String title;

    private String content;

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
}
