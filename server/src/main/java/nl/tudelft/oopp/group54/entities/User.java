package nl.tudelft.oopp.group54.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "User")
public class User {
    @EmbeddedId
    private UserKey primaryKey;

    @Column(name = "name", columnDefinition = "VARCHAR (40) NOT NULL")
    @NotNull
    private String name;

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
     * @param primaryKey
     * @param name
     * @param ipAddress
     * @param lastQuestion
     * @param roleID
     */
    public User(@NotNull UserKey primaryKey, @NotNull String name, @NotNull String ipAddress, Date lastQuestion, @NotNull int roleID) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.ipAddress = ipAddress;
        this.lastQuestion = lastQuestion;
        this.roleID = roleID;
    }

    /**
     * Updates the Identification number and the Lecture ID of the question
     * @return
     */
    public UserKey getKey() {
        return this.primaryKey;
    }

    /**
     * Updates the Identification number and the Lecture ID of the question
     * @param primaryKey
     */
    public void setKey(UserKey primaryKey) {
        this.primaryKey = primaryKey;
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

        if (getRoleID() != user.getRoleID()) return false;
        if (!primaryKey.equals(user.primaryKey)) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getIpAddress().equals(user.getIpAddress())) return false;
        return lastQuestion.equals(user.lastQuestion);
    }

    /**
     * Computes the hashcode
     * @return
     */
    @Override
    public int hashCode() {
        int result = primaryKey.hashCode();
        result = 31 * result + getName().hashCode();
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
                "primaryKey=" + primaryKey +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", lastQuestion=" + lastQuestion +
                ", roleID=" + roleID +
                '}';
    }
}
