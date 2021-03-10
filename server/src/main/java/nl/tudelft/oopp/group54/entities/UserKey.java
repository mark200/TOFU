package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class UserKey implements Serializable, Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Integer id;

    @Column(name = "lecture_id")
    @NotNull
    private Integer lecture_id;

    /**
     * Empty Constructor
     */
    public UserKey() {

    }

    /**
     * Constructor
     * @param id
     * @param lecture_id
     */
    public UserKey(@NotNull int id, @NotNull int lecture_id) {
        this.id = id;
        this.lecture_id = lecture_id;
    }

    /**
     * Compares with another UserKey
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        return 0;
    }

    /**
     * Returns the Identification Number of the User
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the Identification Number of the User
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Identification Number of the Lecture that the User participates in
     * @return
     */
    public int getLecture_id() {
        return lecture_id;
    }

    /**
     * Updates the Identification Number of the Lecture that the User participates in
     * @param lecture_id
     */
    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    /**
     * Checks if o is also a UserKey and has the same simple keys
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserKey)) return false;

        UserKey userKey = (UserKey) o;

        if (getId() != userKey.getId()) return false;
        return getLecture_id() == userKey.getLecture_id();
    }

    /**
     * Computes the hashcode
     * @return
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLecture_id();
        return result;
    }

    /**
     * Returns a String-based representation
     * @return
     */
    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", lecture_id=" + lecture_id +
                '}';
    }
}
