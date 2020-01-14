package com.pingan.starlink.controller;

import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.model.MlUser;
import com.pingan.starlink.service.UserLoadInService;
import com.pingan.starlink.service.UserService;
import com.pingan.starlink.vo.jira.UserVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;

@RestController
@RequestMapping("/v1/eirene/rbac")
@CrossOrigin
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserLoadInService userLoadInService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "导入用户到消费金融部门")
    @RequestMapping(value = "/load_users", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<MlUser> loadUsers(@RequestBody List<UserLoadInService.LoadUser> loadUsers) throws Exception {

        List<MlUser> mlUsers = userLoadInService.loadUsers(loadUsers);
        return mlUsers;
    }

    //users/{username}
    @ApiOperation("根据username查询单个用户")
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public MlUser queryUserByUsername(@PathVariable("username") String username) {
        return this.userService.selectUserByName(username);
    }

    //GET /users?department(requeir false)=
    @ApiOperation(value = "查询用户", notes = "查询用户时，可以通过部门作为查询条件")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<MlUser> queryUsers(@RequestParam(value = "department", required = false) String department) {
        return this.userService.getUsers(department);
    }


    @ApiOperation(value = "查询用户", notes = "根据项目key查询用户")
    @RequestMapping(value = "/project_users", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<UserVO> getUsersByProjectKey(@RequestParam(value = "project_key") String project_key) {
        return this.userService.getUsersByProjectKey(project_key);
    }


    //PUT /users  insertorupdate  xxx=xxx xxx=xxx
    @ApiOperation(value = "新增或修改用户", notes = "当用户不存在时，新增用户，存在时则修改用户")
    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public MlUser insertOrUpdateUser(@RequestBody MlUser user) throws InternalServerErrorException {

        return this.userService.insertOrUpdateUser(user);
    }


    //DELETE /users/{user_uuid}
    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/users/{user_uuid}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteUsers(@PathVariable("user_uuid") String userUUID) {
        int num = this.userService.deleteUser(userUUID);
        ModelMap result = new ModelMap();
        if (num <= 0) {
            result.put("msg", "删除失败");
        } else {
            result.put("msg", "删除成功!");
        }
        return result;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserVO> selectUserById(@PathVariable("id") Integer id) {

        UserVO userVO = null;

        try {
            if (id.longValue() < 1) {
                //错误的请求 400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            userVO = this.userService.selectUserById(id);

            if (userVO == null) {
                //资源不存在，状态响应码：404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //资源存在，返回200
            return ResponseEntity.ok(userVO);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询用户出现异常，用户id==> {}" , id);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
