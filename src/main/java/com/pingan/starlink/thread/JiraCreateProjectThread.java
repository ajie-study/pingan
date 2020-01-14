package com.pingan.starlink.thread;

import com.pingan.starlink.dto.ProjectCreateDataDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.jira.ProjectCreateResponseData;
import com.pingan.starlink.model.jira.ProjectDetail;
import com.pingan.starlink.service.JiraClientService;
import com.pingan.starlink.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;

public class JiraCreateProjectThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private JiraClientService jiraClientService;

    private ProjectCreateDataDTO projectCreateDataDTO;

    public void setProjectCreateDataDTO(ProjectCreateDataDTO projectCreateDataDTO) {
        this.projectCreateDataDTO = projectCreateDataDTO;
    }

//    public JiraCreateProjectThread(){}

    public JiraCreateProjectThread(JiraClientService jiraClientService) {
        this.jiraClientService = jiraClientService;
    }

    private void setStatus(String projectKey, String status) throws Exception {
        Project project = new Project();
        project.setProjectKey(projectKey);
        project.setProjectStatus(status);
        jiraClientService.updateProject(project);
        return;
    }

    @Override
    public void run() {
        ProjectCreateResponseData project1 = null;
        int retryTimes = 3;
        for (int i = 0; i < retryTimes; i++) {
            try {
                //向JIRA上添加项目
                project1 = jiraClientService.getJiraHttpClient().createProject(projectCreateDataDTO.projectCreateData());
                //修改状态
                setStatus(project1.getKey(), ConstantUtil.READY);
                return;
            } catch (SocketTimeoutException e) {
                //如果是自定义模板,在超时的情况下,等创建成功后,追加关联自定义模板
                if (!ConstantUtil.projectTemplates.contains(projectCreateDataDTO.getProjectTemplateKey())) {
                    for (int j = 0; j < 24; j++) {
                        try {
                            //如果创建project超时,最多等待5s*24=2min
                            Thread.sleep(5000);
                            ProjectDetail project = jiraClientService.getJiraHttpClient().getProject(projectCreateDataDTO.getProjectKey());
                            //关联模板
                            jiraClientService.getJiraHttpClient().associationProjectSchemes(projectCreateDataDTO.getProjectKey(), projectCreateDataDTO.getProjectTemplateKey());
                            setStatus(project1.getKey(), ConstantUtil.READY);
                        } catch (NotFoundException e1) {
                            logger.info("read time out not found project continue");
                            continue;
                        } catch (Exception e2) {
                            logger.info("read time out other error break");
                            e2.printStackTrace();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("create project failed");
                e.printStackTrace();
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                logger.info("Thread sleep error .30s ");
                e.printStackTrace();
            }

            //get project use Key

        }
        //修改projectStatus状态
        try {
            setStatus(projectCreateDataDTO.getProjectKey(), ConstantUtil.FAILED);
        } catch (Exception e) {
            logger.info("update projectStatus failed");
            e.printStackTrace();
        }
    }
}
