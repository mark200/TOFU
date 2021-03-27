package nl.tudelft.oopp.group54.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This object represents the feedback that a user can give on a specific lecture.
 * The recognized lectureFeedbackCodes are as follows:
 * 1. Lecture is going too fast.
 * 2. Lecture is going too slow.
 */
@Entity
@Table(name = "LectureFeedback")
public class LectureFeedback {

    @EmbeddedId
    private UserKey userKey;

    @Column(name = "lecture_feedback_code", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer lectureFeedbackCodeAsInteger;


    public LectureFeedback(UserKey userKey,
                              @NotNull Integer lectureFeedbackCodeAsInteger) {
        this.userKey = userKey;
        this.lectureFeedbackCodeAsInteger = lectureFeedbackCodeAsInteger;
    }

    public LectureFeedback() {

    }

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public Integer getLectureFeedbackCodeAsInteger() {
        return lectureFeedbackCodeAsInteger;
    }

    public void setLectureFeedbackCodeAsInteger(Integer lectureFeedbackCodeAsInteger) {
        this.lectureFeedbackCodeAsInteger = lectureFeedbackCodeAsInteger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LectureFeedback that = (LectureFeedback) o;
        return Objects.equals(userKey, that.userKey)
                && Objects.equals(lectureFeedbackCodeAsInteger, that.lectureFeedbackCodeAsInteger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userKey, lectureFeedbackCodeAsInteger);
    }

    @Override
    public String toString() {
        return String.format(
                "LectureFeedback{userKey=%s, lectureFeedbackCodeAsInteger=%d}",
                userKey,
                lectureFeedbackCodeAsInteger
        );
    }
}


