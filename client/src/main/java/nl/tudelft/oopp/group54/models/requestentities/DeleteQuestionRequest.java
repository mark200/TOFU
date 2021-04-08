package nl.tudelft.oopp.group54.models.requestentities;

public class DeleteQuestionRequest extends AbstractRequest {
    String userId;

    public DeleteQuestionRequest(String userId) {
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
        return "DeleteQuestionRequest{"
                + "userId='" + userId + '\''
                + '}';
    }
}
