package com.ensia.bds.replication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        String mode,
        String peerUrl
) {
    public AppProperties {
        if (mode == null || mode.isBlank()) mode = "AP";
        if (peerUrl == null) peerUrl = "";
    }
}