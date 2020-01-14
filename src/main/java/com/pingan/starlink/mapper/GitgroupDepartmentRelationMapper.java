package com.pingan.starlink.mapper;

import com.pingan.starlink.model.GitgroupDepartmentRelation;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GitgroupDepartmentRelationMapper extends BaseMapper<GitgroupDepartmentRelation> {

    List<GitgroupDepartmentRelation> selectAllByDept(@Param("department") String department);

    int updateGitgroupDev(@Param("gdr") GitgroupDepartmentRelation gitgroupDepartmentRelation);

}