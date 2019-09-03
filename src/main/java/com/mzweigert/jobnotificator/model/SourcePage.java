package com.mzweigert.jobnotificator.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class SourcePage extends ConfigurableEntity {

    @NotEmpty
    @URL
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourcePage)) return false;
        SourcePage that = (SourcePage) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(additionalSelector, that.additionalSelector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, additionalSelector);
    }

    @Override
    public String toString() {
        return "SourcePage{" +
                "url='" + url + '\'' +
                ", additionalSelector='" + additionalSelector + '\'' +
                '}';
    }
}
