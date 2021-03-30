package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostPollResponse implements Serializable {
    private boolean success;
    private String message;

    public PostPollResponse() {
        this.success = false;
        this.message = "";
    }


    /**
     * initialize PostPollResponse.
     * @param success - success boolean
     * @param message - message from the server
     */
    public PostPollResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
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

    @Override
    public String toString() {
        return "BanIpResponse{"
                + "success=" + success
                + ", message='" + message + '\''
                + '}';
    }
}