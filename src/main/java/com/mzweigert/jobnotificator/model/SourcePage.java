package com.mzweigert.jobnotificator.model;

import javax.persistence.*;

@Entity
public class SourcePage extends ConfigurableEntity {

    @Column(nullable = false)
    private String url;

    private String additionalSelector;

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
}
