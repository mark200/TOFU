package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoteRequestTest {
	VoteRequest request;
	VoteRequest request2;
	VoteRequest request3;

	@BeforeEach
	void setUp() {
		request = new VoteRequest("bob", 1);
		request.setUpvote(false);

		request2 = new VoteRequest();
		request2.setQuestionId(1);
		request2.setUserId("bob");

		request3 = new VoteRequest();
		request3.setQuestionId(1);
		request3.setUserId("bob");
		request3.setUpvote(false);
	}

	@Test
	void isUpvote() {
		assertFalse(request.isUpvote());
	}

	@Test
	void getUserId() {
		assertEquals("bob", request.getUserId());
	}

	@Test
	void getQuestionId() {
		assertEquals(1, request.getQuestionId());
	}

	@Test
	void testToString() {
		String requestString1 = request.toString();
		String requestString2 = request2.toString();
		String requestString3 = request3.toString();

		assertEquals(requestString1, requestString3);
		assertEquals(requestString1, requestString3);
		assertNotEquals(requestString2, requestString3);

		assertTrue(requestString1.contains("bob"));
		assertTrue(requestString1.contains("1"));
		assertTrue(requestString1.contains("false"));
		assertTrue(requestString2.contains("true"));
	}
}