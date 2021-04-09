package nl.tudelft.oopp.group54.models.requestentities;

public class PostPollVoteRequest extends AbstractRequest {
    private String vote;
    private String userId;
    
    public PostPollVoteRequest(String vote, String userId) {
        this.vote = vote;
        this.userId = userId;
    }
    
    public PostPollVoteRequest() {
    
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
