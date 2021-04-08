package nl.tudelft.oopp.group54.models.requestentities;

public class LectureFeedbackRequest extends AbstractRequest {

    private Long userId;
    private Integer lectureFeedbackCode;

    public LectureFeedbackRequest() {
        this.userId = 0L;
        this.lectureFeedbackCode = -1;
    }

    public LectureFeedbackRequest(Long userId, Integer lectureFeedbackCode) {
        this.userId = userId;
        this.lectureFeedbackCode = lectureFeedbackCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLectureFeedbackCode() {
        return lectureFeedbackCode;
    }

    public void setLectureFeedbackCode(Integer lectureFeedbackCode) {
        this.lectureFeedbackCode = lectureFeedbackCode;
    }

    @Override
    public String toString() {
        return "LectureFeedbackRequest{" + "userId=" + userId + ", lectureFeedbackCode=" + lectureFeedbackCode + '}';
    }
}
