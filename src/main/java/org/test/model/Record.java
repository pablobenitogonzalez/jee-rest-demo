package org.test.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static org.test.utils.ApplicationStrings.CREATED;
import static org.test.utils.ApplicationStrings.LAST_UPDATE;

@Embeddable
public class Record {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = CREATED,	columnDefinition = "DATETIME", nullable = false)
    private Date created = new Date();
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = LAST_UPDATE,	columnDefinition = "DATETIME", nullable = false)
    private Date lastUpdate = new Date();
    public Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Record() {}

    public Record(Date created, Date lastUpdated) {
        this.created = created;
        this.lastUpdate = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (created != null ? !created.equals(record.created) : record.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return created != null ? created.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Record{" +
                "created=" + created +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
