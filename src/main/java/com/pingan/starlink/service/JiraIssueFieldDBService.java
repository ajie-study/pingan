package com.pingan.starlink.service;

import com.pingan.starlink.mapper.IssueFieldMapper;
import com.pingan.starlink.model.IssueField;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JiraIssueFieldDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IssueFieldMapper issueFieldMapper;

    public List<IssueField> getAll() {
        return issueFieldMapper.selectAll();
    }

    public IssueField getById(String fieldId) throws Exception {
        logger.info(String.format("select by id: %s", fieldId));
        IssueField issueField = issueFieldMapper.selectByPrimaryKey(fieldId);
        logger.info(String.format("result is: %s", JacksonUtil.bean2Json(issueField)));
        return issueField;
    }

    public int deleteById(String fieldId) {
        logger.info(String.format("delete by id: %s", fieldId));
        int res = issueFieldMapper.deleteByPrimaryKey(fieldId);
        logger.info(String.format("result is: %d", res));
        return res;
    }

    public IssueField saveIssueField(IssueField issueField) throws Exception {
        logger.info(String.format("insert issueField: %s", JacksonUtil.bean2Json(issueField)));
        issueFieldMapper.insert(issueField);
        issueField = issueFieldMapper.selectByPrimaryKey(issueField.getUuid());
        logger.info(String.format("save issueField: %s", JacksonUtil.bean2Json(issueField)));
        return issueField;
    }

    public IssueField updateIssueField(IssueField issueField) throws Exception {
        logger.info(String.format("update issueField: %s", JacksonUtil.bean2Json(issueField)));
        issueFieldMapper.updateByPrimaryKey(issueField);
        issueField = issueFieldMapper.selectByPrimaryKey(issueField.getUuid());
        logger.info(String.format("save issueField: %s", JacksonUtil.bean2Json(issueField)));
        return issueField;
    }
}



