package com.ensia.bds.replication.domain;

import java.time.Instant;

public class Document {

        private String id;
        private String title;
        private String content;
        private Instant createdAt;

        public Document() {}

        public Document(String id, String title, String content, Instant createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
        }

        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public Instant getCreatedAt() { return createdAt; }

        public void setId(String id) { this.id = id; }
        public void setTitle(String title) { this.title = title; }
        public void setContent(String content) { this.content = content; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
