package org.jeecgframework.web.hrm.controller.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wang Yu
 * 公告 ID vo
 */
public class AnnouncementIdVO implements Serializable {
    @NotNull
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
