package com.pingan.starlink.service;

import com.pingan.starlink.mapper.GitgroupDepartmentRelationMapper;
import com.pingan.starlink.model.GitgroupDepartmentRelation;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GitgroupDepartmenRelationDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GitgroupDepartmentRelationMapper gitgroupDepartmentRelationMapper;

    public int insertGitgroupDepartmentRelation(GitgroupDepartmentRelation gitgroupDepartmentRelation) throws IOException {
        gitgroupDepartmentRelation.setUuid(EireneUtil.randomUUID());
        logger.info(String.format("Insert gitgroupDepartmentRelation : %s ", JacksonUtil.bean2Json(gitgroupDepartmentRelation)));
        int insert = gitgroupDepartmentRelationMapper.insert(gitgroupDepartmentRelation);
        logger.info(String.format("Result version : %d ", insert));
        return insert;
    }

    public int deleteGitgroupDepartmentRelation(String uuid) {
        logger.info(String.format("delete gitgroupDepartmentRelation : %s ", uuid));
        int res = gitgroupDepartmentRelationMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("Result is  : %d ", res));
        return res;
    }

    public int updateGitgroupDepartmentRelation(GitgroupDepartmentRelation gitgroupDepartmentRelation) throws IOException {
        logger.info(String.format("update gitgroupDepartmentRelation : %s ", JacksonUtil.bean2Json(gitgroupDepartmentRelation)));
        int i =gitgroupDepartmentRelationMapper.updateGitgroupDev(gitgroupDepartmentRelation);
        logger.info(String.format("save gitgroupDepartmentRelation : %d ", i));
        return i;
    }

    public GitgroupDepartmentRelation getByUuid(String uuid) throws Exception {
        logger.info(String.format("select by uuid: %s", uuid));
        GitgroupDepartmentRelation gitgroupDepartmentRelation = gitgroupDepartmentRelationMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("result is: %s", JacksonUtil.bean2Json(gitgroupDepartmentRelation)));
        return gitgroupDepartmentRelation;
    }

    public List<GitgroupDepartmentRelation> queryAll() throws Exception {
        logger.info("Select GitgroupDepartmentRelation All .");
        List<GitgroupDepartmentRelation> selectAll = gitgroupDepartmentRelationMapper.selectAll();
        logger.info(String.format("Result is: %s", JacksonUtil.bean2Json(selectAll)));
        return selectAll;
    }

    public List<GitgroupDepartmentRelation> getGitgroupsByDeptName(String department) throws IOException {
        logger.info("select GitgroupDepartmentRelation by department .");
        List<GitgroupDepartmentRelation> gitgroupRelationList = gitgroupDepartmentRelationMapper.selectAllByDept(department);
        logger.info(String.format("Result is: %s", JacksonUtil.bean2Json(gitgroupRelationList)));
        return gitgroupRelationList;
    }
}
