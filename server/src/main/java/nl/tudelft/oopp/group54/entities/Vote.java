package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Votes")
public class Vote {
    @Id
    @Column(name = "student_id", columnDefinition = "INT NOT NULL")
    private int id;

    @Column(name = "lecture_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private int lectureID;

    @Column(name = "question_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private int questionID;

    @Column(name = "vote_value", columnDefinition = "INT DEFAULT 0")
    @NotNull
    private int voteValue = 0;

    /**
     * Empty constructor
     */
    public Vote() {

    }

    /**
     * Constructor
     * @param id
     * @param lectureID
     * @param questionID
     * @param voteValue
     */
    public Vote(int id, @NotNull int lectureID, @NotNull int questionID, @NotNull int voteValue) {
        this.id = id;
        this.lectureID = lectureID;
        this.questionID = questionID;
        this.voteValue = voteValue;
    }

    /**
     * Returns for Identification number of the vote
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the Identification number of the vote
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Identification number of the lecture where a vote has been added to
     * in one of the question on that particular lecture
     * @return
     */
    public int getLectureID() {
        return lectureID;
    }

    /**
     * Updates the Identification number of the lecture where a vote has been added to
     * in one of the question on that particular lecture
     * @return
     */
    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    /**
     * Returns the Identification number of the question that the Vote is assigned for
     * @return
     */
    public int getQuestionID() {
        return questionID;
    }

    /**
     * Updates the identification number of the question that the Vote is assigned for
     * @param questionID
     */
    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    /**
     * Returns the type of Vote
     * @return
     */
    public int getVoteValue() {
        return voteValue;
    }

    /**
     * Updates the type of Vote
     * @param voteValue
     */
    public void setVoteValue(int voteValue) {
        this.voteValue = voteValue;
    }

    /**
     * Cheks if o is a vote and have the same attributes
     * @param o
     * @return True/False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;

        Vote vote = (Vote) o;

        if (getId() != vote.getId()) return false;
        if (getLectureID() != vote.getLectureID()) return false;
        if (getQuestionID() != vote.getQuestionID()) return false;
        return getVoteValue() == vote.getVoteValue();
    }

    /**
     * Computes the hash code of the vote
     * @return
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLectureID();
        result = 31 * result + getQuestionID();
        result = 31 * result + getVoteValue();
        return result;
    }

    /**
     * Returns a String-based representation of the vote
     * @return
     */
    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", lectureID=" + lectureID +
                ", questionID=" + questionID +
                ", voteValue=" + voteValue +
                '}';
    }
}
