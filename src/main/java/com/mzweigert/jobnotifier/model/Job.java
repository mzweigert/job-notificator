package com.mzweigert.jobnotifier.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Job extends IdentifiableEntity {

    @ManyToOne(optional = false)
    private SourcePage sourcePage;

    @Column(nullable = false)
    private String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "SentJobsToReceiver",
            inverseJoinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Receiver"))
            },
            joinColumns = {
                    @JoinColumn(name = "jobId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SentJobsToReceiver_Job"))
            })
    public Set<Receiver> receivers;

    public SourcePage getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(SourcePage sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Receiver> receivers) {
        this.receivers = receivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return Objects.equals(sourcePage, job.sourcePage) &&
                Objects.equals(url, job.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourcePage, url);
    }
}
