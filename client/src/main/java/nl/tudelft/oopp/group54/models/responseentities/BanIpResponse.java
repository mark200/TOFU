package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class BanIpResponse implements Serializable {
    private boolean success;
    private String message;

    public BanIpResponse() {
        this.success = false;
        this.message = "";
    }

    public BanIpResponse(boolean success, String message) {
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

    @Override
    public String toString() {
        return "BanIpResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
