package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Answers")
public class Answer {
    @EmbeddedId
    private AnswerKey primaryKey;

    @Column(name = "answer_text", columnDefinition = "text NOT NULL")
    @NotNull
    private String answer_text;

    @Column(name = "question_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer question_id;

    @Column(name = "moderator_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer moderator_id;

    public Answer() {}

    /**
     * Instantiates a new Answer.
     *
     * @param primaryKey   the primary key
     * @param answer_text  the answer text
     * @param question_id  the question id
     * @param moderator_id the moderator id
     */
    public Answer(AnswerKey primaryKey, @NotNull String answer_text, @NotNull Integer question_id, @NotNull Integer moderator_id) {
        this.primaryKey = primaryKey;
        this.answer_text = answer_text;
        this.question_id = question_id;
        this.moderator_id = moderator_id;
    }

    public AnswerKey getPrimaryKey() {
        return primaryKey;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public Integer getModerator_id() {
        return moderator_id;
    }

    public void setPrimaryKey(AnswerKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public void setModerator_id(Integer moderator_id) {
        this.moderator_id = moderator_id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "primaryKey=" + primaryKey +
                ", answer_text='" + answer_text + '\'' +
                ", question_id=" + question_id +
                ", moderator_id=" + moderator_id +
                '}';
    }
}
