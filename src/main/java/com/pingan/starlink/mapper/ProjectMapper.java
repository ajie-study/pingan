package com.pingan.starlink.mapper;

import com.pingan.starlink.model.Project;
import com.pingan.starlink.util.BaseMapper;
import com.pingan.starlink.vo.jira.DepartmentCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {

    int updateProject(@Param("project") Project project);

    Project selectByProjectKey(@Param("projectKey") String projectKey);

    int deleteByProjectKey(@Param("projectKey") String projectKey);

    List<DepartmentCountVO> departmentCount();

    List<Project> selectByProjectKeyAndDepartment(@Param("projectKey")List<String> projectKey,@Param("department")List<String> department);

}