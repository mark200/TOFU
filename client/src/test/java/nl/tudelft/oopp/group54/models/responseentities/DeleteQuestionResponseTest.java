package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import nl.tudelft.oopp.group54.models.QuestionModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DeleteQuestionResponseTest {


    DeleteQuestionResponse dqr;
    DeleteQuestionResponse dqr2;

    String code = "SOMEC0DE";
    Boolean success = false;
    String message = "Some message";
    Integer questionId = 42;

    @BeforeEach
    void setUp() {

        dqr = new DeleteQuestionResponse();
        dqr2 = new DeleteQuestionResponse(success, message, questionId, code);

        dqr.setCode(code);
        dqr.setMessage(message);
        dqr.setQuestionId(questionId);
        dqr.setSuccess(success);
    }

    @Test
    void getSuccess() {
        assertEquals(success, dqr.getSuccess());
    }

    @Test
    void getMessage() {
        assertEquals(message, dqr.getMessage());
    }


    @Test
    void getQuestionId() {
        assertEquals(questionId, dqr.getQuestionId());
    }

    @Test
    void getCode() {
        assertEquals(code, dqr.getCode());
    }

    @Test
    void testEquals() {
        assertNotEquals(null, dqr);
        assertNotEquals(dqr, null);
        assertNotEquals(dqr, 1);
        assertEquals(dqr, dqr);
        assertEquals(dqr, dqr2);

        dqr2.setSuccess(!success);
        assertNotEquals(dqr, dqr2);
        dqr2.setSuccess(success);

        dqr2.setQuestionId(420);
        assertNotEquals(dqr, dqr2);
        dqr2.setQuestionId(questionId);

        dqr2.setMessage("msg");
        assertNotEquals(dqr, dqr2);
        dqr2.setMessage(message);

        dqr2.setCode("C0D3");
        assertNotEquals(dqr, dqr2);

    }

    @Test
    void testHashCode() {
        assertNotNull(dqr.hashCode());
    }
}