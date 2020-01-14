package com.pingan.starlink.service;

import com.pingan.starlink.dto.UserProjectRelationDTO;
import com.pingan.starlink.dto.UserRolesDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.mapper.MlUserMapper;
import com.pingan.starlink.mapper.MlUserProjectRelationMapper;
import com.pingan.starlink.model.MlUser;
import com.pingan.starlink.model.MlUserProjectRelation;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserProjectRelationService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MlUserProjectRelationMapper mlUserProjectRelationMapper;
    @Autowired
    private JiraProjectDBService jiraProjectDBService;
    @Autowired
    private MlUserMapper userMapper;

    public int insertUserRelation(UserProjectRelationDTO userProjectRelationDTO) throws Exception {
        String projectKey1 = userProjectRelationDTO.getProjectKey();
        Project project = jiraProjectDBService.selectProject(projectKey1);
        if (project == null){
            logger.info(" 'create userProjectRelease' not found jira project ");
            throw new NotFoundException("找不到jira的项目");
        }
        if (project.getProjectStatus().equals(ConstantUtil.FAILED) || project.getProjectStatus().equals(ConstantUtil.CREATING)){
            logger.info(" this status don't create version ");
            throw new NotFoundException("此状态的项目不能创建版本");
        }
        List<UserRolesDTO> users = userProjectRelationDTO.getUsers();
        for (UserRolesDTO userRolesDTO:users){
            String username = userRolesDTO.getUsername();
            MlUser mlUser = userMapper.selectUserByUsername(username);
            if (mlUser == null){
                logger.info(" This user does not exist ");
                throw new NotFoundException("此用户不存在");
            }
        }
        MlUserProjectRelation mlUserProjectRelation = new MlUserProjectRelation();
        String projectKey = userProjectRelationDTO.getProjectKey();
        mlUserProjectRelation.setProjectKey(projectKey);
        List<UserRolesDTO> roles = userProjectRelationDTO.getUsers();
        int insert = 0;
        for (UserRolesDTO userRolesDTO:roles){
            mlUserProjectRelation.setUsername(userRolesDTO.getUsername());
            mlUserProjectRelation.setRole(userRolesDTO.getRole());
            String uuid = EireneUtil.randomUUID();
            mlUserProjectRelation.setUuid(uuid);
            logger.info(String.format("Insert Project: , %s.", JacksonUtil.bean2Json(mlUserProjectRelation))); //%s 字符串类型 %d 整数类型 十进制
            insert = mlUserProjectRelationMapper.insertSelective(mlUserProjectRelation);
            logger.info(String.format("Result is: %d.", insert));
        }
//        MlUserProjectRelation mlUserProjectRelation1 = null;
//        if (insert > 0) {
//            mlUserProjectRelation1 = selectByUuid(mlDepartment.getUuid());
//        }

        return insert;
    }

    public List<MlUserProjectRelation> selectProjectByName(String username) throws Exception {
        logger.info(String.format("Insert Project: , %s.", username)); //%s 字符串类型 %d 整数类型 十进制
        List<MlUserProjectRelation> mlUserProjectRelationMappers = mlUserProjectRelationMapper.selectByProjectKey(username);
        logger.info(String.format("Result is: %s.",  JacksonUtil.bean2Json(mlUserProjectRelationMappers)));
        return mlUserProjectRelationMappers;
    }


}
