package com.mzweigert.jobnotificator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Job extends IdentifiableEntity {

    @OneToOne(optional = false)
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
}
