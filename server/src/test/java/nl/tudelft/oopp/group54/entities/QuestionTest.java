package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {

    static Question question;
    static Question questionDuplicate;
    static QuestionKey key;
    static QuestionKey keyDuplicate;

    /**
     * Executes before all tests.
     */
    @BeforeAll
    public static void init() {
        key = new QuestionKey(123, 321);
        keyDuplicate = new QuestionKey(123, 321);
        Date date = new Date();
        question = new Question(key, 321, "192.158.1.38", "231", 231, true, date);
        questionDuplicate = new Question(keyDuplicate, 321, "192.158.1.38", "231", 231, true, date);

    }

    @Test
    public void equalsPrimaryKeys() {
        assertEquals(question, questionDuplicate);
    }


}

