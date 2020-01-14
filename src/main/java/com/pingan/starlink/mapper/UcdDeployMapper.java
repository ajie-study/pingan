package com.pingan.starlink.mapper;

import com.pingan.starlink.model.UcdDeploy;
import com.pingan.starlink.vo.jira.UcdDeployVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UcdDeployMapper {

    int deleteByPrimaryKey(String uuid);

    int insert(UcdDeploy record);

    int insertSelective(UcdDeploy record);

    UcdDeploy selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(UcdDeploy record);

    int updateByPrimaryKey(UcdDeploy record);

    List<UcdDeployVo> selectUcdDeploys(@Param("uuid") String uuid, @Param("projectKey") String projectKey);
}