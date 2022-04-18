/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.search.aggregations;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.util.BytesRef;

import java.util.function.Supplier;

/**
 * Used to preserve contextual information during aggregation execution. It can be used by search executors and parent
 * aggregations to provide contextual information for the child aggregation during execution such as the currently executed
 * time series id or the size of the current date histogram bucket. The information provided by this class is highly contextual and
 * only valid during the {@link LeafBucketCollector#collect} call.
 */
public class AggregationExecutionContext {

    private final Supplier<BytesRef> tsidProvider;
    private final Supplier<Long> timestampProvider;
    private final LeafReaderContext leafReaderContext;

    public AggregationExecutionContext(
        LeafReaderContext leafReaderContext,
        Supplier<BytesRef> tsidProvider,
        Supplier<Long> timestampProvider
    ) {
        this.leafReaderContext = leafReaderContext;
        this.tsidProvider = tsidProvider;
        this.timestampProvider = timestampProvider;
    }

    public LeafReaderContext getLeafReaderContext() {
        return leafReaderContext;
    }

    public BytesRef getTsid() {
        return tsidProvider != null ? tsidProvider.get() : null;
    }

    public Long getTimestamp() {
        return timestampProvider.get();
    }
}
