package com.pingan.starlink.model;

import javax.persistence.*;

@Table(name = "issue_field")
public class IssueField {
    /**
     * 主键uuid
     */
    @Id
    private String uuid;

    /**
     * 字段Id(jira生成的值)
     */
    @Column(name = "field_id")
    private String fieldId;

    /**
     * 字段名(jira配置的名称)
     */
    @Column(name = "field_name")
    private String fieldName;

    /**
     * protal中定义字段
     */
    @Column(name = "field_key")
    private String fieldKey;

    /**
     * 获取主键uuid
     *
     * @return uuid - 主键uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置主键uuid
     *
     * @param uuid 主键uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取字段Id(jira生成的值)
     *
     * @return field_id - 字段Id(jira生成的值)
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * 设置字段Id(jira生成的值)
     *
     * @param fieldId 字段Id(jira生成的值)
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取字段名(jira配置的名称)
     *
     * @return field_name - 字段名(jira配置的名称)
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设置字段名(jira配置的名称)
     *
     * @param fieldName 字段名(jira配置的名称)
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 获取protal中定义字段
     *
     * @return field_key - protal中定义字段
     */
    public String getFieldKey() {
        return fieldKey;
    }

    /**
     * 设置protal中定义字段
     *
     * @param fieldKey protal中定义字段
     */
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}