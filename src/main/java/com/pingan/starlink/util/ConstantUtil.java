package com.pingan.starlink.util;

import org.gitlab4j.api.models.Visibility;

import java.util.HashSet;

public class ConstantUtil {
    /*
       emample:
           JIRA_FIELD_TPYE
           GIT_
     */
//    public static final String GIT_PROJECT_TYPE = "type";
//    public static final String GIT_PROJECT_TYPE_CREATE = "create";
//    public static final String GIT_PROJECT_TYPE_RELATE = "relate";
//    public static final String GIT_PROJECT_PROJECT_NAME = "project_name";
//    public static final String GIT_PROJECT_GROUP_ID = "group_Id";
    public static final Visibility GIT_PROJECT_VISIBILITY_DEFAULT = Visibility.valueOf("PUBLIC");
    public static final Boolean GIT_PROJECT_initializeWithReadme_DEFAULT = true;

    //jira项目状态
    public static final String READY = "ready"; //成功
    public static final String CREATING = "creating";//创建中
    public static final String FAILED = "failed"; //失败

    //版本的发布状态
    public static final String NOT_RELEASE = "not_release"; //未发布
    public static final String PRE_RELEASE = "pre_release"; //预发布
    public static final String READY_RELEASE = "ready_release"; //已发布

    // 版本的操作状态
    public static final String RELEASE = "release"; //发布
    public static final String CONFIRM = "confirm"; //确认
    public static final String ROLL_BACK = "roll_back"; //回滚

    public static final HashSet<String> projectTemplates = new HashSet<String>() {{
        add("com.pyxis.greenhopper.jira:gh-scrum-template");
        add("com.pyxis.greenhopper.jira:gh-kanban-template");
        add("com.pyxis.greenhopper.jira:basic-software-development-template");
        add("com.atlassian.jira-core-project-templates:jira-core-project-management");
        add("com.atlassian.jira-core-project-templates:jira-core-task-management");
        add("com.atlassian.jira-core-project-templates:jira-core-process-management");
    }};

    public static final String projectTypeKey = "software";

    // 监控项目标识
    public static final String TOPIC_ID = "pab-star-starlink";

}
