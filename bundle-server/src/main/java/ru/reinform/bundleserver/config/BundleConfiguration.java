package ru.reinform.bundleserver.config;

import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BundleConfigProperties.class)
public class BundleConfiguration {
    @Getter
    private final BundleConfigProperties bundleProperties;

    public BundleConfiguration(BundleConfigProperties bundleProperties) {
        this.bundleProperties = bundleProperties;
    }

}
