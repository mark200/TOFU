package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class VoteResponse implements Serializable {
    private boolean success;
    private String message;

    public VoteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
