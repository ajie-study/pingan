package com.pingan.starlink.mapper;

import com.pingan.starlink.model.MlDepartment;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface MlDepartmentMapper extends BaseMapper<MlDepartment> {
    MlDepartment selectByName(@Param("departmentName") String departmentName);

    int updateByDepartmentName(@Param("index") int index, @Param("departmentName") String departmentName);

}