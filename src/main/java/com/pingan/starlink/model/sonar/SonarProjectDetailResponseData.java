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
public class SonarProjectDetailResponseData {

    private Component component;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Component implements Serializable {
        private String id;
        private String key;
        private String name;
        private String description;
        private String qualifier;
        private List<Measure> measures = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Measure implements Serializable {
        private String metric;
        private String value;
        private List<Period> periods = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Period implements Serializable {
        private int index;
        private String value;
    }
}
