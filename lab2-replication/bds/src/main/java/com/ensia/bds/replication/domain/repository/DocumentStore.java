package com.ensia.bds.replication.domain.repository;

import com.ensia.bds.replication.domain.Document;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DocumentStore {

    private final Map<String, Document> documents = new ConcurrentHashMap<>();

    public void put(Document doc) {
        documents.put(doc.getId(), doc);
    }

    public Collection<Document> all() {
        return documents.values();
    }

    public int size() {
        return documents.size();
    }
}
