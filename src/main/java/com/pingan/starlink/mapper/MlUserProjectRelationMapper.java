package com.pingan.starlink.mapper;

import com.pingan.starlink.model.MlUserProjectRelation;
import com.pingan.starlink.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MlUserProjectRelationMapper extends BaseMapper<MlUserProjectRelation> {

   List<MlUserProjectRelation> selectByProjectKey(@Param("userName") String userName);

}