package com.pingan.starlink.enums;

/**
 * git_branch 分支状态枚举
 */
public enum StatusEnum {

    OPEN_STATUS("open","打开状态"),
    CLOSE_STATUS("close","关闭状态"),
    PR_STATUS_MREGED("merged","合并状态的"),
    RELEASE("release","发布"),
    UNRELEASE("unRelease","未发布");

    /**
     * 状态
     */
    private String status;

    /**
     * 描述
     */
    private String description;



    StatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
