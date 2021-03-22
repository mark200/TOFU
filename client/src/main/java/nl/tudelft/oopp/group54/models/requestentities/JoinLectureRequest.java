package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class JoinLectureRequest implements Serializable {

  private String userName;
  private Integer lectureID;
  private String userID;

  public JoinLectureRequest(String userName, Integer lectureID, String userID) {
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

  public Integer getLectureID() {
    return lectureID;
  }

  public void setLectureID(Integer lectureID) {
    this.lectureID = lectureID;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
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
