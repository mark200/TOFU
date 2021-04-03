package nl.tudelft.oopp.group54.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    @Column(name = "last_question_timestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIME")
    private Date lastQuestion;

    @Column(name = "Role_id", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer roleID;

    public User() {

    }


    /**
     * Instantiates a new User.
     *
     * @param primaryKey   the primary key
     * @param name         the name
     * @param ipAddress    the ip address
     * @param lastQuestion the last question
     * @param roleID       the role id
     */
    public User(@NotNull UserKey primaryKey, @NotNull String name, @NotNull String ipAddress,
                Date lastQuestion, @NotNull int roleID) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.ipAddress = ipAddress;
        this.lastQuestion = lastQuestion;
        this.roleID = roleID;
    }


    /**
     * Gets key.
     *
     * @return the key
     */
    public UserKey getKey() {
        return this.primaryKey;
    }


    /**
     * Sets key.
     *
     * @param primaryKey the primary key
     */
    public void setKey(UserKey primaryKey) {
        this.primaryKey = primaryKey;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets ip address.
     *
     * @return the ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Changes the IP address of the user.
     *
     * @param ipAddress a new IP address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the date of the last question that the User asked.
     *
     * @return
     */
    public Date getlastQuestion() {
        return lastQuestion;
    }


    /**
     * Sets last question.
     *
     * @param lastQuestion the last question
     */
    public void setLastQuestion(Date lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    /**
     * Returns the role.
     *
     * @return
     */
    public Integer getRoleID() {
        return roleID;
    }


    /**
     * Sets role id.
     *
     * @param roleID the role id
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * Checks if either o is either the same object or an instance of User and has the same attributes.
     *
     * @param o the object to check
     * @return True/False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (getRoleID() != user.getRoleID()) {
            return false;
        }
        if (!primaryKey.equals(user.primaryKey)) {
            return false;
        }
        if (!getName().equals(user.getName())) {
            return false;
        }
        if (!getIpAddress().equals(user.getIpAddress())) {
            return false;
        }
        return lastQuestion.equals(user.lastQuestion);
    }

    /**
     * Computes the hashcode.
     *
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
     * Returns a String-based representation of the User object.
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{"
                + "primaryKey=" + primaryKey
                + ", name='" + name + '\''
                + ", ipAddress='" + ipAddress + '\''
                + ", lastQuestion=" + lastQuestion
                + ", roleID=" + roleID
                + '}';
    }
}
