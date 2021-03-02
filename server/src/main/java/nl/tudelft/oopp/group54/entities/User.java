package nl.tudelft.oopp.group54.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id", columnDefinition = "serial NOT NULL")
    @NotNull
    private long id;

    @Column(name = "name", columnDefinition = "VARCHAR (40) NOT NULL")
    @NotNull
    private String name;

    @Column(name = "lecture_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private long lectureID;

    @Column(name = "IP", columnDefinition = "VARCHAR NOT NULL")
    @NotNull
    private String ipAddress;

    @Column(name = "last_question_timestamp", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIME")
    private Date lastQuestion;

    @Column(name = "Role_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private int roleID;

    public User() {

    }

    /**
     * Constructor
     * @param id
     * @param name
     * @param lectureID
     * @param ipAddress
     * @param lastQuestion
     * @param roleID
     */
    public User(long id, String name, long lectureID, String ipAddress, Date lastQuestion, int roleID) {
        this.id = id;
        this.name = name;
        this.lectureID = lectureID;
        this.ipAddress = ipAddress;
        this.lastQuestion = lastQuestion;
        this.roleID = roleID;
    }

    /**
     * Returns the Identification number of the user
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Updates the Identification number of the question
     * @param id new ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the name of the user
     * @return A String-based representation of the specific user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the user to a new one
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Identification Number of the lecture that the user is currently watching
     * @return
     */
    public long getLectureID() {
        return lectureID;
    }

    /**
     * Changes the Identification number that the user is currently watching
     * @param lectureID Identification number of a new lecture
     */
    public void setLectureID(long lectureID) {
        this.lectureID = lectureID;
    }

    /**
     * Returns the IP address of the user
     * @return
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Changes the IP address of the user
     * @param ipAddress a new IP address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the date of the last question that the User asked
     * @return
     */
    public Date getlastQuestion() {
        return lastQuestion;
    }

    /**
     * Changes the date to a new time
     * @param lastQuestion
     */
    public void setLastQuestion(Date lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    /**
     * Returns the role
     * @return
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * Changes the role
     * @param roleID
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * Checks if either o is either the same object or an instance of User and has the same attributes
     * @param o
     * @return True/False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getLectureID() != user.getLectureID()) return false;
        if (getRoleID() != user.getRoleID()) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getIpAddress().equals(user.getIpAddress())) return false;
        return lastQuestion.equals(user.lastQuestion);
    }

    /**
     * Computres the hashcode of the object
     * @return
     */
    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + (int) (getLectureID() ^ (getLectureID() >>> 32));
        result = 31 * result + getIpAddress().hashCode();
        result = 31 * result + lastQuestion.hashCode();
        result = 31 * result + getRoleID();
        return result;
    }

    /**
     * Returns a String-based representation of the User object
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lectureID=" + lectureID +
                ", ipAddress='" + ipAddress + '\'' +
                ", lastQuestion=" + lastQuestion +
                ", roleID=" + roleID +
                '}';
    }
}
