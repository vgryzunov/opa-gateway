package ru.reinform.bundleserver.config;

import lombok.Data;

@Data
public class BundleTarget {
    private String target;
    private String type;
    private String uri;
}
