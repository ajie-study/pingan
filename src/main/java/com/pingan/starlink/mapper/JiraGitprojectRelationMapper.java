package com.pingan.starlink.mapper;

import com.pingan.starlink.model.JiraGitprojectRelation;
import com.pingan.starlink.util.BaseMapper;
import com.pingan.starlink.vo.jira.JiraGitprojectRelationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JiraGitprojectRelationMapper extends BaseMapper<JiraGitprojectRelation> {
    /**
     * 根据projectKey进行动态查询
     * @param projectKey
     * @return
     */
    List<JiraGitprojectRelation> selectRelationByProjectKey(@Param("projectKey") String projectKey);

    /**
     * 根据uuid查询jira和gitProject关系
     * @param uuid
     * @return
     */
    JiraGitprojectRelation selectJiraGitprojectRelationByUuid(String uuid);
}