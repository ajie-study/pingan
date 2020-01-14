package com.pingan.starlink.model.jfrog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ArtifactsQueryResponseData {

    private List<Results> results = new ArrayList();
    private Range range;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Results implements Serializable {
        private String repo;
        private String path;
        private String name;
        private String type;
        private long size;
        private Date created;
        private String created_by;
        private Date modified;
        private String modified_by;
        private Date updated;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Range implements Serializable {
        private int start_pos;
        private int end_pos;
        private int total;
    }

}
