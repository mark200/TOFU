package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class EndPollResponse implements Serializable {
    private Boolean success;
    private String message;

    public EndPollResponse() {
    
    }

    public EndPollResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
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
