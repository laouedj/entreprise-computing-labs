package com.ensia.bds.replication.infrastructure;

import com.ensia.bds.replication.config.AppProperties;
import com.ensia.bds.replication.domain.WriteResult;
import com.ensia.bds.replication.domain.Document;
import com.ensia.bds.replication.domain.repository.DocumentStore;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component("CP")
public class CpStrategy implements WriteStrategy {

    private final DocumentStore store;
    private final RestTemplate restTemplate;
    private final AppProperties props;

    public CpStrategy(DocumentStore store, RestTemplate restTemplate, AppProperties props) {
        this.store = store;
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public WriteResult write(Document document) {

        // CP = replication obligatoire (sinon fail)
        String peer = props.peerUrl();
        if (peer == null || peer.isBlank()) {
            return new WriteResult(Map.of("error", "peer-url required in CP mode"), 503);
        }

        try {
            ResponseEntity<Void> resp =
                    restTemplate.postForEntity(peer + "/documents/replicate", document, Void.class);

            if (!resp.getStatusCode().is2xxSuccessful()) {
                return new WriteResult(Map.of("error", "replication failed"), 503);
            }

            store.put(document);
            return new WriteResult(document, 201);

        } catch (Exception e) {
            return new WriteResult(Map.of("error", "replication failed"), 503);
        }

    }
}
