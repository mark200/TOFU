package nl.tudelft.oopp.group54.util;

import java.util.HashSet;
import java.util.Set;

public class MapQuestions {
    private Set<String> innerUnansweredQuestionsIDs;
    private Set<String> innerAnsweredQuestionsIDs;

    /**
     * Empty Constructor.
     */
    public MapQuestions() {
        this.innerAnsweredQuestionsIDs = new HashSet<>();
        this.innerUnansweredQuestionsIDs = new HashSet<>();
    }

    /**
     * Constructor.
     * @param innerUnansweredQuestionsIDs IDs of all unanswered questions
     * @param innerAnsweredQuestionsIDs IDs of all answered questions
     */
    public MapQuestions(Set<String> innerUnansweredQuestionsIDs, Set<String> innerAnsweredQuestionsIDs) {
        this.innerUnansweredQuestionsIDs = innerUnansweredQuestionsIDs;
        this.innerAnsweredQuestionsIDs = innerAnsweredQuestionsIDs;
    }

    public Set<String> getInnerUnansweredQuestionsIDs() {
        return innerUnansweredQuestionsIDs;
    }

    public void setInnerUnansweredQuestionsIDs(Set<String> innerUnansweredQuestionsIDs) {
        this.innerUnansweredQuestionsIDs = innerUnansweredQuestionsIDs;
    }

    public Set<String> getInnerAnsweredQuestionsIDs() {
        return innerAnsweredQuestionsIDs;
    }

    public void setInnerAnsweredQuestionsIDs(Set<String> innerAnsweredQuestionsIDs) {
        this.innerAnsweredQuestionsIDs = innerAnsweredQuestionsIDs;
    }

    /**
     * Adds ID of an unanswered question to the set.
     * @param id textual representation of an identification number
     */
    public void addUnansweredQuestion(String id) {
        if (id == null) {
            return;
        }

        this.innerUnansweredQuestionsIDs.add(id);
    }

    /**
     * Adds ID of an answered question to the set.
     * @param id textual representation of an identification number
     */
    public void addAnsweredQuestion(String id) {
        if (id == null) {
            return;
        }

        this.innerAnsweredQuestionsIDs.add(id);
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

        return this.innerUnansweredQuestionsIDs.contains(id);
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

        return this.innerAnsweredQuestionsIDs.contains(id);
    }
}
