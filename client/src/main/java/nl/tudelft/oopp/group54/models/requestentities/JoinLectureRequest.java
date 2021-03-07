package nl.tudelft.oopp.group54.models.requestentities;

public class JoinLectureRequest {

  private String userName;
  private Long lectureID;
  private Long userID;

  public JoinLectureRequest(String userName, Long lectureID, Long userID) {
    this.userName = userName;
    this.lectureID = lectureID;
    this.userID = userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getLectureID() {
    return lectureID;
  }

  public void setLectureID(Long lectureID) {
    this.lectureID = lectureID;
  }

  public Long getUserID() {
    return userID;
  }

  public void setUserID(Long userID) {
    this.userID = userID;
  }

  @Override
  public String toString() {
    return "JoinLectureRequest{" +
            "userName='" + userName + '\'' +
            ", lectureID=" + lectureID +
            ", userID=" + userID +
            '}';
  }
}
