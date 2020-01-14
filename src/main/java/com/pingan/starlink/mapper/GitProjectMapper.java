package com.pingan.starlink.mapper;

import com.pingan.starlink.model.GitProject;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GitProjectMapper extends BaseMapper<GitProject> {

    GitProject getGitProjectByUuid(String uuid);

    List<GitProject> selectByProjectIdAndGroupId(@Param("gitProjectIdList") List<Integer> gitProjectIdList, @Param("gitGroupIdList")List<Integer> gitGroupIdList);

    List<GitProject> selectCodeSpaceByProjectKey(@Param("projectKey") String projectKey);

    int updateGitproject(@Param("gp") GitProject gitProject);

    /**
     * 根据groupId进行动态查询获取gitProject
     * @param groupId
     * @return
     */
    List<GitProject> selectProjectByGroupId(@Param("groupId") Integer groupId);

    /**
     * 根据uuid修改gitPorject
     * @param gitProject
     * @return
     */
    int updateGitprojectByUUid(@Param("gp")GitProject gitProject);

    GitProject selectByProjectId(Integer gitProjectId);

    /**
     * 根据projectId查询对应的组
     * @param projectId
     * @return
     */
    Integer getGroupIdGitProjectId(Integer projectId);

}