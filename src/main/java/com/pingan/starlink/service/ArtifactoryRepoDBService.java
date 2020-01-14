package com.pingan.starlink.service;

import com.pingan.starlink.mapper.ArtifactoryRepoMapper;
import com.pingan.starlink.model.ArtifactoryRepo;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ArtifactoryRepoDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArtifactoryRepoMapper artifactoryRepoMapper;

    public List<ArtifactoryRepo> getAllRepos(String department) throws Exception {
        Example example = new Example(ArtifactoryRepo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("department", department);
        List<ArtifactoryRepo> artifactoryRepos = artifactoryRepoMapper.selectByExample(example);
        return artifactoryRepos;
    }

    public ArtifactoryRepo getArtifactoryRepo(String uuid) throws Exception {
        logger.info(String.format("query by id: %s", uuid));
        ArtifactoryRepo artifactoryRepo = artifactoryRepoMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("result is: %s", JacksonUtil.bean2Json(artifactoryRepo)));
        return artifactoryRepo;
    }

    public int deleteById(String uuid) throws Exception {
        logger.info(String.format("delete by id: %s", uuid));
        int res = artifactoryRepoMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("result is: %d", res));
        return res;
    }

    public ArtifactoryRepo saveArtifactoryRepo(ArtifactoryRepo artifactoryRepo) throws Exception {
        logger.info(String.format("insert artifactoryRepo: %s", JacksonUtil.bean2Json(artifactoryRepo)));
        artifactoryRepoMapper.insert(artifactoryRepo);
        artifactoryRepo = artifactoryRepoMapper.selectByPrimaryKey(artifactoryRepo.getUuid());
        logger.info(String.format("save artifactoryRepo: %s", JacksonUtil.bean2Json(artifactoryRepo)));
        return artifactoryRepo;
    }

    public ArtifactoryRepo updateArtifactoryRepo(ArtifactoryRepo artifactoryRepo) throws Exception {
        logger.info(String.format("update artifactoryRepo: %s", JacksonUtil.bean2Json(artifactoryRepo)));
        artifactoryRepoMapper.updateByPrimaryKey(artifactoryRepo);
        artifactoryRepo = artifactoryRepoMapper.selectByPrimaryKey(artifactoryRepo.getUuid());
        logger.info(String.format("save artifactoryRepo: %s", JacksonUtil.bean2Json(artifactoryRepo)));
        return artifactoryRepo;
    }
}
