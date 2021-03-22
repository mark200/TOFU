package nl.tudelft.oopp.group54.models.responseentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLectureResponse implements Serializable {

  private Boolean success;

  private Integer lectureId;

  private String lecturerId;
  private String studentId;
  private String moderatorId;

  private String message;

  public CreateLectureResponse() {
    this.success = false;
    this.lectureId = 0;

    this.lecturerId = "";
    this.studentId = "";
    this.moderatorId = "";

    this.message = "";
  }

  public CreateLectureResponse(Boolean success,
                               Integer lecturerId,
                               String lectureId,
                               String studentId,
                               String moderatorId,
                               String message) {
    this.success = success;
    this.lectureId = lecturerId;

    this.lecturerId = lectureId;
    this.studentId = studentId;
    this.moderatorId = moderatorId;

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

  public Integer getLectureId() {
    return lectureId;
  }

  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getModeratorId() {
    return moderatorId;
  }

  public void setModeratorId(String moderatorId) {
    this.moderatorId = moderatorId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLecturerId() {
    return lecturerId;
  }

  public void setLecturerId(String lecturerId) {
    this.lecturerId = lecturerId;
  }

  @Override
  public String toString() {
    return "CreateLectureResponse{" +
            "success=" + success +
            ", lectureId=" + lectureId +
            ", lecturerId=" + lecturerId +
            ", studentId=" + studentId +
            ", moderatorId=" + moderatorId +
            ", message='" + message + '\'' +
            '}';
  }
}
