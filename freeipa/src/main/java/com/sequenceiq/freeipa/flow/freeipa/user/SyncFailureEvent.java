package com.sequenceiq.freeipa.flow.freeipa.user;

import reactor.rx.Promise;

public class SyncFailureEvent extends SyncEvent {

    private final Exception exception;

    public SyncFailureEvent(Long syncId, Exception exception) {
        this(null, syncId, exception);
    }

    public SyncFailureEvent(String selector, Long syncId, Exception exception) {
        this(selector, syncId, new Promise<>(), exception);
    }

    public SyncFailureEvent(String selector, Long syncId, Promise<Boolean> accepted, Exception exception) {
        super(selector, syncId, accepted);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
