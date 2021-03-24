package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.group54.entities.Answer;
import nl.tudelft.oopp.group54.entities.AnswerKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class AnswerTest {

    static Answer answer;
    static Answer answerDuplicate;
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

    }

    @Test
    public void equalsPrimaryKeys() {
        assertEquals(answer, answerDuplicate);
    }

}
