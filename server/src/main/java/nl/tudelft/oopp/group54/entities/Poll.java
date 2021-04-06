package nl.tudelft.oopp.group54.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Poll")
public class Poll {
    @EmbeddedId
    private PollKey primaryKey;

    @Column(name = "title", columnDefinition = "VARCHAR(420) NOT NULL")
    @NotNull
    private String title;

    @Column(name = "closed", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    @NotNull
    private Boolean closed = false;

    @Column(name = "option_count", columnDefinition = "INTEGER NOT NULL")
    @NotNull
    private Integer optionCount;

    @Column(name = "correct_choice", columnDefinition = "VARCHAR(15) NOT NULL")
    @NotNull
    private String correctChoice = "No Answer";

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIME")
    @NotNull
    private Date createdAt;

    /**
     * Empty constructor.
     */
    public Poll() {

    }

    /**
     * Initialize a new Poll object.
     * @param primaryKey - the primary key
     * @param title - the title
     * @param closed - boolean if closed or not
     * @param correctChoice - the correct choice
     * @param createdAt - timestamp of creation
     */
    public Poll(PollKey primaryKey, @NotNull String title, @NotNull Boolean closed,
                @NotNull Integer optionCount, @NotNull String correctChoice, @NotNull Date createdAt) {
        this.primaryKey = primaryKey;
        this.title = title;
        this.closed = closed;
        this.optionCount = optionCount;
        this.correctChoice = correctChoice;
        this.createdAt = createdAt;
    }


    public PollKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PollKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
    
    public void setOptionCount(Integer count) {
        this.optionCount = count;
    }
    
    public Integer getOptionCount() {
        return optionCount;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
