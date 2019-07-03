package com.sequenceiq.freeipa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class StackSyncOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "stacksyncoperation_generator")
    @SequenceGenerator(name = "stacksyncoperation_generator", sequenceName = "stacksyncoperation_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private SyncOperation syncOperation;

    @ManyToOne
    private Stack stack;

    private Long requested;

    private Long completed;

    // TODO make this an enum
    private String status;

    @Column(columnDefinition = "TEXT")
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SyncOperation getSyncOperation() {
        return syncOperation;
    }

    public void setSyncOperation(SyncOperation syncOperation) {
        this.syncOperation = syncOperation;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public Long getRequested() {
        return requested;
    }

    public void setRequested(Long requested) {
        this.requested = requested;
    }

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
