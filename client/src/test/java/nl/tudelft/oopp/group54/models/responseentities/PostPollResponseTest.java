package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostPollResponseTest {
    PostPollResponse response;
    PostPollResponse response2;

    @BeforeEach
    void setUp() {
        Boolean success = true;
        String message = "Success message";

        response = new PostPollResponse();
        response2 = new PostPollResponse(success, message);
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

        assertTrue(responseString.contains(String.valueOf(response.getSuccess())));
        assertTrue(responseString.contains(response.getMessage()));
    }
}