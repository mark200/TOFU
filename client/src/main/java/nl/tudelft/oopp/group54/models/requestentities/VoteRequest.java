package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class VoteRequest implements Serializable {
    private String userId;
    private Integer questionId;
    private boolean isUpvote;

    public VoteRequest() {
    }

    public VoteRequest(String userId, Integer questionId) {
        this.userId = userId;
        this.questionId = questionId;
        this.isUpvote = true;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}