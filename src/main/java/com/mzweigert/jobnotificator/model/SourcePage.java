package com.mzweigert.jobnotificator.model;

import javax.persistence.*;

@Entity
public class SourcePage extends ConfigurableEntity {

    @Column(nullable = false)
    private String url;

    private String contentToFind;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentToFind() {
        return contentToFind;
    }

    public void setContentToFind(String contentToFind) {
        this.contentToFind = contentToFind;
    }
}
