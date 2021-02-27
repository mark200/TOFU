package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
public class QuestionKey implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "lecture_id", nullable = false)
    private int lecture_id;

    public QuestionKey(int id, int lecture_id) {
        this.id = id;
        this.lecture_id = lecture_id;
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
}