package com.pingan.starlink.controller;

import com.pingan.starlink.dto.UserProjectRelationDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.MlUserProjectRelation;
import com.pingan.starlink.service.UserProjectRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/eirene/rbac")
@CrossOrigin
public class UserProjectRelationController {

    @Autowired
    private UserProjectRelationService userProjectRelationService;


    @ApiOperation(value = "")
    @RequestMapping(value = "/user_project_relations", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap loadUsers(@RequestBody UserProjectRelationDTO userProjectRelationDTO) throws Exception {

        ModelMap result = new ModelMap();
        int i = userProjectRelationService.insertUserRelation(userProjectRelationDTO);
        if (i < 1) {
            throw new NotFoundException("not found in db");
        }
        result.put("msg", "增加成功!");
        result.put("result", i);

        return result;

    }

    @ApiOperation(value = "根据用户名的名称查询projectKey")
    @RequestMapping(value = "/user_project_relations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<MlUserProjectRelation> relations(@RequestParam("username") String username) throws Exception {
        List<MlUserProjectRelation> mlUserProjectRelationMappers = userProjectRelationService.selectProjectByName(username);
        return mlUserProjectRelationMappers;
    }

}
