package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoteResponseTest {
    VoteResponse response;
    VoteResponse response2;

    @BeforeEach
    void setUp() {
        final Boolean success = true;
        final String message = "Success message";

        response = new VoteResponse();
        response2 = new VoteResponse(success, message);
        response.setSuccess(success);
        response.setMessage(message);
    }

    @Test
    void isSuccess() {
        assertEquals(response.isSuccess(), response2.isSuccess());
    }

    @Test
    void getMessage() {
        assertEquals(response.getMessage(), response2.getMessage());
    }

    @Test
    void testToString() {
        assertEquals(response.toString(), response2.toString());

        String responseString = response.toString();

        assertTrue(responseString.contains(String.valueOf(response.isSuccess())));
        assertTrue(responseString.contains(response.getMessage()));
    }
}