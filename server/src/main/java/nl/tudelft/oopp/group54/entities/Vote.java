package nl.tudelft.oopp.group54.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Votes")
public class Vote {
    @EmbeddedId
    private VoteKey primaryKey;

    @Column(name = "vote_value", columnDefinition = "INT DEFAULT 0")
    @NotNull
    private Integer voteValue = 0;

    /**
     * Empty constructor
     */
    public Vote() {

    }

    /**
     * Constructor
     * @param primaryKey
     * @param voteValue
     */
    public Vote(VoteKey primaryKey, @NotNull int voteValue) {
        this.primaryKey = primaryKey;
        this.voteValue = voteValue;
    }

    /**
     * Returns the primary key
     * @return
     */
    public VoteKey getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Updates the primary key
     * @param primaryKey
     */
    public void setPrimaryKey(VoteKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the type of Vote
     * @return
     */
    public int getVoteValue() {
        return voteValue;
    }

    /**
     * Updates the type of Vote
     * @param voteValue
     */
    public void setVoteValue(int voteValue) {
        this.voteValue = voteValue;
    }

    /**
     * Checks if the o is a Vote and has the same primary key
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;

        Vote vote = (Vote) o;
        return getPrimaryKey().equals(vote.getPrimaryKey());
    }

    /**
     * Computes the hashcode
     * @return
     */
    @Override
    public int hashCode() {
        int result = getPrimaryKey().hashCode();
        result = 31 * result + getVoteValue();
        return result;
    }

    /**
     * Returns a String-based representation of the vote
     * @return
     */
    @Override
    public String toString() {
        return "Vote{" +
                "primaryKey = " + primaryKey.toString() +
                ", voteValue = " + voteValue +
                '}';
    }
}
