package com.pingan.starlink.service;

import com.pingan.starlink.exception.NotFoundException;
import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

/**
 * GitLabApi工厂类
 */
@Service
public class GitLabApiFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Hashtable<Integer,GitLabApi>  groupMap = new Hashtable<>();

    private Hashtable<Integer,GitLabApi>  projectMap = new Hashtable<>();

    @Value("${git.url}")
    private String giturl;

    @Autowired
    private GitDBService gitDBService;

    /**
     * 根据gitGroupId获取
     * @param groupId
     * @return
     */
    public GitLabApi getGitLabApiByGroupId(Integer groupId) throws NotFoundException {

        GitLabApi gitLabApi = groupMap.get(groupId);

        if(gitLabApi == null){
           //查询数据库
           String token = gitDBService.getTokenByGroupId(groupId);

           if(token == null){
               logger.info("getTokenByGroupId is null,groupId = {}",groupId);
               throw new NotFoundException("select token failed");
           }
           //创建对象
            gitLabApi = new GitLabApi(giturl, token);
            groupMap.put(groupId,gitLabApi);
        }
        return gitLabApi;
    }

    /**
     * 根据gitProjectId获取
     * @param projectId
     * @return
     */
    public GitLabApi getGitLabApiByGitProjectId(Integer projectId) throws NotFoundException {

        GitLabApi gitLabApi = projectMap.get(projectId);

        if(gitLabApi == null){
            Integer groupId = gitDBService.getGroupIdGitProjectId(projectId);
            gitLabApi = getGitLabApiByGroupId(groupId);
            projectMap.put(projectId,gitLabApi);
        }
        return gitLabApi;
    }

}
