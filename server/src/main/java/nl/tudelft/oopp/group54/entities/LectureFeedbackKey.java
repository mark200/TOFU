package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotNull;


@Embeddable
public class LectureFeedbackKey implements Serializable {

    @EmbeddedId
    private UserKey userKey;

    @Column(name = "lecture_feedback_code", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer lectureFeedbackCodeAsInteger;


    public LectureFeedbackKey(UserKey userKey,
                              @NotNull Integer lectureFeedbackCodeAsInteger) {
        this.userKey = userKey;
        this.lectureFeedbackCodeAsInteger = lectureFeedbackCodeAsInteger;
    }

    public LectureFeedbackKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LectureFeedbackKey that = (LectureFeedbackKey) o;
        return Objects.equals(userKey, that.userKey)
                && Objects.equals(lectureFeedbackCodeAsInteger, that.lectureFeedbackCodeAsInteger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userKey, lectureFeedbackCodeAsInteger);
    }

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public Integer getLectureFeedbackCode() {
        return lectureFeedbackCodeAsInteger;
    }

    private void setLectureFeedbackCode(Integer lectureFeedbackCodeAsInteger) {
        this.lectureFeedbackCodeAsInteger = lectureFeedbackCodeAsInteger;
    }

    public void setLectureFeedbackCode(LectureFeedbackCode lectureFeedbackCode) {
        this.setLectureFeedbackCode(lectureFeedbackCode.getValue());
    }

}
