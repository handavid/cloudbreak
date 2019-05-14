package com.sequenceiq.freeipa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Usersync {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "usersync_generator")
    @SequenceGenerator(name = "usersync_generator", sequenceName = "usersync_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Stack stack;

    public Long getId() {
        return id;
    }

    public Stack getStack() {
        return stack;
    }
}
