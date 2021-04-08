package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetCurrentPollResponseTest {

    GetCurrentPollResponse response;
    GetCurrentPollResponse response2;
    GetCurrentPollResponse response3;

    @BeforeEach
    void setUp() {
        final Boolean success = true;
        final String message = "Some message";
        final Integer optionCount = 42;
        final Boolean closed = false;
        final String title = "Test Question";
        final String pollId = "RandomlyGeneratorPollId";

        response = new GetCurrentPollResponse();
        response.setSuccess(success);
        response.setMessage(message);
        response.setOptionCount(optionCount);
        response.setClosed(closed);
        response.setTitle(title);
        response.setPollId(); // What in the fresh fxck is this??

        response2 = new GetCurrentPollResponse(
                success,
                message,
                optionCount,
                closed,
                title,
                pollId
        );

        response3 = new GetCurrentPollResponse();
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
    void getOptionCount() {
        assertEquals(response.getOptionCount(), response2.getOptionCount());
    }

    @Test
    void getClosed() {
        assertEquals(response.getClosed(), response2.getClosed());
    }

    @Test
    void getTitle() {
        assertEquals(response.getTitle(), response2.getTitle());
    }

    @Test
    void getPollId() {
        assertNotEquals(response.getPollId(), response2.getPollId());
        assertNull(response.getPollId());
        assertNotNull(response2.getPollId());
    }
}