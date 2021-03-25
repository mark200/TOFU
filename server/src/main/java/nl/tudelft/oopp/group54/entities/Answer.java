package nl.tudelft.oopp.group54.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Answers")
public class Answer {
    @EmbeddedId
    private AnswerKey primaryKey;

    @Column(name = "answer_text", columnDefinition = "text NOT NULL")
    @NotNull
    private String answerText;

    @Column(name = "question_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer questionId;

    @Column(name = "moderator_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer moderatorId;

    public Answer() {
    }

    /**
     * Instantiates a new Answer.
     *
     * @param primaryKey   the primary key
     * @param answerText  the answer text
     * @param questionId  the question id
     * @param moderatorId the moderator id
     */
    public Answer(AnswerKey primaryKey, @NotNull String answerText, @NotNull Integer questionId, @NotNull Integer moderatorId) {
        this.primaryKey = primaryKey;
        this.answerText = answerText;
        this.questionId = questionId;
        this.moderatorId = moderatorId;
    }

    public AnswerKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AnswerKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return getPrimaryKey().equals(answer.getPrimaryKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrimaryKey(), getAnswerText(), getQuestionId(), getModeratorId());
    }

    @Override
    public String toString() {
        return "Answer{" + "primaryKey=" + primaryKey + ", answer_text='" + answerText
                + '\'' + ", question_id=" + questionId + ", moderator_id=" + moderatorId + '}';
    }
}
