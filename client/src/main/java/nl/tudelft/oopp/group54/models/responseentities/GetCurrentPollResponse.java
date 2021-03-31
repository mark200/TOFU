package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class GetCurrentPollResponse implements Serializable {
    private Boolean success;
    private String message;
    private Integer optionCount;
    private Boolean closed;
    private String title;

    public GetCurrentPollResponse() {
    
    }

    /**
     * Constructor for GetCurrentPollResponse.
     * 
     * @param success whether the request was successful
     * @param message the message
     * @param optionCount the amount of options
     * @param closed whether the poll is open
     * @param title the title
     */
    public GetCurrentPollResponse(Boolean success, String message, Integer optionCount, Boolean closed, String title) {
        this.success = success;
        this.message = message;
        this.optionCount = optionCount;
        this.closed = closed;
        this.title = title;
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

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
