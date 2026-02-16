package com.ensia.bds.replication.api;

import com.ensia.bds.replication.domain.Document;
import com.ensia.bds.replication.domain.WriteResult;
import com.ensia.bds.replication.domain.repository.DocumentStore;
import com.ensia.bds.replication.infrastructure.StrategyFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final StrategyFactory factory;
    private final DocumentStore store;

    public DocumentController(StrategyFactory factory, DocumentStore store) {
        this.factory = factory;
        this.store = store;
    }

    @GetMapping("/add")
    public ResponseEntity<?> add(@RequestParam String title,
                                 @RequestParam String content) {

        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "title and content required"));
        }

        Document doc = new Document(
                UUID.randomUUID().toString(),
                title,
                content,
                Instant.now()
        );

        WriteResult result = factory.current().write(doc);
        return ResponseEntity.status(result.httpStatus()).body(result.body());
    }

    @PostMapping("/replicate")
    public ResponseEntity<?> replicate(@RequestBody Document doc) {
        if (doc.getId() == null || doc.getId().isBlank()) {
            doc.setId(UUID.randomUUID().toString());
        }
        if (doc.getCreatedAt() == null) {
            doc.setCreatedAt(Instant.now());
        }
        store.put(doc);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(store.all());
    }
}
