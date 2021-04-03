package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class AnswerKey implements Serializable, Comparable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private Integer id;

    @Column(name = "lecture_id", nullable = false)
    private Integer lectureID;

    public AnswerKey(Integer id, int lectureID) {
        this.id = id;
        this.lectureID = lectureID;
    }

    public AnswerKey() {

    }

    public int getId() {
        return id;
    }

    public int getLecture_id() {
        return lectureID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLecture_id(int lectureID) {
        this.lectureID = lectureID;
    }

    @Override
    public int compareTo(Object o) {
        AnswerKey that = (AnswerKey) o;
        return Integer.valueOf(id).compareTo(Integer.valueOf(that.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnswerKey answerKey = (AnswerKey) o;
        return getId() == answerKey.getId()
                && getLecture_id() == answerKey.getLecture_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLecture_id());
    }
}
