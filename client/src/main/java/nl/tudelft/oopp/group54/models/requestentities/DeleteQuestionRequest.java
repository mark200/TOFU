package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class DeleteQuestionRequest implements Serializable {
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
