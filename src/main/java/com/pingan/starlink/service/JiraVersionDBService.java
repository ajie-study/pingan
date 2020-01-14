package com.pingan.starlink.service;

import com.pingan.starlink.mapper.VersionManagementMapper;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class JiraVersionDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VersionManagementMapper versionManagementMapper;

    public int createVersion(VersionManagement versionManagement) throws IOException {
        logger.info(String.format("Insert version : %s ", JacksonUtil.bean2Json(versionManagement)));
        int insert = versionManagementMapper.insertSelective(versionManagement);
        logger.info(String.format("Result version : %d ", insert));
        return insert;
    }

    public int deleteVersion(String uuid) {
        logger.info(String.format("Insert version : %s ", uuid));
        int deleteByPrimaryKey = versionManagementMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("Result version : %d ", deleteByPrimaryKey));
        return deleteByPrimaryKey;
    }

    public VersionManagement selectVersionById(String versionId) throws IOException {
        logger.info(String.format("Select version : %s ", versionId));
        VersionManagement versionManagement = versionManagementMapper.selectByVersionId(versionId);
        logger.info(String.format("Result version : %s ", JacksonUtil.bean2Json(versionManagement)));
        return versionManagement;
    }

    public int updateVersion(VersionManagement versionManagement) throws IOException {
        logger.info(String.format("Select version : %s ", JacksonUtil.bean2Json(versionManagement)));
        int updateByPrimaryKey = versionManagementMapper.updateByPrimaryKeySelective(versionManagement);
        logger.info(String.format("Result version : %d ", updateByPrimaryKey));
        return updateByPrimaryKey;
    }

    public VersionManagement selectProjectVersion(String uuid) throws IOException {
        logger.info(String.format("Select version : %s ", uuid));
        VersionManagement versionManagement = versionManagementMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("Result version : %s ", JacksonUtil.bean2Json(versionManagement)));
        return versionManagement;
    }

    public List<VersionManagement> selectVersionAll() throws IOException {
        logger.info("Select versionAll start .");
        List<VersionManagement> versionManagements = versionManagementMapper.selectAll();
        logger.info(String.format("Result version : %s ", JacksonUtil.bean2Json(versionManagements)));
        return versionManagements;
    }

    public List<VersionManagement> selectVersionByProjectKey(String projectKey) throws IOException {
        logger.info(String.format("Select version By ProjectKey: %s ", projectKey));
        List<VersionManagement> versionManagements = versionManagementMapper.selectByProjectKey(projectKey);
        logger.info(String.format("Result version : %s ", JacksonUtil.bean2Json(versionManagements)));
        return versionManagements;
    }


}
