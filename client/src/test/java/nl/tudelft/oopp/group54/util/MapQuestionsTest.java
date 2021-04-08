package nl.tudelft.oopp.group54.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapQuestionsTest {

    MapQuestions mq = new MapQuestions();
    MapQuestions mq2 = new MapQuestions();
    MapQuestions mq3 = new MapQuestions();

    Map<String, Integer> innerUnansweredQuestionsIDs;
    Map<String, Integer> innerAnsweredQuestionsIDs;

    @BeforeEach
    void setUp() {

        innerAnsweredQuestionsIDs = new HashMap<>();
        innerAnsweredQuestionsIDs.put("1", 1);
        innerAnsweredQuestionsIDs.put("2", 2);

        innerUnansweredQuestionsIDs = new HashMap<>();
        innerUnansweredQuestionsIDs.put("3", 3);
        innerUnansweredQuestionsIDs.put("4", 4);

        mq = new MapQuestions();
        mq2 = new MapQuestions(innerUnansweredQuestionsIDs, innerAnsweredQuestionsIDs);
        mq3 = new MapQuestions();
        mq3.setInnerAnsweredQuestionsIDs(innerAnsweredQuestionsIDs);
        mq3.setInnerUnansweredQuestionsIDs(innerUnansweredQuestionsIDs);

    }

    @Test
    void getInnerUnansweredQuestionsIDs() {
        assertEquals(mq2.getInnerUnansweredQuestionsIDs(), innerUnansweredQuestionsIDs);
    }

    @Test
    void getInnerAnsweredQuestionsIDs() {
        assertEquals(mq2.getInnerAnsweredQuestionsIDs(), innerAnsweredQuestionsIDs);
    }

    @Test
    void addUnansweredQuestion() {

        mq.addUnansweredQuestion(null, 1);
        assertTrue(mq.getInnerUnansweredQuestionsIDs().isEmpty());

        mq.addUnansweredQuestion("1", 1);
        assertTrue(mq.getInnerUnansweredQuestionsIDs().containsKey("1"));
    }

    @Test
    void addAnsweredQuestion() {

        mq.addAnsweredQuestion(null, 1);
        assertTrue(mq.getInnerAnsweredQuestionsIDs().isEmpty());


        mq.addAnsweredQuestion("1", 1);
        assertTrue(mq.getInnerAnsweredQuestionsIDs().containsKey("1"));
    }

    @Test
    void containsUnansweredQuestion() {
        assertFalse(mq3.containsUnansweredQuestion(null));
        assertFalse(mq3.containsUnansweredQuestion("1"));
        assertFalse(mq3.containsUnansweredQuestion("2"));
        assertTrue(mq3.containsUnansweredQuestion("3"));
        assertTrue(mq3.containsUnansweredQuestion("4"));
    }

    @Test
    void containsAnsweredQuestion() {
        assertFalse(mq3.containsAnsweredQuestion(null));
        assertTrue(mq3.containsAnsweredQuestion("1"));
        assertTrue(mq3.containsAnsweredQuestion("2"));
        assertFalse(mq3.containsAnsweredQuestion("3"));
        assertFalse(mq3.containsAnsweredQuestion("4"));
    }

    @Test
    void getVoteCountUnanswered() {
        assertNull(mq3.getVoteCountUnanswered("1"));
        assertNull(mq3.getVoteCountUnanswered("2"));
        assertEquals(3, mq3.getVoteCountUnanswered("3"));
        assertEquals(4, mq3.getVoteCountUnanswered("4"));
    }

    @Test
    void getVoteCountAnswered() {
        assertEquals(1, mq3.getVoteCountAnswered("1"));
        assertEquals(2, mq3.getVoteCountAnswered("2"));
        assertNull(mq3.getVoteCountAnswered("3"));
        assertNull(mq3.getVoteCountAnswered("4"));
    }

    @Test
    void updateValue() {
        mq3.updateValue("1", 10);
        mq3.updateValue("2", 20);
        mq3.updateValue("3", 30);
        mq3.updateValue("4", 40);

        assertEquals(10, mq3.getVoteCountUnanswered("1"));
        assertEquals(20, mq3.getVoteCountUnanswered("2"));
        assertEquals(30, mq3.getVoteCountUnanswered("3"));
        assertEquals(40, mq3.getVoteCountUnanswered("4"));
    }

    @Test
    void deleteQuestion() {
        mq3.deleteQuestion("1");
        mq3.deleteQuestion("2");
        assertFalse(mq3.getInnerUnansweredQuestionsIDs().isEmpty());
        mq3.deleteQuestion("3");
        mq3.deleteQuestion("4");
        assertTrue(mq3.getInnerUnansweredQuestionsIDs().isEmpty());
    }
}