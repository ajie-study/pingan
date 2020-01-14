package com.pingan.starlink.model.polaris;

import lombok.Data;

@Data
public class ConfigScheme1 {

    private Long id;
    private String name;

    public ConfigScheme1() {
        super();
    }
    public ConfigScheme1(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
