package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class VoteKey implements Serializable {
    @Column(name = "student_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer studentID;

    @Column(name = "lecture_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer lectureID;

    @Column(name = "question_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer questionID;

    /**
     * Empty Constructor.
     */
    public VoteKey() {

    }

    /**
     * Constructor.
     * @param studentID ID of student
     * @param lectureID ID of lecture
     * @param questionID IF of question
     */
    public VoteKey(@NotNull int studentID, @NotNull int lectureID, @NotNull int questionID) {
        this.studentID = studentID;
        this.lectureID = lectureID;
        this.questionID = questionID;
    }

    /**
     * Returns the Identification Number of the Student who made that Vote.
     * @return
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Updates the Identification Number of the Student who made that Vote.
     * @param studentID ID of new student
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Returns the Identification Number of the Lecture where the Question.
     * was asked on which the Vote was made
     * @return
     */
    public int getLectureID() {
        return lectureID;
    }

    /**
     * Updates the Identification Number of the Lecture where the Question.
     * was asked on which the Vote was made
     * @param lectureID ID of new lecture
     */
    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    /**
     * Returns the Identification Number of the Question.
     * for which the Vote was made
     * @return
     */
    public int getQuestionID() {
        return questionID;
    }

    /**
     * Updates the Identification Number of the Question.
     * for which the Vote was made
     * @param questionID ID of new question
     */
    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    /**
     * Checks if o is the same key.
     * @param o object that is compared to this
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteKey)) {
            return false;
        }

        VoteKey voteKey = (VoteKey) o;

        if (getStudentID() != voteKey.getStudentID()) {
            return false;
        }

        if (getLectureID() != voteKey.getLectureID()) {
            return false;
        }

        return getQuestionID() == voteKey.getQuestionID();
    }

    /**
     * Computes the hashcode of the Vote.
     * @return
     */
    @Override
    public int hashCode() {
        int result = getStudentID();
        result = 31 * result + getLectureID();
        result = 31 * result + getQuestionID();
        return result;
    }

    /**
     * Returns a String-based representation of the Vote.
     * @return
     */
    @Override
    public String toString() {
        return "VoteKey{"
                + "studentID = " + studentID
                + ", lectureID = " + lectureID
                + ", roleID = " + questionID
                + '}';
    }
}
