package com.ensia.bds.replication.infrastructure;

import com.ensia.bds.replication.config.AppProperties;
import com.ensia.bds.replication.domain.WriteResult;
import com.ensia.bds.replication.domain.Document;
import com.ensia.bds.replication.domain.repository.DocumentStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("AP")
public class ApStrategy implements WriteStrategy {

    private static final Logger log = LoggerFactory.getLogger(ApStrategy.class);

    private final DocumentStore store;
    private final RestTemplate restTemplate;
    private final AppProperties props;

    public ApStrategy(DocumentStore store, RestTemplate restTemplate, AppProperties props) {
        this.store = store;
        this.restTemplate = restTemplate;
        this.props = props;
    }

    @Override
    public WriteResult write(Document document) {
        // AP = on écrit local quoi qu’il arrive, replication best-effort
        store.put(document);

        String peer = props.peerUrl();
        if (peer != null && !peer.isBlank()) {
            try {
                restTemplate.postForEntity(peer + "/documents/replicate", document, Void.class);
            } catch (Exception ignored) {
                log.warn("Replication attempt failed. peerUrl={}, documentId={}, error={}",
                        props.peerUrl(),
                        document.getId(),
                        ignored.getMessage(),
                        ignored
                );
            }
        }
        return new WriteResult(document, 201);
    }
}
