package com.sequenceiq.freeipa.flow.freeipa.user;

import org.apache.commons.lang3.StringUtils;

import com.sequenceiq.cloudbreak.common.event.Acceptable;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.flow.event.EventSelectorUtil;

import reactor.rx.Promise;

public class SyncEvent implements Selectable, Acceptable {
    private final String selector;

    private final Long syncId;

    private final Promise<Boolean> accepted;

    public SyncEvent(Long syncId) {
        this(null, syncId);
    }

    public SyncEvent(String selector, Long syncId) {
        this(selector, syncId, new Promise<>());
    }

    public SyncEvent(String selector, Long syncId, Promise<Boolean> accepted) {
        this.selector = selector;
        this.syncId = syncId;
        this.accepted = accepted;
    }

    @Override
    public Long getResourceId() {
        return syncId;
    }

    @Override
    public String selector() {
        return StringUtils.isNotEmpty(selector) ? selector : EventSelectorUtil.selector(getClass());
    }

    @Override
    public Promise<Boolean> accepted() {
        return accepted;
    }
}
