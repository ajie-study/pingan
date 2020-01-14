package com.pingan.starlink.model.git;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitPullrequestsCreateData {

    private Integer projectId;    //整数(ID)、字符串(路径)或项目实例形式的项目
    private String sourceBranch;       //源分支 必需
    private String targetBranch;       //目标分支，必需

    private String title;              //合并请求的标题，required
    private String description;        //合并请求的描述
    private Integer assigneeId;        //Assignee用户ID，可选

    private Integer targetProjectId;   //目标项目的ID，可选
    private String[] labels;           //MR的标签，可选
    private Integer milestoneId;       //里程碑的ID，可选

    private Boolean removeSourceBranch;//指示合并请求在合并时是否应删除源分支的标志，可选
    private Boolean squash;            //合并时将提交压缩为单个提交，可选

}
