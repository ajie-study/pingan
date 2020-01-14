package com.pingan.starlink.controller;

import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.MlDepartment;
import com.pingan.starlink.service.DepartmentService;
import com.pingan.starlink.vo.jira.DepartmentVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/eirene/rbac")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "新增部门", notes = "")
    @RequestMapping(value = "/departments", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public MlDepartment insert(@RequestBody MlDepartment mlDepartment) throws Exception {

        MlDepartment mlDepartment1 = departmentService.insertDepartment(mlDepartment);

        return mlDepartment1;

    }

    @ApiOperation(value = "删除部门", notes = "")
    @RequestMapping(value = "/departments/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap delete(@PathVariable("uuid") String uuid) throws Exception {
        ModelMap result = new ModelMap();
        int deleteDepartment = departmentService.deleteDepartment(uuid);

        if (deleteDepartment < 1) {
            throw new NotFoundException("not found in db");
        }

        result.put("msg", "删除成功!");
        result.put("result", deleteDepartment);

        return result;

    }

    @ApiOperation(value = "修改部门", notes = "")
    @RequestMapping(value = "/departments/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public MlDepartment updateDepartment(@PathVariable("uuid") String uuid, @RequestBody MlDepartment mlDepartment) throws Exception {

        MlDepartment mlDepartment1 = departmentService.updateDepartment(uuid, mlDepartment);

        return mlDepartment1;

    }

    @ApiOperation(value = "查询部门", notes = "查询全部")
    @RequestMapping(value = "/departments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<MlDepartment> selectAll(@RequestParam(value = "departmentName", required = false) String departmentName) throws Exception {

        List<MlDepartment> mlDepartment1 = departmentService.selectAll(departmentName);

        return mlDepartment1;

    }

    @ApiOperation(value = "查询部门的key", notes = "根据部门名称查询部门的key")
    @RequestMapping(value = "/generateProjectKey", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public DepartmentVO generateDepartmentKey(@RequestParam("departmentName") String departmentName) throws Exception {

        DepartmentVO departmentVO = new DepartmentVO();
        MlDepartment mlDepartment1 = departmentService.selectByName(departmentName);
        if (mlDepartment1 != null) {
            String departmentKey = mlDepartment1.getDepartmentKey();
            String dKey = departmentKey + mlDepartment1.getNextProjectIndex() + "";
            departmentVO.setProjectKey(dKey);
        } else {
            throw new NotFoundException("找不到此projectKey");
        }
        return departmentVO;

    }

}
