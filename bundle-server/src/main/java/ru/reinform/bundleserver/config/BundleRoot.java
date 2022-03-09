package ru.reinform.bundleserver.config;

import lombok.Data;
import java.util.List;

@Data
public class BundleRoot {
    private String name;
    private List<BundleTarget> targets;
}
