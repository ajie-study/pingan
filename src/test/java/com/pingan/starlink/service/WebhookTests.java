package com.pingan.starlink.service;

import com.pingan.starlink.mapper.MlUserMapper;
import com.pingan.starlink.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebhookTests {

    @Resource
    private GitDBService gitDBService;
    @Resource
    private MlUserMapper userMapper;
    @Test
    public void updateBranch(){
        gitDBService.updateBranchCommitAndTime(
                184,"testonebranch2","hongjie",new Date());
    }


    @Test
    @Ignore
    public void getUserId(){

       User user =  User.builder().username("zhangsan")
                .password("123456")
                .sex('男')
                .address("上海市").build();

        int res= userMapper.insert(user);

        System.out.println("影响行数：" + res);

        Integer id = user.getId();

        System.out.println("自增id：" + res);


    }
}
