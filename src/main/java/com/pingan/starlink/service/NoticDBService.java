package com.pingan.starlink.service;

import com.pingan.starlink.mapper.NoticMapper;
import com.pingan.starlink.model.Notic;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class NoticDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NoticMapper noticMapper;

    public List<Notic> getAll() {
        return noticMapper.selectAll();
    }

    public List<Notic> findLatest(int num) throws IOException {
        return noticMapper.findLatest(num);
    }

    public Notic getByUUID(String uuid) throws Exception {
        logger.info(String.format("select by uuid: %s.", uuid));
        Notic notic = noticMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("result is: %s", JacksonUtil.bean2Json(notic)));
        return notic;
    }

    public int deleteByUUID(String uuid) {
        logger.info(String.format("delete by uuid: %s", uuid));
        int res = noticMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("result is: %d", res));
        return res;
    }

    public Notic createNotic(Notic notic) throws Exception {
        notic.setUuid(EireneUtil.randomUUID());
        notic.setCreatedAt(new Date());
        logger.info(String.format("insert notic: %s", JacksonUtil.bean2Json(notic)));
        noticMapper.insert(notic);
        return notic;
    }

    public Notic updateNotic(Notic notic) throws Exception {
        notic.setCreatedAt(new Date());
        logger.info(String.format("update notic: %s", JacksonUtil.bean2Json(notic)));
        int res = noticMapper.updateByPrimaryKey(notic);
        // 这里当UUID不存在时，尽管没有实际更新效果，但视为更新成功
        return notic;
    }
}



