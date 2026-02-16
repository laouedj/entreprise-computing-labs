package com.ensia.bds;

import com.ensia.bds.replication.config.AppProperties;
import com.ensia.bds.replication.infrastructure.PortUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BdsApplication {

    private static final Logger log = LoggerFactory.getLogger(BdsApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(BdsApplication.class);
        app.run(args);
    }


    @Bean
    ApplicationRunner logMode(AppProperties props) {
        return args -> {
            log.info("Replication mode: {}", props.mode());
            log.info("Peer URL: {}", props.peerUrl());
        };
    }

}
