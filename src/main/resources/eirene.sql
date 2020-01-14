create database if not exists eirene default character set = 'utf8';
use eirene;

-- DB建模规范：
--  表的主键统一设置为uuid，插入时使用EireneUtil.randomUUID()来生成
--  不允许重复的列，使用UNIQUE关键字来约束
--  关联其他表的列，使用FOREIGN KEY关键字来约束
--  外键存在依赖关系时，使用REFERENCES table_name (col_name) ON DELETE CASCADE来关联删除
--  建表sql中，必须为表以及字段用COMMENT添加注释
--  建表成功后，填入sql文本的语句，需为Navicat导出的标准格式的建表SQL

-- PROJECT TABLE
CREATE TABLE IF NOT EXISTS `project` (
  `uuid` VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `project_key` varchar(10) NOT NULL COMMENT '项目Key',
  `project_name` varchar(160) NOT NULL COMMENT '项目名称',
  `project_template_key` varchar(256) DEFAULT NULL COMMENT '项目模板',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `lead` varchar(64) NOT NULL COMMENT '项目领导',
  `assigneeType` varchar(64) DEFAULT "PROJECT_LEAD" COMMENT '被分配的',
  `project_type` varchar(64) DEFAULT NULL COMMENT '项目类型',
  `system` varchar(64) DEFAULT NULL COMMENT '系统',
  `sub_system` varchar(64) DEFAULT NULL COMMENT '子系统',
  `dev_mode` varchar(64) DEFAULT NULL COMMENT '开发模式',
  `host_system` varchar(64) DEFAULT NULL COMMENT '主办系统',
  `auxiliary_system` varchar(64) DEFAULT NULL COMMENT '辅办系统',
  `dev_group` varchar(64) DEFAULT NULL COMMENT '开发组',
  `pmo` varchar(64) DEFAULT NULL COMMENT 'PMO',
  `status` varchar(64) default null COMMENT '空间状态',
  `end_date` date DEFAULT NULL COMMENT '结项日期',
  `plan_end_date` date DEFAULT NULL COMMENT '计划结束日期',
  `create_date` date DEFAULT NULL COMMENT '立项日期',
  `owner` varchar(64) DEFAULT NULL COMMENT '项目owner',
  `plan_start_date` date DEFAULT NULL COMMENT '计划开始日期',
  `artifactory_url` varchar(256) DEFAULT NULL COMMENT '仓库地址',
  `department` varchar(64) DEFAULT NULL  COMMENT '所属部门',
  `project_status` varchar(64) default "creating" COMMENT '项目创建状态',
  UNIQUE KEY `project_key` (`project_key`),
  UNIQUE KEY `project_name` (`project_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

-- NOTIC TABLE
CREATE TABLE IF NOT EXISTS `notic` (
  uuid VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `context` varchar(200) DEFAULT NULL COMMENT '正文内容',
  `created_by` varchar(36) DEFAULT NULL COMMENT '创建者',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知';

-- ISSUE_FIELD TABLE
CREATE TABLE `issue_field` (
  `uuid` varchar(36) NOT NULL DEFAULT '' COMMENT '主键uuid',
  `field_id` varchar(20) NOT NULL COMMENT '字段Id(jira生成的值)',
  `field_name` varchar(20) NOT NULL COMMENT '字段名(jira配置的名称)',
  `field_key` varchar(20) NOT NULL COMMENT 'protal中定义字段',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `field_id` (`field_id`),
  UNIQUE KEY `field_key` (`field_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GIT PROJECT TABLE
CREATE TABLE IF NOT EXISTS `git_project` (
  `uuid` VARCHAR(36) NOT NULL COMMENT 'UUID唯一标识符',
  `git_project_id` int(11) NOT NULL COMMENT 'gitlab生成的值',
  `git_namespace_id` int(11) NOT NULL COMMENT 'gitlab组的id',
  `git_project_name` varchar(64) NOT NULL COMMENT 'gitproject名称',
  `git_project_url` varchar(256) DEFAULT NULL COMMENT 'git仓库地址',
  `git_project_description` varchar(256) NOT NULL COMMENT 'gitProject描述',
  `git_project_created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'gitproject创建时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `git_project_id` (`git_project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git项目表';

-- GIT BRANCH TABLE
CREATE TABLE IF NOT EXISTS `git_branch` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `git_branch_name` varchar(64) NOT NULL COMMENT '分支名称',
  `git_project_id` int(11) NOT NULL COMMENT '分支所属的git项目id(gitlab生成的值)',
  `description` varchar(200) DEFAULT NULL COMMENT '分支描述',
  `created_by` varchar(36) DEFAULT NULL COMMENT '分支创建者',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分支创建时间',
  `release` tinyint(1) NOT NULL DEFAULT '0' COMMENT '分支是否为发布版本',
  `commit_by` varchar(24) DEFAULT NULL COMMENT '最近提交人',
  `commit_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最近提交时间',
  `branch_status` varchar(64) DEFAULT "open" COMMENT '分支状态',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `git_branch_name` (`git_branch_name`,`git_project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git分支表';


-- GIT PROJECT RELATION TABLE
CREATE TABLE IF NOT EXISTS `jira_gitproject_relation` (
  `uuid` VARCHAR(36) NOT NULL COMMENT 'UUID唯一标识符',
  `git_project_id` int(11) NOT NULL COMMENT 'gitlab生成的id',
  `jira_project_key` varchar(64) NOT NULL COMMENT 'jira项目的唯一标识key',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`git_project_id`,`jira_project_key`),
  FOREIGN KEY (`git_project_id`) REFERENCES `git_project` (`git_project_id`) ON DELETE CASCADE,
  FOREIGN KEY (`jira_project_key`) REFERENCES `project` (`project_key`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gitprojcet、jira关联关系表';

-- VERSION_MANAGEMENT TABLE
CREATE TABLE IF NOT EXISTS `version_management` (
  `uuid` VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `version_name` varchar(64) NOT NULL COMMENT '版本号',
  `version_id` varchar(64) DEFAULT NULL COMMENT '版本id',
  `version_type` varchar(64) DEFAULT NULL COMMENT '版本类型',
  `risk_level` varchar(64) DEFAULT NULL COMMENT '风险等级',
  `test_status` varchar(64) DEFAULT NULL COMMENT '测试状态',
  `start_date` DATE DEFAULT NULL COMMENT '开始日期',
  `release_date` varchar(64) DEFAULT NULL COMMENT '发布日期',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '版本创建时间 默认当前时间',
  `develop_contacts` varchar(64) DEFAULT NULL COMMENT '开发联系人',
  `dependent_version` varchar(64) DEFAULT NULL COMMENT '依赖版本',
  `test_plan` varchar(64) DEFAULT NULL COMMENT '测试计划',
  `version_describe` varchar(256) DEFAULT NULL COMMENT '版本描述',
  `project_key` varchar(64) NOT NULL COMMENT '所属项目',
  `version_status` varchar(64) DEFAULT "not_release" COMMENT '版本状态',
  `issue_num` int(64) NOT NULL default 0  COMMENT '问题的总数',
  `release_issues` varchar(256) default null COMMENT '选择要发布问题类型',
  `version_manager` varchar(64) DEFAULT NULL COMMENT '版本负责人',
  UNIQUE KEY `version_id` (`version_id`),
  UNIQUE KEY (`project_key`,`version_name`),
  FOREIGN KEY (`project_key`) REFERENCES `project` (`project_key`) ON DELETE CASCADE
) ENGINE InnoDB DEFAULT CHARSET utf8 COMMENT '版本管理表';

-- GITGROUP TABLE
CREATE TABLE IF NOT EXISTS `git_group` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `gitgroup_id` int(11) NOT NULL COMMENT 'git组的id',
  `gitgroup_fullname` varchar(64) DEFAULT NULL COMMENT 'gitgroup全名唯一标识',
  `gitgroup_fullpath` varchar(64) DEFAULT NULL COMMENT 'gitgroup路径',
  `token` varchar(64)  NOT NULL COMMENT 'gitgroup内master用户access token',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`gitgroup_id`,`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gitgroup表';

-- GITGROUP DEPARTMENT RELATION
CREATE TABLE IF NOT EXISTS `gitgroup_department_relation` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `gitgroup_id` int(11) NOT NULL COMMENT 'git组的id',
  `department` varchar(64) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`gitgroup_id`,`department`),
  FOREIGN KEY (`gitgroup_id`) REFERENCES `git_group`(`gitgroup_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码仓库与部门关联关系表';

-- GIT PULL REQUESTS TABLE
CREATE TABLE IF NOT EXISTS `git_pull_requests` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `git_pr_id` int(11) NOT NULL COMMENT 'pr的id(gitlab生成)',
  `git_pr_title` varchar(64) DEFAULT NULL COMMENT 'pr标题',
  `git_pr_createby` varchar(64) DEFAULT NULL COMMENT 'pr创建人',
  `git_pr_createat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'pr创建时间',
  `git_pr_status` varchar(32) NOT NULL COMMENT 'pr的状态',
  `jira_project_key` varchar(10) NOT NULL COMMENT '相关联的jira id',
  `git_project_id` int(11) NOT NULL COMMENT 'gitproject的id',
  `git_pr_assignee` varchar(32) COMMENT 'pr审阅者',
  `jira_issue` varchar(256) COMMENT 'jira的issue',
  `git_pr_describe` varchar(256) COMMENT 'pr描述',
  `mg_commit_id` VARCHAR(64) COMMENT '提交id',
  `mg_commitby` VARCHAR(64) COMMENT '提交人',
  `mg_commitat` DATE DEFAULT NULL COMMENT '提交时间',
  `mg_commit_info` VARCHAR(256) COMMENT '提交信息',
  `mg_branch_name` VARCHAR(64) COMMENT '目标分支名称',
  `git_pr_iid` INT(11) NOT NULL COMMENT 'pr在仓库下的id',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`git_pr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gir pr表';


-- --------------------------------------------------
-- PROJECT TABLE
CREATE TABLE IF NOT EXISTS `ml_user` (
  `uuid` VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `created_at` timestamp COMMENT '创建时间',
  `updated_at` timestamp COMMENT '更新时间',
  `permission` varchar(64) DEFAULT NULL COMMENT '权限',
  `type` varchar(64) DEFAULT NULL COMMENT '子账号类型',
  `realname` varchar(64) DEFAULT NULL COMMENT '子账号注册者的真实姓名',
  `department` varchar(64) NOT NULL COMMENT '所在部门',
  `position` varchar(64) DEFAULT NULL COMMENT '职位',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱地址',
  `office_address` varchar(64) DEFAULT NULL COMMENT '办公地址',
  `landline_phone` varchar(64) DEFAULT NULL COMMENT '固定电话',
  `extra_info` varchar(64) DEFAULT NULL COMMENT '附加信息',
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- DEPARTMENT TABLE
CREATE TABLE IF NOT EXISTS `ml_department` (
  uuid VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `department_name` varchar(64) NOT NULL COMMENT '部门名称',
  `department_key` varchar(10) NOT NULL COMMENT '部门KEY',
  `next_project_index` int(11) DEFAULT 1 COMMENT '部门下创建新项目时的项目索引',
  `roles` varchar(64) DEFAULT NULL COMMENT '部门角色',
  `superior_department` varchar(64) DEFAULT NULL COMMENT '上级部门名称',
  `inferior_department` varchar(64) DEFAULT NULL COMMENT '下级部门名称',
  `describes` varchar(256) DEFAULT NULL COMMENT '描述',
  UNIQUE KEY `key` (`department_key`),
  UNIQUE KEY `name` (`department_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- USER PROJECT RELATION TABLE
CREATE TABLE IF NOT EXISTS `ml_user_project_relation` (
  `uuid` VARCHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID唯一标识符',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `project_key` varchar(64) NOT NULL COMMENT 'protal项目的key',
  `role` varchar(64) DEFAULT NULL COMMENT '用户在项目中的角色',
  UNIQUE KEY (`username`,`project_key`),
  FOREIGN KEY (`username`) REFERENCES `ml_user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和protal项目关联关系表';

INSERT INTO `issue_field` VALUES ('1e5ca0a7-1e4e-413e-b923-6989bba68f96', 'customfield_10001', 'Epic Link', 'EpicLink');
INSERT INTO `issue_field` VALUES ('6b3460c0-cb2b-4861-ad25-1cf73938b7ed', 'customfield_10008', 'Assign', 'Assign');
INSERT INTO `issue_field` VALUES ('a2e54af5-1855-47ea-9f27-bb27b0922118', 'customfield_10004', 'Epic Color', 'EpicColor');
INSERT INTO `issue_field` VALUES ('bd783da1-b967-45f1-986e-ed5f266aba63', 'customfield_10005', 'Rank', 'Rank');
INSERT INTO `issue_field` VALUES ('e0e20a4e-5202-473a-8351-fb2523afd324', 'customfield_10003', 'Epic Name', 'EpicName');
INSERT INTO `issue_field` VALUES ('e0e20a4e-5202-473a-8351-fb2523afd326', 'customfield_10006', 'Story Points', 'StoryPoints');
INSERT INTO `issue_field` VALUES ('e0e20a4e-5202-473a-8351-fb2523afd327', 'customfield_10002', 'Epic Status', 'EpicStatus');
INSERT INTO `issue_field` VALUES ('e0e20a4e-5202-473a-8351-fb2523afd328', 'customfield_10000', 'Sprint', 'Sprint');
INSERT INTO `issue_field` VALUES ('e0e20a4e-5202-473a-8351-fb2523afd329', 'customfield_10007', 'Due Date', 'DueDate');

-- ARTIFACTORY DEPARTMENT RELATION TABLE
CREATE TABLE IF NOT EXISTS `artifactory_repo` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `department` varchar(32) NOT NULL COMMENT '所属部门',
  `repo_name` varchar(32) NOT NULL COMMENT '仓库名',
  `repo_url` varchar(32) NOT NULL COMMENT '仓库地址',
  `repo_type` varchar(10) NOT NULL COMMENT '仓库类型',
  `token` varchar(1000) NOT NULL COMMENT 'Token',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `department` (`department`,`repo_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库表';

-- CMS_APPID_PROJECT RELATION TABLE
CREATE TABLE IF NOT EXISTS `cms_appid_project_relation` (
  `uuid` varchar(36) NOT NULL COMMENT 'UUID唯一标识符',
  `appid` varchar(32) NOT NULL COMMENT 'appid',
  `project` varchar(32) NOT NULL COMMENT '项目',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`project`,`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cms和project关系表';


ALTER TABLE git_pull_requests ADD(
	git_pr_source VARCHAR(64) COMMENT "pr源分支",
	git_pr_target VARCHAR(64) COMMENT "pr目标分支"
);



