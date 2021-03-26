package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class LectureFeedbackResponse implements Serializable {
    private boolean success;
    private String message;
    private String code;

    /**
     * Default constructor of a LectureFeedbackResponse object, which represents the
     * response body of the POST lecture feedback request. Sets all fields to null, except
     * success. That gets set to false.
     */
    public LectureFeedbackResponse() {
        this.success = false;
        this.message = null;
        this.code = null;
    }

    /**
     * Constructor of a LectureFeedbackResponse object, which represents the
     * response body of the POST lecture feedback request.
     */
    public LectureFeedbackResponse(boolean success, String message, String code) {
        this.success = success;
        this.message = message;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
