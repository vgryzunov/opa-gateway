package ru.reinform.bundleserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "opa.bundle")
public class BundleConfigProperties {
    private String revision;
    private List<BundleRoot> roots;
}
