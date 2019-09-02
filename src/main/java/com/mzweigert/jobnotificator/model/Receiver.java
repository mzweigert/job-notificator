package com.mzweigert.jobnotificator.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Receiver extends ConfigurableEntity {

    @Column(nullable = false)
    private String mail;

    @ManyToMany
    @JoinTable(
            name = "SentJobsToReceiver",
            joinColumns = {
                    @JoinColumn(name = "jobId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Job"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Receiver"))
            })
    private Set<Job> sentJobs = new LinkedHashSet<>();

    private Set<>

}
