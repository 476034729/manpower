package org.jeecgframework.web.hrm.controller.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wang Yu
 * 更新轮播配置
 */
public class LoopPlayConfigUpdateVO implements Serializable {
    /**
     * 图片ID
     */
    @NotNull
    @NotBlank
    private String imgId;

    // 轮播标题
    private String title;
    // 轮播描述
    private String description;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
