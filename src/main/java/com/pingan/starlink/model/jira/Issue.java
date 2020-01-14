package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class Issue implements Serializable {
    private String expand;
    private String id;
    private String self;
    private String key;
    private Fields fields;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Fields implements Serializable {
        private List<Object> fixVersions;
        private Object resolution;
        private String customfield_10105;
        private Object lastViewed;
        private Priority priority;
        private List<Object> labels;
        private Object timeestimate;
        private Object aggregatetimeoriginalestimate;
        private List<Object> versions;
        private List<Object> issuelinks;
        private Object assignee;
        private Status status;
        private List<Object> components;
        private Object aggregatetimeestimate;
        private UserAbstract creator;
        /**
         * subtasks待整理
         */
        private List<Object> subtasks;
        private UserAbstract reporter;
        private Progress aggregateprogress;
        private String customfield_10834;
        private Progress progress;
        private Votes votes;
        private Worklog worklog;
        private IssueType issuetype;
        private Object timespent;
        private ProjectAbstract project;
        private Object aggregatetimespent;
        private String customfield_10700;
        private Object resolutiondate;
        private int workratio;
        private Watches watches;
        private String created;
        private String customfield_10813;
        private String updated;
        private Object timeoriginalestimate;
        private Object description;
        private Object timetracking;
        private List<Object> attachment;
        private String customfield_10801;
        private String summary;
        private String customfield_10000;
        private Object environment;
        private Object duedate;
        private Comment comment;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Priority implements Serializable {
        private String self;
        private String iconUrl;
        private String name;
        private String id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Status implements Serializable {
        private String self;
        private String description;
        private String iconUrl;
        private String name;
        private String id;
        private StatusCategory statusCategory;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class StatusCategory implements Serializable {
        private String self;
        private String id;
        private String key;
        private String colorName;
        private String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Progress implements Serializable {
        private int progress;
        private int total;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Votes implements Serializable {
        private String self;
        private int votes;
        private boolean hasVoted;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Worklog implements Serializable {
        private int startAt;
        private int maxResults;
        private int total;
        private List<Object> worklogs;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class IssueType implements Serializable {
        private String self;
        private String id;
        private String description;
        private String iconUrl;
        private String name;
        private boolean subtask;
        private int avatarId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Watches implements Serializable {
        private String self;
        private boolean isWatching;
        private int watchCount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Comment implements Serializable {
        private List<Object> comments;
        private int maxResults;
        private int total;
        private int startAt;
    }
}
