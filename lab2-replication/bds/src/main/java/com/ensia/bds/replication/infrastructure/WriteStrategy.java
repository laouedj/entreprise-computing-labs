package com.ensia.bds.replication.infrastructure;

import com.ensia.bds.replication.domain.WriteResult;
import com.ensia.bds.replication.domain.Document;

public interface WriteStrategy {
    WriteResult write(Document document);

}
