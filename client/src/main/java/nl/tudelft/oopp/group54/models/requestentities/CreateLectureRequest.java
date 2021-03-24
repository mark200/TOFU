package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class CreateLectureRequest implements Serializable {

    private String lectureName;
    private Long startTime;

    public CreateLectureRequest() {
        this.lectureName = null;
        this.startTime = 0L;
    }

    public CreateLectureRequest(String lectureName, Long startTime) {
        this.lectureName = lectureName;
        this.startTime = startTime;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "CreateLectureRequest{"
                + "lectureName='" + lectureName + '\''
                + ", startTime=" + startTime
                + '}';
    }

}
