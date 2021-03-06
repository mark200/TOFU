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
@Table(name = "Questions")
public class Question {
    @EmbeddedId
    private QuestionKey primaryKey;

    @Column(name = "student_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer student_id;

    @Column(name = "content", columnDefinition = "VARCHAR(420) NOT NULL")
    @NotNull
    private String content;

    @Column(name = "vote_counter", columnDefinition = "INT default 0")
    private Integer vote_counter = 0;

    @Column(name = "answered", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    @NotNull
    private Boolean answered;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIME")
    @NotNull
    private Date created_at;

    @Column(name = "answer_text", columnDefinition = "VARCHAR(1000)")
    private String answerText;

    public Question(){

    }


    /**
     * Create a new Question instance
     * @param primaryKey
     * @param student_id
     * @param content
     * @param vote_counter
     * @param answered
     * @param created_at
     */
    public Question(QuestionKey primaryKey, @NotNull Integer student_id, @NotNull String content, Integer vote_counter, @NotNull Boolean answered, @NotNull Date created_at) {
        this.primaryKey = primaryKey;
        this.student_id = student_id;
        this.content = content;
        this.vote_counter = vote_counter;
        this.answered = answered;
        this.created_at = created_at;
    }

    public QuestionKey getPrimaryKey() {
        return primaryKey;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public String getContent() {
        return content;
    }

    public Integer getVote_counter() {
        return vote_counter;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setPrimaryKey(QuestionKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVote_counter(Integer vote_counter) {
        this.vote_counter = vote_counter;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     * Compare two Question Objects, the idea is to compare their primary keys,
     * because in the context of this application questions with different primary keys
     * will be different questions.
     * @param o Object.
     * @return true if their primaryKey is equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getPrimaryKey().equals(question.getPrimaryKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimaryKey(), getStudent_id(), getContent(), getVote_counter(), getAnswered(), getCreated_at(), getAnswerText());
    }

    @Override
    public String toString() {
        return "Question{" +
                "primaryKey=" + primaryKey +
                ", student_id=" + student_id +
                ", content='" + content + '\'' +
                ", vote_counter=" + vote_counter +
                ", answered=" + answered +
                ", created_at=" + created_at +
                '}';
    }
}
