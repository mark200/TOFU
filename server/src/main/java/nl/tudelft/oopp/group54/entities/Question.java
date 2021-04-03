package nl.tudelft.oopp.group54.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Questions")
public class Question {
    @EmbeddedId
    private QuestionKey primaryKey;

    @Column(name = "student_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer studentId;

    @Column(name = "studentIp", columnDefinition = "VARCHAR NOT NULL")
    @NotNull
    private String studentIp;

    @Column(name = "content", columnDefinition = "VARCHAR(420) NOT NULL")
    @NotNull
    private String content;

    @Column(name = "vote_counter", columnDefinition = "INT default 0")
    private Integer voteCounter = 0;

    @Column(name = "answered", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    @NotNull
    private Boolean answered = false;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIME")
    @NotNull
    private Date createdAt;

    @Column(name = "answer_text", columnDefinition = "VARCHAR(1000)")
    private String answerText = "";

    public Question() {

    }


    /**
     * Instantiates a new Question.
     *
     * @param primaryKey  the primary key
     * @param studentId   the student id
     * @param studentIp   the student ip
     * @param content     the content
     * @param voteCounter the vote counter
     * @param answered    the answered
     * @param createdAt   the created at
     */
    public Question(QuestionKey primaryKey, @NotNull Integer studentId, @NotNull String studentIp,
                    @NotNull String content, Integer voteCounter, @NotNull Boolean answered, @NotNull Date createdAt) {
        this.primaryKey = primaryKey;
        this.studentId = studentId;
        this.studentIp = studentIp;
        this.content = content;
        this.voteCounter = voteCounter;
        this.answered = answered;
        this.createdAt = createdAt;
    }


    public QuestionKey getPrimaryKey() {
        return primaryKey;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getContent() {
        return content;
    }

    public Integer getVoteCounter() {
        return voteCounter;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getStudentIp() {
        return studentIp;
    }

    public void setPrimaryKey(QuestionKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVoteCounter(Integer voteCounter) {
        this.voteCounter = voteCounter;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setStudentIp(String studentIp) {
        this.studentIp = studentIp;
    }

    /**
     * Compare two Question Objects, the idea is to compare their primary keys,
     * because in the context of this application questions with different primary keys
     * will be different questions.
     *
     * @param o Object.
     * @return true if their primaryKey is equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return getPrimaryKey().equals(question.getPrimaryKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimaryKey(), getStudentId(), getStudentIp(), getContent(),
                getVoteCounter(), getAnswered(), getCreatedAt(), getAnswerText());
    }

    @Override
    public String toString() {
        return "Question{"
                + "primaryKey=" + primaryKey
                + ", student_id=" + studentId
                + ", studentIp='" + studentIp + '\''
                + ", content='" + content + '\''
                + ", vote_counter=" + voteCounter
                + ", answered=" + answered
                + ", created_at=" + createdAt
                + ", answerText='" + answerText + '\''
                + '}';
    }
}
