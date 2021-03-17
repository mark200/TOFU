package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class CreateLectureResponse implements Serializable {

  private Boolean success;

  private Integer lectureID;

  private String lecturerID;
  private String studentID;
  private String moderatorID;

  private String message;

  public CreateLectureResponse() {
    this.success = false;
    this.lectureID = 0;

    this.lecturerID = "";
    this.studentID = "";
    this.moderatorID = "";

    this.message = "";
  }

  public CreateLectureResponse(Boolean success,
                               Integer lecturerID,
                               String lectureID,
                               String studentID,
                               String moderatorID,
                               String message) {
    this.success = success;
    this.lectureID = lecturerID;

    this.lecturerID = lectureID;
    this.studentID = studentID;
    this.moderatorID = moderatorID;

    this.message = message;
  }

  public Boolean getSuccess() {
    if (this.success == null) {
      return false;
    }
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Integer getLectureID() {
    return lectureID;
  }

  public void setLectureID(Integer lectureID) {
    this.lectureID = lectureID;
  }

  public String getStudentID() {
    return studentID;
  }

  public void setStudentID(String studentID) {
    this.studentID = studentID;
  }

  public String getModeratorID() {
    return moderatorID;
  }

  public void setModeratorID(String moderatorID) {
    this.moderatorID = moderatorID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLecturerID() {
    return lecturerID;
  }

  public void setLecturerID(String lecturerID) {
    this.lecturerID = lecturerID;
  }

  @Override
  public String toString() {
    return "CreateLectureResponse{" +
            "success=" + success +
            ", lectureID=" + lectureID +
            ", lecturerID=" + lecturerID +
            ", studentID=" + studentID +
            ", moderatorID=" + moderatorID +
            ", message='" + message + '\'' +
            '}';
  }
}
