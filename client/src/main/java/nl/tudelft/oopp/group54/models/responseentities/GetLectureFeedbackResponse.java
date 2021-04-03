package nl.tudelft.oopp.group54.models.responseentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetLectureFeedbackResponse implements Serializable {
    private Boolean success;
    private String message;
    private HashMap<String, Integer> lectureFeedbackMap;

    /**
     * Create a response body to a GET lecture feedback request.
     * @param success Whether the request was successful
     * @param message If the request was not successful, what might the reason be for failure.
     * @param lectureFeedbackMap The vote count of specific feedback
     */
    public GetLectureFeedbackResponse(Boolean success, String message, HashMap<String, Integer> lectureFeedbackMap) {
        this.success = success;
        this.message = message;
        this.lectureFeedbackMap = lectureFeedbackMap;
    }

    /**
     * Create a response body to a GET lecture feedback request.
     * Initializes success as false, and everything else as nul.
     */
    public GetLectureFeedbackResponse() {
        this.success = false;
        this.message = null;
        this.lectureFeedbackMap = null;
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

    public HashMap<String, Integer> getLectureFeedbackMap() {
        return lectureFeedbackMap;
    }

    public void setLectureFeedbackMap(HashMap<String, Integer> lectureFeedbackMap) {
        this.lectureFeedbackMap = lectureFeedbackMap;
    }

    @Override
    public String toString() {
        return String.format(
                "GetLectureFeedbackResponse{success=%s, message='%s', lectureFeedbackMap=%s}",
                success,
                message,
                lectureFeedbackMap
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetLectureFeedbackResponse that = (GetLectureFeedbackResponse) o;

        return Objects.equals(success, that.success)
                && Objects.equals(message, that.message)
                &&  Objects.equals(lectureFeedbackMap, that.lectureFeedbackMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, lectureFeedbackMap);
    }
}
