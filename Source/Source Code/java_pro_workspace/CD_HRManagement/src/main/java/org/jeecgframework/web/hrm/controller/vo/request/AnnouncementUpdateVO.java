package org.jeecgframework.web.hrm.controller.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 公告更新
 * @author Wang Yu
 */
public class AnnouncementUpdateVO implements Serializable {
    @NotNull
    @NotBlank
    private String id;

    private String title;

    private String content;

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
}
