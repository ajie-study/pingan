package com.pingan.starlink.service;

import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.mapper.MlUserMapper;
import com.pingan.starlink.model.MlUser;
import com.pingan.starlink.vo.jira.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private MlUserMapper userMapper;

    public List<MlUser> getAll() {
        return userMapper.selectAll();
    }

    /**
     * 根据部门查询用户
     * @param department
     * @return
     */
    public List<MlUser> getUsers(String department){

        return userMapper.selectByDepartment(department);
    }

    /**
     * 新增或修改用户
     * @param user
     * @return
     * @throws InternalServerErrorException
     */
    public MlUser insertOrUpdateUser(MlUser user) throws InternalServerErrorException {

        if(user == null){
            throw new InternalServerErrorException("user data is null");
        }

        String username = user.getUsername();

        MlUser mlUser = userMapper.selectUserByUsername(username);


        if(mlUser == null){
            String uuid = UUID.randomUUID().toString();

            user.setUuid(uuid);
            userMapper.insert(user);
            return userMapper.selectUserByUuid(uuid);
        }else{
            userMapper.updateByUser(user);
        }
        return user;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    public int deleteUser(String userId) {

        return this.userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    public MlUser selectUserByName(String username) {
        return  this.userMapper.selectUserByUsername(username);
    }

    /**
     * 根据project_key查询用户
     * @param project_key
     * @return
     */
    public List<UserVO> getUsersByProjectKey(String project_key) {

        return this.userMapper.selectUsersByProjectKey(project_key);
    }

    public UserVO selectUserById(Integer id) {
        return null;
    }
}
