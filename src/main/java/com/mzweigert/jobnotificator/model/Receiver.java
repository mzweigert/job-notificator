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
			inverseJoinColumns = {
                    @JoinColumn(name = "jobId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Job"))
            },
			joinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Receiver"))
            })
    private Set<Job> sentJobs = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "SubscribedSourcePagesReceiver",
			inverseJoinColumns = {
                    @JoinColumn(name = "sourcePageId", referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK_SubscribedSourcePagesReceiver_SourcePage"))
            },
			joinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK_SubscribedSourcePagesReceiver_Receiver"))
            })
    private Set<SourcePage> subscribedSourcePages = new LinkedHashSet<>();

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Job> getSentJobs() {
        return sentJobs;
    }

    public void setSentJobs(Set<Job> sentJobs) {
        this.sentJobs = sentJobs;
    }

    public Set<SourcePage> getSubscribedSourcePages() {
        return subscribedSourcePages;
    }

    public void setSubscribedSourcePages(Set<SourcePage> subscribedSourcePages) {
        this.subscribedSourcePages = subscribedSourcePages;
    }
}
