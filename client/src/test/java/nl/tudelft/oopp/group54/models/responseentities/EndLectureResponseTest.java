package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndLectureResponseTest {

    EndLectureResponse response;
    EndLectureResponse response2;

    @BeforeEach
    void setUp() {
        Boolean success = true;
        String message = "Success message";

        response = new EndLectureResponse();
        response2 = new EndLectureResponse(message, success);
        response.setSuccess(success);
        response.setMessage(message);
    }

    @Test
    void getSuccess() {
        assertEquals(response.getSuccess(), response2.getSuccess());
    }

    @Test
    void getMessage() {
        assertEquals(response.getMessage(), response2.getMessage());
    }

    @Test
    void testToString() {
        assertEquals(response.toString(), response2.toString());

        String responseString = response.toString();

        assertTrue(responseString.contains(response.getSuccess().toString()));
        assertTrue(responseString.contains(response.getMessage()));
    }
}