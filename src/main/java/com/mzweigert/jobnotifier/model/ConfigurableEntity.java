package com.mzweigert.jobnotifier.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ConfigurableEntity extends IdentifiableEntity {

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
