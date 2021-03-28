package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.group54.entities.Answer;
import nl.tudelft.oopp.group54.entities.AnswerKey;
import org.junit.jupiter.api.BeforeAll;
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
    @BeforeAll
    public static void init() {
        key = new AnswerKey(123, 321);
        keyDuplicate = new AnswerKey(123, 321);
        answer = new Answer(key, "answer", 1234, 4321);
        answerDuplicate = new Answer(keyDuplicate, "answer", 1234, 4321);
        answer1 = new Answer(new AnswerKey(000, 111), "answering", 1, 2);

    }

    @Test
    public void equalsPrimaryKeys() {
        assertEquals(answer, answerDuplicate);
    }

    @Test
    public void equalsSameObject() {
        assertEquals(answer, answer);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(answer, null);
    }

    @Test
    public void notEquals() {
        assertNotEquals(answer, answer1);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(answer, "123");
    }



}
