package nl.tudelft.oopp.group54.models.responseentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteQuestionResponse implements Serializable {
    private Boolean success;
    private String message;
    private Integer questionId;
    private String code;

    public DeleteQuestionResponse(Boolean success, String message, Integer questionId, String code) {
        this.success = success;
        this.message = message;
        this.questionId = questionId;
        this.code = code;
    }

    public DeleteQuestionResponse() {
        this.success = false;
        this.message = "";
        this.questionId = 0;
        this.code = "";
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteQuestionResponse that = (DeleteQuestionResponse) o;
        return getSuccess().equals(that.getSuccess()) &&
                getMessage().equals(that.getMessage()) &&
                Objects.equals(getQuestionId(), that.getQuestionId()) &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuccess(), getMessage(), getQuestionId(), getCode());
    }
}