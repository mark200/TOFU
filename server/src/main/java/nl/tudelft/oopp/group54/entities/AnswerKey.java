package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AnswerKey implements Serializable, Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lecture_id", nullable = false)
    private Integer lecture_id;

    public AnswerKey(int id, int lecture_id) {
        this.id = id;
        this.lecture_id = lecture_id;
    }

    public AnswerKey(){}

    public int getId() {
        return id;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    @Override
    public int compareTo(Object o) {
        AnswerKey that = (AnswerKey) o;
        return Integer.valueOf(id).compareTo(Integer.valueOf(that.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerKey answerKey = (AnswerKey) o;
        return getId() == answerKey.getId() &&
                getLecture_id() == answerKey.getLecture_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLecture_id());
    }
}
