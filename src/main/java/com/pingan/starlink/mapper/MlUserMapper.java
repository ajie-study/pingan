package com.pingan.starlink.mapper;


import com.pingan.starlink.model.MlUser;
import com.pingan.starlink.util.BaseMapper;
import com.pingan.starlink.vo.jira.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MlUserMapper extends BaseMapper<MlUser> {

    List<MlUser> selectByDepartment(@Param("department") String department);

    int updateByUser(MlUser user);

    MlUser selectUserByUsername(String username);

    MlUser selectUserByUuid(String uuid);

    List<UserVO> selectUsersByProjectKey(@Param("projectKey") String project_key);
}