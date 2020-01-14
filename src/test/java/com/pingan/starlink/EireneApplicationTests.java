package com.pingan.starlink;

import com.pingan.starlink.dto.JiraGitprojectRelationDTO;
import com.pingan.starlink.model.JiraGitprojectRelation;
import com.pingan.starlink.service.GitService;
import com.pingan.starlink.service.GitlabAPIClient;
import com.pingan.starlink.util.JacksonUtil;
import org.gitlab4j.api.GitLabApiClient;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EireneApplicationTests {

	@Autowired
	private GitlabAPIClient gitlabAPIClient;

	@Autowired
	private GitService gitService;


	@Test
	@Ignore
	public void contextLoads() throws Exception {

		Project projectByPath = gitlabAPIClient.getProjectByPath("test1/test1son_group/test1granson_group/test1",1);

		System.out.println(JacksonUtil.bean2Json(projectByPath));
	}

	@Ignore
	@Test
	public void  setRelateGitProject() throws Exception {

		JiraGitprojectRelationDTO jiraGitprojectRelationDTO = new JiraGitprojectRelationDTO();
		jiraGitprojectRelationDTO.setGitProjectUrl("http://119.28.9.84:8880/test1/test1son_group/test1granson_group/test1.git");
		jiraGitprojectRelationDTO.setJiraProjectKey("PW");

		JiraGitprojectRelation relation = gitService.setRelateGitProject(jiraGitprojectRelationDTO);

		System.out.println(relation);
	}
}

