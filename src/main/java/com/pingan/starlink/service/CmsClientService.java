package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.CmsHttpResult;
import com.pingan.starlink.model.CmsAppidProjectRelation;
import com.pingan.starlink.vo.jira.CmsNodeVO;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmsClientService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    @Setter
    private CmsHttpClient cmsHttpClient;

    @Autowired
    private CmsAppidProjectRelationService cmsAppidProjectRelationService;

    /**
     * 查询cms 根据appid
     *
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, List<CmsNodeVO>> getCmsByAppId(String appId) throws Exception {

        Map<String, List<CmsNodeVO>> cmsNodeVos = new HashMap<>();
        CmsHttpResult cmsHttpResult = cmsHttpClient.getCmsNodes(appId);

        Map<String, List<CmsNodeVO>> data = cmsHttpResult.getData();
        List<CmsNodeVO> cmsNodeVOS = data.get("node");

        for (CmsNodeVO cmsNodeVO : cmsNodeVOS) {
            List<CmsNodeVO> cmsNodeVOS1 = cmsNodeVos.get(cmsNodeVO.getEnv());
            if (cmsNodeVOS1 == null){
                cmsNodeVOS1  = new ArrayList<>();
                cmsNodeVos.put(cmsNodeVO.getEnv(), cmsNodeVOS1);
            }
            cmsNodeVOS1.add(cmsNodeVO);
        }
        return cmsNodeVos;
    }

    /**
     * 新增cms和项目的关系
     *
     * @param cmsAppidProjectRelation
     * @return
     * @throws IOException
     */
    public CmsAppidProjectRelation insertCmsAndProjectRelation(CmsAppidProjectRelation cmsAppidProjectRelation) throws IOException {
        CmsAppidProjectRelation cmsAppidProjectRelation1 = cmsAppidProjectRelationService.insertCmsAndProjectRelation(cmsAppidProjectRelation);
        return cmsAppidProjectRelation1;
    }

    /**
     * 查询cms和项目的关系 根据uuid
     *
     * @param uuid
     * @return
     * @throws IOException
     */
    public CmsAppidProjectRelation selectAppidProjectRelationByUuid(String uuid) throws IOException {
        CmsAppidProjectRelation cmsAppidProjectRelation = cmsAppidProjectRelationService.selectAppidProjectRelationByUuid(uuid);
        return cmsAppidProjectRelation;
    }

    /**
     * 查询所有的cms和项目的关系
     *
     * @return
     * @throws IOException
     */
    public List<CmsAppidProjectRelation> selectAppidProjectRelationAll() throws IOException {
        List<CmsAppidProjectRelation> cmsAppidProjectRelation = cmsAppidProjectRelationService.selectAppidProjectRelationAll();
        return cmsAppidProjectRelation;
    }

    /**
     * 删除cms和项目的关系 根据uuid
     *
     * @param uuid
     * @return
     * @throws IOException
     */
    public int deleteAppidProjectRelationByUuid(String uuid) throws IOException {
        int cmsAppidProjectRelation = cmsAppidProjectRelationService.deleteAppidProjectRelationByUuid(uuid);
        return cmsAppidProjectRelation;
    }


}
