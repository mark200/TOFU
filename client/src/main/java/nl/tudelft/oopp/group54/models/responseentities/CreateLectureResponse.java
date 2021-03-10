package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class CreateLectureResponse implements Serializable {

  private Boolean success;
  private Long lectureID;
  private Long lecturerID;
  private Long studentID;
  private Long moderatorID;
  private String message;

  public CreateLectureResponse() {
    this.success = false;
    this.lectureID = 0L;
    this.studentID = 0L;
    this.moderatorID = 0L;
    this.message = "";
  }

  public CreateLectureResponse(Boolean success,
                               Long lectureID,
                               Long studentID,
                               Long moderatorID,
                               String message) {
    this.success = success;
    this.lectureID = lectureID;
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

  public Long getLectureID() {
    return lectureID;
  }

  public void setLectureID(Long lectureID) {
    this.lectureID = lectureID;
  }

  public Long getStudentID() {
    return studentID;
  }

  public void setStudentID(Long studentID) {
    this.studentID = studentID;
  }

  public Long getModeratorID() {
    return moderatorID;
  }

  public void setModeratorID(Long moderatorID) {
    this.moderatorID = moderatorID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getLecturerID() {
    return lecturerID;
  }

  public void setLecturerID(Long lecturerID) {
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
