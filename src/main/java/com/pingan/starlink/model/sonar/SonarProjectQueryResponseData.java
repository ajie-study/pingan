package com.pingan.starlink.model.sonar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class SonarProjectQueryResponseData {

    private Page paging;
    private List<Component> components = new ArrayList<>();

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Page implements Serializable {
        private Long pageIndex;
        private Long pageSize;
        private Long total;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Component implements Serializable {
        private String organization;
        private String id;
        private String key;
        private String name;
        private String qualifier;
        private String visibility;
        private String lastAnalysisDate;
    }

}
