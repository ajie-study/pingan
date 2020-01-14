package com.pingan.starlink.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

public class Notic {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 正文内容
     */
    private String context;

    /**
     * 创建者
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createdAt;

    /**
     * 获取UUID唯一标识符
     *
     * @return uuid - UUID唯一标识符
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置UUID唯一标识符
     *
     * @param uuid UUID唯一标识符
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取正文内容
     *
     * @return context - 正文内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置正文内容
     *
     * @param context 正文内容
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * 获取创建者
     *
     * @return created_by - 创建者
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建者
     *
     * @param createdBy 创建者
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}