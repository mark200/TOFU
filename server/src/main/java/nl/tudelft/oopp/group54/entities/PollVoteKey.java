package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class PollVoteKey implements Serializable {
    @Column(name = "pollId")
    @NotNull
    private Integer pollId;
    
    @Column(name = "lectureId")
    @NotNull
    private Integer lectureId;
    
    @Column(name = "userId")
    @NotNull
    private Integer userId;
    
    public PollVoteKey() {
    
    }
    
    /**
     * Makes the key.
     * 
     * @param pollId the poll id
     * @param lectureId the lecture id
     * @param userId the user id
     */
    public PollVoteKey(Integer pollId, Integer lectureId, Integer userId) {
        this.pollId = pollId;
        this.lectureId = lectureId;
        this.userId = userId;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public Integer getLectureId() {
        return lectureId;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    
    
}
