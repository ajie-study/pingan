package com.pingan.starlink.mapper;

import com.pingan.starlink.model.Notic;
import com.pingan.starlink.util.BaseMapper;

import java.io.IOException;
import java.util.List;

public interface NoticMapper extends BaseMapper<Notic> {
    public List<Notic> findLatest(int num) throws IOException;
}