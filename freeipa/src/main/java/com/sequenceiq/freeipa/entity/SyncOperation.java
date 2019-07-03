package com.sequenceiq.freeipa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@NamedEntityGraph(name = "SyncOperation.stackSyncOperation",
        attributeNodes = @NamedAttributeNode("stackSyncOperation"))
@Entity
public class SyncOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "syncoperation_generator")
    @SequenceGenerator(name = "syncoperation_generator", sequenceName = "syncoperation_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany
    private Account account;

    private Long requested;

    private Long completed;

    // TODO figure out how to store this data
    private String template;

    // TODO make this an enum
    private String status;

    @OneToMany(mappedBy = "syncOperation")
    private Set<StackSyncOperation> stackSyncOperations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
