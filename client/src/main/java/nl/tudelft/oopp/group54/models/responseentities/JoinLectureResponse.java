package nl.tudelft.oopp.group54.models.responseentities;

public class JoinLectureResponse {

    private Boolean success;
    private Long userID;
    private String userName;
    private String role;
    private String message;


    /**
     * Instantiates a new Join lecture response.
     */
    public JoinLectureResponse() {
        this.success = false;
        this.userID = 0L;
        this.userName = "";
        this.role = "";
        this.message = "";
    }

    /**
     * Instantiates a new Join lecture response.
     *
     * @param success  the success
     * @param userID   the user id
     * @param userName the user name
     * @param role     the role
     * @param message  the message
     */
    public JoinLectureResponse(Boolean success,
                               Long userID,
                               String userName,
                               String role,
                               String message) {
        this.success = success;
        this.userID = userID;
        this.userName = userName;
        this.role = role;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JoinLectureResponse{"
                + "success=" + success
                + ", userID=" + userID
                + ", userName='" + userName + '\''
                + ", role='" + role + '\''
                + ", message='" + message + '\''
                + '}';
    }
}
