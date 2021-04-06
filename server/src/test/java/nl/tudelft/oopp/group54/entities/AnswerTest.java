package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnswerTest {

    static Answer answer;
    static Answer answerDuplicate;
    static Answer answer1;
    static AnswerKey key;
    static AnswerKey keyDuplicate;


    /**
     * Init.
     */
    @BeforeEach
    public void init() {
        key = new AnswerKey(123, 321);
        keyDuplicate = new AnswerKey(123, 321);
        answer = new Answer(key, "answer", 1234, 4321);
        answerDuplicate = new Answer(keyDuplicate, "answer", 1234, 4321);
        answer1 = new Answer(new AnswerKey(000, 111), "answering", 1, 2);
    }

    @Test
    public void testEquals() {
        assertEquals(answer, answerDuplicate);
        assertEquals(answer, answer);
        assertNotEquals(answer, null);
        assertNotEquals(answer, answer1);
        assertNotEquals(answer, "123");

    }

    public void testEmptyAnswerKeyNotNull() {
        AnswerKey answerKey = new AnswerKey();
        assertNotNull(answerKey);
    }

    @Test
    public void testEmptyAnswerNotNull() {
        Answer newAnswer = new Answer();
        assertNotNull(newAnswer);
    }

    @Test
    public void testAnswerKeySetId() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        answerKey.setId(3);
        assertEquals(3, answerKey.getId());
    }

    @Test
    public void testAnswerKeySetLecturerId() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        answerKey.setLecture_id(3);
        assertEquals(3, answerKey.getLecture_id());
    }

    @Test
    public void testAnswerKeyCompareTo() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        AnswerKey answerKey1 = new AnswerKey(1, 2);
        assertEquals(0, answerKey.compareTo(answerKey1));
    }

    @Test
    public void testAnswerKeyEquals() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        AnswerKey answerKey1 = new AnswerKey(1,3);
        AnswerKey answerKey2 = new AnswerKey(1, 2);
        assertEquals(answerKey, answerKey);
        assertEquals(answerKey, answerKey2);
        assertFalse(answerKey.equals(answerKey1));
        assertFalse(answerKey2.equals(null));
        assertFalse(answerKey.equals("123"));
    }

    @Test
    public void testAnswerKeyHashCode() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        assertEquals(994, answerKey.hashCode());
    }

    @Test
    public void testSetPrimaryKey() {
        AnswerKey answerKey = new AnswerKey(1, 2);
        answer.setPrimaryKey(new AnswerKey(1, 2));
        assertEquals(answerKey, answer.getPrimaryKey());
    }

    @Test
    public void testGetAnsweredText() {
        assertEquals("answer", answer.getAnswerText());
    }

    @Test
    public void testSetAnsweredText() {
        answer.setAnswerText("answered again");
        assertEquals("answered again", answer.getAnswerText());
    }

    @Test
    public void testGetQuestionId() {
        assertEquals(1234, answer.getQuestionId());
    }

    @Test
    public void testSetQuestionId() {
        answer.setQuestionId(1132);
        assertEquals(1132, answer.getQuestionId());
    }

    @Test
    public void testGetModeratorId() {
        assertEquals(4321, answer.getModeratorId());
    }

    @Test
    public void testSetModeratorId() {
        answer.setModeratorId(1132);
        assertEquals(1132, answer.getModeratorId());
    }

    @Test
    public void testAnswerHashCode() {
        assertEquals(-346811193, answer.hashCode());
    }

    @Test
    public void testAnswerToString() {
        String expected = "Answer{"
                + "primaryKey=nl.tudelft.oopp.group54.entities.AnswerKey@13e7"
                + ", answer_text='answer', question_id=1234, moderator_id=4321}";
        assertEquals(expected, answer.toString());
    }
}
