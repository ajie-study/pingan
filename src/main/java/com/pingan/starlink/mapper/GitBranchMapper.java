package com.pingan.starlink.mapper;

import com.pingan.starlink.model.GitBranch;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GitBranchMapper extends BaseMapper<GitBranch> {

    int insertGitBranch(GitBranch gitBranch);

    List<GitBranch> selectBranchByProjectId(@Param("gitProjectId") Integer gitProjectId , @Param("branchStatus") String branchStatus);

    /**
     * 根据uuid查询Branch
     * @param uuid
     * @return
     */
    GitBranch selectBranchsByUuid(String uuid);

    /**
     * 根据uuid修改Branch
     * @param gitBranchDB
     * @return
     */
    int updateBranchsByUuid(GitBranch gitBranchDB);

    /**
     * 根据uuid修改分支状态
     * @param name
     */
    void updateBranchsStatusByName(@Param("name") String name,
                                   @Param("branchStatus") String branchStatus,
                                   @Param("projectId") Integer gitProjectId
    );

    /**
     * 根据projectId和branchName 修改 commitName 和 commitAt
     * @param projectId
     * @param branchName
     * @param commitName
     * @param commitDate
     */
    void updateBranchCommitAndTime(@Param("projectId") Integer projectId, @Param("branchName") String branchName, @Param("commitName") String commitName, @Param("commitDate") Date commitDate);
}