package com.pingan.starlink.mapper;

import com.pingan.starlink.model.GitGroup;
import com.pingan.starlink.util.BaseMapper;

public interface GitGroupMapper extends BaseMapper<GitGroup> {
    /**
     *
     * @param groupId
     * @return
     */
    String getTokenByGroupId(Integer groupId);
}