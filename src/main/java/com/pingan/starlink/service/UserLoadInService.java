package com.pingan.starlink.service;

import com.pingan.starlink.mapper.MlUserMapper;
import com.pingan.starlink.model.MlUser;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserLoadInService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MlUserMapper mlUserMapper;

    public List<MlUser> loadUsers(List<LoadUser> loadUsers) {
        List<MlUser> mlUserHashList = new ArrayList<MlUser>();
        for(LoadUser loadUser : loadUsers){
            MlUser mlUser = new MlUser();
            mlUser.setDepartment("消费金融部门");
            mlUser.setRealname(loadUser.getRealname());
            mlUser.setUsername(loadUser.getUsername());
            mlUser.setEmail(generateEmail(loadUser.getUsername()));
            mlUser.setUuid(UUID.randomUUID().toString());
            try{
                mlUserMapper.insert(mlUser);
                mlUserHashList.add(mlUser);
            } catch (Exception e) {
                logger.info(String.format("insert user %s failure, error: %s.", mlUser.getUsername()), e.getMessage());
            }

        }
        return mlUserHashList;
    }

    private String generateEmail(String username) {
        return String.format("%s@pingan.com.cn", username);
    }


    @Data
    public static class LoadUser {
        private String username;
        private String realname;
    }


}
