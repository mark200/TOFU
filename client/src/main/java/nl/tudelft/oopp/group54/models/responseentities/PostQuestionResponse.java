package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostQuestionResponse implements Serializable {
    private boolean success;
    private String message;
    private Integer questionID;
    private String code;

    public PostQuestionResponse() {
        this.success = false;
        this.message = "";
        this.questionID = 0;
        this.code = "";
    }

    public PostQuestionResponse(boolean success, String message, Integer questionID, String code) {
        this.success = success;
        this.message = message;
        this.questionID = questionID;
        this.code = code;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PostQuestionResponse [success=" + success + ", message=" + message + "]";
    }


}
