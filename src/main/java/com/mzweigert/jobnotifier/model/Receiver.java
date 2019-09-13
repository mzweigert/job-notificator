package com.mzweigert.jobnotifier.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Receiver extends ConfigurableEntity {

    @Column(nullable = false)
    @NotEmpty
    @Email
    private String mail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SentJobsToReceiver",
            inverseJoinColumns = {
                    @JoinColumn(name = "jobId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Job"))
            },
            joinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Receiver"))
            })
    private Set<Job> sentJobs = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
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

    public void addSentJobs(Set<Job> sentJobs) {
        this.sentJobs.addAll(sentJobs);
    }

    public String getSubscribedSourcePagesAsString() {
        return subscribedSourcePages.stream()
                .map(IdentifiableEntity::getId)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
