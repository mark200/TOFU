package nl.tudelft.oopp.group54.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lecture_nickname", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotNull
    private String lectureName;

    @Column(name = "start_time", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    @NotNull
    private Date startTime;

    @Column(name = "student_join_id", columnDefinition = "VARCHAR(32) NOT NULL")
    @NotNull
    private String studentJoinId;

    @Column(name = "moderator_join_id", columnDefinition = "VARCHAR(32) NOT NULL")
    @NotNull
    private String moderatorJoinId;

    @Column(name = "lecturer_join_id", columnDefinition = "VARCHAR(32) NOT NULL")
    @NotNull
    private String lecturerJoinId;

    @Column(name = "lecture_ongoing", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean lectureOngoing;


    public Lecture() {

    }

    /**
     * Constructor for Lecture Entity.
     *
     * @param id              - Integer
     * @param lectureName     - String
     * @param startTime       - Date
     * @param studentJoinId   - Integer
     * @param moderatorJoinId - Integer
     * @param lecturerJoinId  - Integer
     */
    public Lecture(Integer id, @NotNull String lectureName, @NotNull Date startTime,
                   @NotNull String studentJoinId, @NotNull String moderatorJoinId,
                   @NotNull String lecturerJoinId, Boolean lectureOngoing) {
        this.id = id;
        this.lectureName = lectureName;
        this.startTime = startTime;
        this.studentJoinId = studentJoinId;
        this.moderatorJoinId = moderatorJoinId;
        this.lecturerJoinId = lecturerJoinId;
        this.lectureOngoing = lectureOngoing;
    }


    public Boolean isLectureOngoing() {
        return lectureOngoing;
    }

    public Boolean isLectureStarted() {
        Date current = new Date();
        return !(getStartTime().compareTo(current) > 0);
    }

    public Boolean isJoinable() {
        return isLectureOngoing() && isLectureStarted();
    }

    public void setLectureOngoing(Boolean lectureOngoing) {
        this.lectureOngoing = lectureOngoing;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStudentJoinId() {
        return studentJoinId;
    }

    public void setStudentJoinId(String studentJoinId) {
        this.studentJoinId = studentJoinId;
    }

    public String getModeratorJoinId() {
        return moderatorJoinId;
    }

    public void setModeratorJoinId(String moderatorJoinId) {
        this.moderatorJoinId = moderatorJoinId;
    }

    public String getLecturerJoinId() {
        return lecturerJoinId;
    }

    public void setLecturerJoinId(String lecturerJoinId) {
        this.lecturerJoinId = lecturerJoinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lecture lecture = (Lecture) o;
        return getId().equals(lecture.getId())
                && getLectureName().equals(lecture.getLectureName())
                && getStartTime().equals(lecture.getStartTime())
                && getStudentJoinId().equals(lecture.getStudentJoinId())
                && getModeratorJoinId().equals(lecture.getModeratorJoinId())
                && getLecturerJoinId().equals(lecture.getLecturerJoinId());
    }

    /**
     * Compares two primary keys.
     * @param o the object that is going to be compared to this
     * @return
     */
    public boolean equalsPrimaryKeys(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return getId().equals(lecture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLectureName(), getStartTime(),
                getStudentJoinId(), getModeratorJoinId(), getLecturerJoinId());
    }

    @Override
    public String toString() {
        return "Lecture{"
                + "id=" + id
                + ", lectureName='" + lectureName + '\''
                + ", startTime=" + startTime
                + ", studentJoinId=" + studentJoinId
                + ", moderatorJoinId=" + moderatorJoinId
                + ", lecturerJoinId=" + lecturerJoinId
                + '}';
    }
}
