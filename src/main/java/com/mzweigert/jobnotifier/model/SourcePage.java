package com.mzweigert.jobnotifier.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
public class SourcePage extends ConfigurableEntity {

    @NotEmpty
    @Column(nullable = false)
    private String description;

    @NotEmpty
    @URL
    @Column(nullable = false)
    private String url;

    private String additionalSelector;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourcePage", cascade = CascadeType.REMOVE)
    private Set<Job> jobs;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "SubscribedSourcePagesReceiver",
            joinColumns = {
                    @JoinColumn(name = "sourcePageId", referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK_SubscribedSourcePagesReceiver_SourcePage"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "receiverId", referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK_SubscribedSourcePagesReceiver_Receiver"))
            })
    private Set<Receiver> receivers;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdditionalSelector() {
        return additionalSelector;
    }

    public void setAdditionalSelector(String additionalSelector) {
        this.additionalSelector = additionalSelector;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
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
        if (o == null || getClass() != o.getClass()) return false;
        SourcePage that = (SourcePage) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(description, that.description) &&
                Objects.equals(additionalSelector, that.additionalSelector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, description, additionalSelector);
    }
}
