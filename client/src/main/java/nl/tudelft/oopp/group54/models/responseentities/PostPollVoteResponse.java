package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostPollVoteResponse implements Serializable {
    private Boolean success;
    private String message;

    public PostPollVoteResponse() {
    
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
