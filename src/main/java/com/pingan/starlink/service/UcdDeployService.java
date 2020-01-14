package com.pingan.starlink.service;

import com.pingan.starlink.dto.UcdDeployDTO;
import com.pingan.starlink.mapper.UcdDeployMapper;
import com.pingan.starlink.model.AddressInfo;
import com.pingan.starlink.model.UcdDeploy;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.UcdDeployVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UcdDeployService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UcdDeployMapper ucdDeployMapper;
    /**
     * 创建ucd部署
     * @param ucdDeployDTO
     * @return
     */
    public UcdDeployVo createRelease(UcdDeployDTO ucdDeployDTO) throws IOException {

        ucdDeployDTO.setUuid(EireneUtil.randomUUID());

        UcdDeploy ucdDeploy = ucdDeployDTO.getUcdDeploy(ucdDeployDTO);

        logger.info("insert ucdDeploy ==> {}",JacksonUtil.bean2Json(ucdDeploy));

        ucdDeployMapper.insert(ucdDeploy);

        logger.info("insert ucdDeploy ==> SUCCESS ");

        UcdDeployVo ucdDeployVo = new UcdDeployVo();

        BeanUtils.copyProperties(ucdDeploy,ucdDeployVo);

        /**
         * 将String转换成list
         */
        ucdDeployVo.setAddressInfos(JacksonUtil.json2Bean(ucdDeploy.getAddress(), List.class, AddressInfo.class));

        return ucdDeployVo;
    }

    /**
     * 通过uuid删除DB数据
     * @param uuid
     * @return
     */
    public int deleteUcdDeployByUuid(String uuid) {

        logger.info("deleteUcdDeployByUuid , uuid==> {} ", uuid);

        return ucdDeployMapper.deleteByPrimaryKey(uuid);
    }

    /**
     * 根据uuid修改DB数据
     * @param ucdDeployDTO
     * @return
     */
    public UcdDeployVo updateUcdDeploy(UcdDeployDTO ucdDeployDTO) throws IOException {

        UcdDeploy ucdDeploy = ucdDeployDTO.getUcdDeploy(ucdDeployDTO);

        logger.info("update ucdDeployByUuid ==> {}",JacksonUtil.bean2Json(ucdDeploy));

        ucdDeployMapper.updateByPrimaryKey(ucdDeploy);

        logger.info("update ucdDeployByUuid ==> SUCCESS ");

        UcdDeployVo ucdDeployVo = new UcdDeployVo();

        BeanUtils.copyProperties(ucdDeploy,ucdDeployVo);

        /**
         * 将String转换成list
         */
        ucdDeployVo.setAddressInfos(JacksonUtil.json2Bean(ucdDeploy.getAddress(), List.class, AddressInfo.class));

        return ucdDeployVo;

    }

    /**
     * 查询ucdDeploys
     * @param uuid
     * @param projectKey
     * @return
     */
    public List<UcdDeployVo> selectUcdDeploys(String uuid, String projectKey) throws IOException {

        logger.info("uuid : {} , projectKey : {}",uuid,projectKey);

        List<UcdDeployVo> ucdDeployVos = ucdDeployMapper.selectUcdDeploys(uuid,projectKey);

       return ucdDeployVos;


    }
}
