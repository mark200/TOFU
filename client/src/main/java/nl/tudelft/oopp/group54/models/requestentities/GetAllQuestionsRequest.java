package nl.tudelft.oopp.group54.models.requestentities;

public class GetAllQuestionsRequest extends AbstractRequest {
    private String userId;

    public GetAllQuestionsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "getAllQuestionsRequest [userId=" + userId + "]";
    }
}
