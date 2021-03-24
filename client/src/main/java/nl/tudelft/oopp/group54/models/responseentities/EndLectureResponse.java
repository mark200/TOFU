package nl.tudelft.oopp.group54.models.responseentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndLectureResponse implements Serializable {
    private String message;
    private Boolean success;

    public EndLectureResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public EndLectureResponse() {
        this.message = "";
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "EndLectureResponse{"
                + "message='" + message + '\''
                + ", success=" + success
                + '}';
    }
}
