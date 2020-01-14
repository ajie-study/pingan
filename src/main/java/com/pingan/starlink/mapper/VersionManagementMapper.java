package com.pingan.starlink.mapper;

import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionManagementMapper extends BaseMapper<VersionManagement> {

    VersionManagement selectByVersionId(@Param("versionId") String versionId);

    List<VersionManagement> selectByProjectKey(@Param("projectKey") String projectKey);
}