package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Embeddable
public class UserKey implements Serializable, Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Integer id;

    @Column(name = "lecture_id")
    @NotNull
    private Integer lectureID;

    /**
     * Empty Constructor.
     */
    public UserKey() {

    }

    /**
     * Constructor.
     * @param id ID of user
     * @param lectureID ID of lecture that the user is watching
     */
    public UserKey(@NotNull int id, @NotNull int lectureID) {
        this.id = id;
        this.lectureID = lectureID;
    }

    /**
     * Compares with another UserKey.
     * @param o another object
     * @return
     */
    @Override
    public int compareTo(Object o) {
        return 0;
    }

    /**
     * Returns the Identification Number of the User.
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * Updates the Identification Number of the User.
     * @param id new identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Identification Number of the Lecture that the User participates in.
     * @return
     */
    public int getLectureID() {
        return lectureID;
    }

    /**
     * Updates the Identification Number of the Lecture that the User participates in.
     * @param lectureID new identifier of lecture
     */
    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    /**
     * Checks if o is also a UserKey and has the same simple keys.
     * @param o the object this is going to be compared to
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof UserKey)) {
            return false;
        }

        UserKey userKey = (UserKey) o;

        if (getId() != userKey.getId()) {
            return false;
        }

        return getLectureID() == userKey.getLectureID();
    }

    /**
     * Computes the hashcode.
     * @return
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLectureID();
        return result;
    }

    /**
     * Returns a String-based representation.
     * @return
     */
    @Override
    public String toString() {
        return "UserKey{"
                + "id=" + id
                + ", lecture_id=" + lectureID
                + '}';
    }
}
