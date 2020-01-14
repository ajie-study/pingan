package com.pingan.starlink.mapper;

import com.pingan.starlink.model.GitPullRequests;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GitPullRequestsMapper extends BaseMapper<GitPullRequests> {

    List<GitPullRequests> selectGitprbyId(@Param("gitProjectId") Integer gitProjectId);

    List<GitPullRequests> selectGitpr(@Param("gitProjectId") Integer gitProjectId, @Param("status") String status);

    void updateGitprStatusByUuid(@Param("uuid") String uuid, @Param("state") String state);

    int updateGitpullrequests(@Param("pr") GitPullRequests gitPullRequests);

    GitPullRequests getGitPrByPrid(@Param("gitPrId") Integer gitPrId);

}