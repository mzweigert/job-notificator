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
