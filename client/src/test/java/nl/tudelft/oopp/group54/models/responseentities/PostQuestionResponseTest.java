package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostQuestionResponseTest {
    PostQuestionResponse response;
    PostQuestionResponse response2;
    PostQuestionResponse response3;

    @BeforeEach
    void setUp() {

        final boolean success = true;
        final String message = "Success message";
        final Integer questionID = 123123;
        final String code = "200";

        response = new PostQuestionResponse();
        response.setSuccess(success);
        response.setMessage(message);
        response.setQuestionID(questionID);
        response.setCode(code);

        response2 = new PostQuestionResponse(
                success,
                message,
                questionID,
                code
        );

        response3 = new PostQuestionResponse();
    }


    @Test
    void getSuccess() {
        assertEquals(response.getSuccess(), response2.getSuccess());
    }

    @Test
    void getQuestionID() {
        assertEquals(response.getQuestionID(), response2.getQuestionID());
    }

    @Test
    void getMessage() {
        assertEquals(response.getMessage(), response2.getMessage());
    }

    @Test
    void getCode() {
        assertEquals(response.getCode(), response2.getCode());
    }


    @Test
    void testToString() {
        assertEquals(response.toString(), response2.toString());

        String responseString = response.toString();

        assertTrue(responseString.contains(String.valueOf(response.getSuccess())));
        assertTrue(responseString.contains(response.getMessage()));

    }
}