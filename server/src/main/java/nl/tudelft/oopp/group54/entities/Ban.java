package nl.tudelft.oopp.group54.entities;

import java.util.Date;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Bans")
public class Ban {
    @EmbeddedId
    private BanKey primaryKey;

    public Ban() {

    }

    /**
     * Instantiates a new Ban.
     *
     * @param primaryKey the primary key
     */
    public Ban(BanKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public BanKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(BanKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ban ban = (Ban) o;
        return getPrimaryKey().equals(ban.getPrimaryKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimaryKey());
    }

    @Override
    public String toString() {
        return "Ban{"
                + "primaryKey=" + primaryKey
                + '}';
    }
}
