package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class PostPollRequest implements Serializable {
    Integer optionCount;
    String userId;
    String correctAnswer;
    String title;

    /**
     * Create a new PostPollRequest.
     * @param optionCount - option count
     * @param userId - current user id
     * @param correctAnswer - correct answer for poll.
     */
    public PostPollRequest(Integer optionCount, String userId, String correctAnswer, String title) {
        this.optionCount = optionCount;
        this.userId = userId;
        this.correctAnswer = correctAnswer;
        this.title = title;
    }

    public PostPollRequest() {
    }

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
