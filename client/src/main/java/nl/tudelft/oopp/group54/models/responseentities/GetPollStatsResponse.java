package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;
import java.util.Map;

public class GetPollStatsResponse implements Serializable {
    private Boolean success;
    private String message;
    private String correctAnswer;
    private Map<String, Integer> statsMap;
    private Integer voteCount;
    private Integer optionCount;
    
    public GetPollStatsResponse() {
    
    }

    /**
     * Constructor for GetPollStatsResponse.
     * 
     * @param success whether the request was a success
     * @param message the message
     * @param correctAnswer the correct answer
     * @param statsMap the statistics
     * @param voteCount the amount of votes
     * @param optionCount the amount of options
     */
    public GetPollStatsResponse(Boolean success, String message, String correctAnswer, Map<String, Integer> statsMap,
                                Integer voteCount, Integer optionCount) {
        this.success = success;
        this.message = message;
        this.correctAnswer = correctAnswer;
        this.statsMap = statsMap;
        this.voteCount = voteCount;
        this.optionCount = optionCount;
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Map<String, Integer> getStatsMap() {
        return statsMap;
    }

    public void setStatsMap(Map<String, Integer> statsMap) {
        this.statsMap = statsMap;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }
    
    
    
    
}
