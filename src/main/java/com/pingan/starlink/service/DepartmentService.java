package com.pingan.starlink.service;

import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.mapper.MlDepartmentMapper;
import com.pingan.starlink.model.MlDepartment;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MlDepartmentMapper departmentMapper;

    public MlDepartment insertDepartment(MlDepartment mlDepartment) throws IOException {

        String uuid = EireneUtil.randomUUID();
        mlDepartment.setUuid(uuid);
        logger.info(String.format("Insert Project: , %s.", JacksonUtil.bean2Json(mlDepartment))); //%s 字符串类型 %d 整数类型 十进制
        int insert = departmentMapper.insert(mlDepartment);
        logger.info(String.format("Result is: %d.", insert));

        MlDepartment mlDepartment1 = null;
        if (insert > 0) {
            mlDepartment1 = selectByUuid(mlDepartment.getUuid());
        }

        return mlDepartment1;
    }

    public int deleteDepartment(String uuid) throws IOException {

        logger.info(String.format("Delete Project: , %s.", uuid)); //%s 字符串类型 %d 整数类型 十进制
        int deleteByPrimaryKey = departmentMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("Result is: %d.", deleteByPrimaryKey));

        return deleteByPrimaryKey;
    }

    public MlDepartment updateDepartment(String uuid,MlDepartment mlDepartment) throws Exception {
        MlDepartment mlDepartment1 = null;

        if(mlDepartment.getUuid() == null){
            mlDepartment.setUuid(uuid);
        }
        if (uuid != null && ! uuid .equals(mlDepartment.getUuid())){
            throw new InternalServerErrorException("两个uuid不一致");
        }

        logger.info(String.format("Update Project: , %s.", JacksonUtil.bean2Json(mlDepartment))); //%s 字符串类型 %d 整数类型 十进制
        int updateDepartment = departmentMapper.updateByPrimaryKeySelective(mlDepartment);
        logger.info(String.format("Result is: %d.", updateDepartment));

        if (updateDepartment > 0) {
            mlDepartment1 = selectByUuid(mlDepartment.getUuid());
        }
        return mlDepartment1;
    }

    public MlDepartment selectByUuid(String uuid) throws IOException {

        logger.info(String.format("Select Project: , %s.", uuid)); //%s 字符串类型 %d 整数类型 十进制
        MlDepartment mlDepartment = departmentMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("Result is: %s.", JacksonUtil.bean2Json(mlDepartment)));

        return mlDepartment;
    }

    public MlDepartment selectByName(String name) throws IOException {

        logger.info(String.format("Select Project: , %s.", name)); //%s 字符串类型 %d 整数类型 十进制
        MlDepartment mlDepartment = departmentMapper.selectByName(name);
        logger.info(String.format("Result is: %s.", JacksonUtil.bean2Json(mlDepartment)));

        return mlDepartment;
    }

    public List<MlDepartment> selectAll(String departmentName) throws IOException {

        logger.info(String.format("Select Project: , %s.", departmentName)); //%s 字符串类型 %d 整数类型 十进制
        List<MlDepartment> mlDepartments = new ArrayList<>();
        if (departmentName != null){
            MlDepartment mlDepartment = departmentMapper.selectByName(departmentName);
            mlDepartments.add(mlDepartment);
            return mlDepartments;
        }
        List<MlDepartment> mlDepartment = departmentMapper.selectAll();
        logger.info(String.format("Result is: %s.", JacksonUtil.bean2Json(mlDepartment)));

        return mlDepartment;
    }

    public String generateProjectKeyAndUpdate(String departmentName) throws Exception{

        MlDepartment mlDepartment = selectByName(departmentName);
        int index = mlDepartment.getNextProjectIndex();
        String projectKey = mlDepartment.getDepartmentKey() + index;
        departmentMapper.updateByDepartmentName(++index,departmentName);

        return projectKey;
    }
}
