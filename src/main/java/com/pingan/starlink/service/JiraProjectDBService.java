package com.pingan.starlink.service;

import com.pingan.starlink.mapper.ProjectMapper;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.DepartmentCountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JiraProjectDBService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectMapper projectMapper;

    public int insertProject(Project project) throws IOException {

        logger.info(String.format("Insert Project: , %s.", JacksonUtil.bean2Json(project))); //%s 字符串类型 %d 整数类型 十进制
        int insert = projectMapper.insertSelective(project);
        logger.info(String.format("Result is: %d.", insert));

        return insert;
    }

    public int deleteProject(String projectKey) {
        logger.info(String.format("Delete Project:，%s.", projectKey));
        int deleteByPrimaryKey = projectMapper.deleteByProjectKey(projectKey);
        logger.info(String.format("Result is：%d. ", deleteByPrimaryKey));
        return deleteByPrimaryKey;
    }

    public int updateProject(Project project) throws IOException {
        logger.info(String.format("update Project：,%s", JacksonUtil.bean2Json(project)));
        int updateByPrimaryKey = projectMapper.updateProject(project);
        logger.info(String.format("Result is：%d. ", updateByPrimaryKey));
        return updateByPrimaryKey;
    }

    public int updateProjectByUuid(Project project) throws IOException {
        logger.info(String.format("update Project：,%s", JacksonUtil.bean2Json(project)));
        int updateByPrimaryKey = projectMapper.updateByPrimaryKeySelective(project);
        logger.info(String.format("Result is：%d. ", updateByPrimaryKey));
        return updateByPrimaryKey;
    }

    public Project selectProject(String projectKey) throws IOException {
        logger.info(String.format("Select Project:，%s.", projectKey));
        Project project = projectMapper.selectByProjectKey(projectKey);
        logger.info(String.format("Result is：%s", JacksonUtil.bean2Json(project)));
        return project;
    }

    public List<Project> selectProjects() throws IOException {
        logger.info("Select Project All .");
        List<Project> selectAll = projectMapper.selectAll();
        logger.info("Result is：%s,", JacksonUtil.bean2Json(selectAll));
        return selectAll;
    }

    public List<DepartmentCountVO> departmentCountVOList() throws IOException {
        logger.info("Select Project All .");
        List<DepartmentCountVO> departmentCountVOS = projectMapper.departmentCount();
        logger.info("Result is：%d,", JacksonUtil.bean2Json(departmentCountVOS));
        return departmentCountVOS;
    }

    public List<Project> selectByProjectKeyAndDepartment(List<String> projectKey, List<String> department) throws IOException {
        logger.info("Select Project ByProjectKeyAndDepartment .");
        List<Project> selectAll = projectMapper.selectByProjectKeyAndDepartment(projectKey, department);
        logger.info("Result is：%s,", JacksonUtil.bean2Json(selectAll));
        return selectAll;
    }

}
