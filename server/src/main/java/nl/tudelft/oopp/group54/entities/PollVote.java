package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PollVote")
public class PollVote {
    @EmbeddedId
    private PollVoteKey primaryKey;

    @Column(name = "vote", columnDefinition = "VARCHAR(15) NOT NULL")
    @NotNull
    private String vote;
    
    public PollVote() {
    }
    
    public PollVote(PollVoteKey primaryKey, String vote) {
        this.primaryKey = primaryKey;
        this.vote = vote;
    }

    public PollVoteKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PollVoteKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
    
    
}
