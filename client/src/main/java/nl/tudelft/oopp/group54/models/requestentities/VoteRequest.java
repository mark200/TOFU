package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class VoteRequest implements Serializable {
    private Integer questionId = -1;
    private boolean upvote = true;

    private String userId = "";

    public VoteRequest() {
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public VoteRequest(String userId, Integer questionId) {
        this.userId = userId;
        this.questionId = questionId;
        this.upvote = true;
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

    @Override
    public String toString() {
        return "VoteRequest [userId=" + userId + ", questionId=" + questionId + ", isUpvote=" + upvote + "]";
    }
}