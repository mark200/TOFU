package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;


public class QuestionKey implements Serializable, Comparable {
	
    
    private Integer id;
    private Integer lecture_id;

    public QuestionKey(Integer id, Integer lecture_id) {
        this.id = id;
        this.lecture_id = lecture_id;
    }

    public QuestionKey() {

    }

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

    //TODO: implement this compare correctly.
    @Override
    public int compareTo(Object o) {
        QuestionKey that = (QuestionKey) o;
        return Integer.valueOf(id).compareTo(Integer.valueOf(that.getId()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionKey that = (QuestionKey) o;
        return getId() == that.getId() &&
                getLecture_id() == that.getLecture_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLecture_id());
    }
}