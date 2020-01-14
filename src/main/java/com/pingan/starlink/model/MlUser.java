package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ml_user")
public class MlUser {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 权限
     */
    private String permission;

    /**
     * 子账号类型
     */
    private String type;

    /**
     * 子账号注册者的真实姓名
     */
    private String realname;

    /**
     * 所在部门
     */
    private String department;

    /**
     * 职位
     */
    private String position;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 办公地址
     */
    @Column(name = "office_address")
    private String officeAddress;

    /**
     * 固定电话
     */
    @Column(name = "landline_phone")
    private String landlinePhone;

    /**
     * 附加信息
     */
    @Column(name = "extra_info")
    private String extraInfo;

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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取权限
     *
     * @return permission - 权限
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限
     *
     * @param permission 权限
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 获取子账号类型
     *
     * @return type - 子账号类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置子账号类型
     *
     * @param type 子账号类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取子账号注册者的真实姓名
     *
     * @return realname - 子账号注册者的真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置子账号注册者的真实姓名
     *
     * @param realname 子账号注册者的真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取所在部门
     *
     * @return department - 所在部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置所在部门
     *
     * @param department 所在部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取职位
     *
     * @return position - 职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置职位
     *
     * @param position 职位
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱地址
     *
     * @return email - 邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱地址
     *
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取办公地址
     *
     * @return office_address - 办公地址
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * 设置办公地址
     *
     * @param officeAddress 办公地址
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    /**
     * 获取固定电话
     *
     * @return landline_phone - 固定电话
     */
    public String getLandlinePhone() {
        return landlinePhone;
    }

    /**
     * 设置固定电话
     *
     * @param landlinePhone 固定电话
     */
    public void setLandlinePhone(String landlinePhone) {
        this.landlinePhone = landlinePhone;
    }

    /**
     * 获取附加信息
     *
     * @return extra_info - 附加信息
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * 设置附加信息
     *
     * @param extraInfo 附加信息
     */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}