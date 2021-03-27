package nl.tudelft.oopp.group54.models.responseentities;

public class EditQuestionResponse {
    private boolean success;
    private String code;
    private String questionId;
    private String message;
    
    public EditQuestionResponse() {

    }

    public boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
