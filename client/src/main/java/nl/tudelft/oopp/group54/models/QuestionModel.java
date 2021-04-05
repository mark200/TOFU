package nl.tudelft.oopp.group54.models;

import java.io.Serializable;
import java.util.Date;

public class QuestionModel implements Serializable {
    private String userId;
    private String questionId;
    private String userIp;
    private String userName;
    private String questionText;
    private int score;
    private boolean answered;
    private String answerText;
    private Date createdAt;

    private QuestionModel() {

    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        questionText = questionText.trim();
        String s = "Q: " + questionText + "\n";
        if (answerText != null && !answerText.equals("")) {
            s += "A: " + answerText + "\n";
        } else {
            s += "A: No answer for this question\n";
        }
        
        s += "created at " + createdAt.toString();

        return s;

    }
}
