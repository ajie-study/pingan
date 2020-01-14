package com.pingan.starlink.service;

import com.pingan.starlink.model.GitProject;
import com.pingan.starlink.util.JacksonUtil;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GitLabSyncTest {

    @Resource
    private GitlabAPIClient gitlabAPIClient;

    @Autowired
    private GitDBService gitDBService;

    @Autowired
    private GitService gitService;

    @Value("${git.url}")
    private String giturl;


    //根据组id获取项目
    @Ignore
    @Test
    public void getGitLabApi() throws Exception {
        // 获取组的所有信息
        Group group = gitlabAPIClient.getGroup(33);

        List<Branch> branches = this.gitlabAPIClient.getBranches(198);

        List<GitProject> gitProjects = gitDBService.selectProjectByGroupId(33);

        //取出组下的所有project
        List<Project> projects = group.getProjects();

        System.out.println(JacksonUtil.bean2Json(gitProjects));

        System.out.println(gitProjects.size());
    }


    //同步测试
    @Ignore
    @Test
    public void  synchronizeDBTest() throws Exception{

        //获取token  oTT4exggGCEgxGociSGW
       GitLabApi  gitLabApi = new GitLabApi(giturl, "oTT4exggGCEgxGociSGW");

        Project project = gitLabApi.getProjectApi().getProject(199);

        ArrayList<Project> projects = new ArrayList<>();

        projects.add(project);

        this.gitService.gitProjectAndBranchInit(projects);
    }


    @Ignore
    @Test
    public void getGroupId() throws Exception {

        //GitLabApi  gitLabApi = new GitLabApi(giturl, "vYSNGSzdx1gffAZ7ui-u");


        //Project project = gitLabApi.getProjectApi().getProject(201);

        //System.out.println( JacksonUtil.bean2Json(project));


        Group group = this.gitlabAPIClient.getGroup(56);

        System.out.println(JacksonUtil.bean2Json(group));
    }
}
