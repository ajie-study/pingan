package com.pingan.starlink.service;

import com.pingan.starlink.mapper.CmsAppidProjectRelationMapper;
import com.pingan.starlink.model.CmsAppidProjectRelation;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CmsAppidProjectRelationService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsAppidProjectRelationMapper cmsAppidProjectRelationMapper;

    public CmsAppidProjectRelation insertCmsAndProjectRelation(CmsAppidProjectRelation cmsAppidProjectRelation) throws IOException {

        String uuid = EireneUtil.randomUUID();
        cmsAppidProjectRelation.setUuid(uuid);
        logger.info(String.format("Insert CmsAppidProjectRelation: , %s.", JacksonUtil.bean2Json(cmsAppidProjectRelation))); //%s 字符串类型 %d 整数类型 十进制
        int insert = cmsAppidProjectRelationMapper.insert(cmsAppidProjectRelation);
        logger.info(String.format("Result is: %d.", insert));
        CmsAppidProjectRelation cmsAppidProjectRelation1 = null;
        if (insert > 0) {
            cmsAppidProjectRelation1 = selectAppidProjectRelationByUuid(cmsAppidProjectRelation.getUuid());
        }

        return cmsAppidProjectRelation1;
    }

    public CmsAppidProjectRelation selectAppidProjectRelationByUuid(String uuid) throws IOException {
        logger.info(String.format("select is select AppidProjectRelation By Uuid: %s.", uuid));
        CmsAppidProjectRelation cmsAppidProjectRelation = cmsAppidProjectRelationMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("select is AppidProjectRelation By Uuid: %s.", JacksonUtil.bean2Json(cmsAppidProjectRelation)));
        return cmsAppidProjectRelation;
    }

    public List<CmsAppidProjectRelation> selectAppidProjectRelationAll() throws IOException {
        logger.info("select is select AppidProjectRelation All start");
        List<CmsAppidProjectRelation> cmsAppidProjectRelation = cmsAppidProjectRelationMapper.selectAll();
        logger.info(String.format("select is AppidProjectRelation By Uuid: %s.", JacksonUtil.bean2Json(cmsAppidProjectRelation)));
        return cmsAppidProjectRelation;
    }

    public int deleteAppidProjectRelationByUuid(String uuid) throws IOException {
        logger.info(String.format("delete is select AppidProjectRelation By Uuid: %s.", uuid));
        int cmsAppidProjectRelation = cmsAppidProjectRelationMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("delete is AppidProjectRelation By Uuid: %d.",cmsAppidProjectRelation));
        return cmsAppidProjectRelation;
    }


}
