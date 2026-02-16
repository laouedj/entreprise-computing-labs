package com.ensia.bds.replication.infrastructure;

import com.ensia.bds.replication.config.AppProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StrategyFactory {

    private final Map<String, WriteStrategy> strategies;
    private final AppProperties props;

    public StrategyFactory(Map<String, WriteStrategy> strategies, AppProperties props) {
        this.strategies = strategies; // keys = bean names ("AP", "CP")
        this.props = props;
    }

    public WriteStrategy current() {
        String mode = props.mode() == null ? "AP" : props.mode().toUpperCase();
        return strategies.getOrDefault(mode, strategies.get("AP"));
    }
}
