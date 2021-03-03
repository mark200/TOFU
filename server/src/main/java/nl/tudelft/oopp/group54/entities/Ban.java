package nl.tudelft.oopp.group54.entities;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Bans")
public class Ban {
    @EmbeddedId
    private BanKey primaryKey;

    public Ban() {}

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
    public String toString() {
        return "Ban{" +
                "primaryKey=" + primaryKey +
                '}';
    }
}
