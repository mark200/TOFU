package nl.tudelft.oopp.group54.models.requestentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostPollVoteRequestTest {
    PostPollVoteRequest request;
    PostPollVoteRequest request2;

    @BeforeEach
    void setUp() {
        request2 = new PostPollVoteRequest("Vote content", "bob");
        request = new PostPollVoteRequest();
        request.setVote("Vote content");
        request.setUserId("bob");
    }

    @Test
    void getVote() {
        assertEquals(request.getVote(), request2.getVote());
        assertEquals("Vote content", request2.getVote());
    }

    @Test
    void getUserId() {
        assertEquals(request.getUserId(), request2.getUserId());
        assertEquals("bob", request2.getUserId());
    }
}