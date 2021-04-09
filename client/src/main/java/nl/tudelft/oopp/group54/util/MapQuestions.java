package nl.tudelft.oopp.group54.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapQuestions {
    private Map<String, Integer> innerUnansweredQuestionsIDs;
    private Map<String, Integer> innerAnsweredQuestionsIDs;

    /**
     * Empty Constructor.
     */
    public MapQuestions() {
        this.innerAnsweredQuestionsIDs = new HashMap<>();
        this.innerUnansweredQuestionsIDs = new HashMap<>();
    }

    /**
     * Constructor.
     * @param innerUnansweredQuestionsIDs IDs of all unanswered questions
     * @param innerAnsweredQuestionsIDs IDs of all answered questions
     */
    public MapQuestions(Map<String,Integer> innerUnansweredQuestionsIDs, Map<String, Integer> innerAnsweredQuestionsIDs) {
        this.innerUnansweredQuestionsIDs = innerUnansweredQuestionsIDs;
        this.innerAnsweredQuestionsIDs = innerAnsweredQuestionsIDs;
    }

    public Map<String,Integer> getInnerUnansweredQuestionsIDs() {
        return innerUnansweredQuestionsIDs;
    }

    public void setInnerUnansweredQuestionsIDs(Map<String, Integer> innerUnansweredQuestionsIDs) {
        this.innerUnansweredQuestionsIDs = innerUnansweredQuestionsIDs;
    }

    public Map<String, Integer> getInnerAnsweredQuestionsIDs() {
        return innerAnsweredQuestionsIDs;
    }

    public void setInnerAnsweredQuestionsIDs(Map<String,Integer> innerAnsweredQuestionsIDs) {
        this.innerAnsweredQuestionsIDs = innerAnsweredQuestionsIDs;
    }

    /**
     * Adds ID of an unanswered question to the set.
     * @param id textual representation of an identification number
     */
    public void addUnansweredQuestion(String id, Integer voteCount) {
        if (id == null) {
            return;
        }

        this.innerUnansweredQuestionsIDs.put(id, voteCount);
    }

    /**
     * Adds ID of an answered question to the set.
     * @param id textual representation of an identification number
     */
    public void addAnsweredQuestion(String id, Integer voteCount) {
        if (id == null) {
            return;
        }

        this.innerAnsweredQuestionsIDs.put(id, voteCount);
    }

    /**
     * Checks if the ID of an unanswered question exists in the set.
     * @param id textual representation of an identification number
     * @return true/false
     */
    public boolean containsUnansweredQuestion(String id) {
        if (id == null) {
            return false;
        }

        return this.innerUnansweredQuestionsIDs.containsKey(id);
    }

    /**
     * Checks if the ID of an answered question exists in the set.
     * @param id textual representation of an identification number
     * @return true/false
     */
    public boolean containsAnsweredQuestion(String id) {
        if (id == null) {
            return false;
        }

        return this.innerAnsweredQuestionsIDs.containsKey(id);
    }

    public Integer getVoteCountUnanswered(String id) {
        return this.innerUnansweredQuestionsIDs.get(id);
    }

    public Integer getVoteCountAnswered(String id) {
        return  this.innerAnsweredQuestionsIDs.get(id);
    }

    public void updateValue(String id, Integer voteCount) {
        this.innerUnansweredQuestionsIDs.put(id, voteCount);
    }

    public void deleteQuestion(String id) {
        this.innerUnansweredQuestionsIDs.remove(id);
    }
}
