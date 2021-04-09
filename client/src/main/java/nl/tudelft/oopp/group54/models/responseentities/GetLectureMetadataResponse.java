package nl.tudelft.oopp.group54.models.responseentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetLectureMetadataResponse implements Serializable {
    private Boolean success;
    private String message;
    private Integer lectureId;
    private Integer people;
    private String studentJoinId;
    private String lecturerJoinId;
    private String moderatorJoinId;
    private Boolean lectureOngoing;

    /**
     * Constructor for GetLectureMetadateResponse.
     * @param success - Boolean
     * @param message - String
     * @param lectureId - Integer
     * @param people - number of people in lecture. Integer
     * @param studentJoinId - String
     * @param lecturerJoinId - String
     * @param moderatorJoinId - String
     * @param lectureOngoing - has the lecture ended. Boolean
     */
    public GetLectureMetadataResponse(Boolean success,
                                      String message,
                                      Integer lectureId,
                                      Integer people,
                                      String studentJoinId,
                                      String lecturerJoinId,
                                      String moderatorJoinId,
                                      Boolean lectureOngoing) {
        this.success = success;
        this.message = message;
        this.lectureId = lectureId;
        this.people = people;
        this.studentJoinId = studentJoinId;
        this.lecturerJoinId = lecturerJoinId;
        this.moderatorJoinId = moderatorJoinId;
        this.lectureOngoing = lectureOngoing;
    }


    /**
     * empty Constructor for GetLectureMetadateResponse.
     */
    public GetLectureMetadataResponse() {
        this.success = false;
        this.message = "";
        this.lectureId = 0;
        this.people = 0;
        this.studentJoinId = "";
        this.lecturerJoinId = "";
        this.moderatorJoinId = "";
        this.lectureOngoing = true;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLectureId() {
        return lectureId;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getStudentJoinId() {
        return studentJoinId;
    }

    public void setStudentJoinId(String studentJoinId) {
        this.studentJoinId = studentJoinId;
    }

    public String getLecturerJoinId() {
        return lecturerJoinId;
    }

    public void setLecturerJoinId(String lecturerJoinId) {
        this.lecturerJoinId = lecturerJoinId;
    }

    public String getModeratorJoinId() {
        return moderatorJoinId;
    }

    public void setModeratorJoinId(String moderatorJoinId) {
        this.moderatorJoinId = moderatorJoinId;
    }

    public Boolean getLectureOngoing() {
        return lectureOngoing;
    }

    public void setLectureOngoing(Boolean lectureOngoing) {
        this.lectureOngoing = lectureOngoing;
    }
}
