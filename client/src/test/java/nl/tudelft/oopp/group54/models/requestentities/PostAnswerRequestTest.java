package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostAnswerRequestTest {
	PostAnswerRequest request;
	PostAnswerRequest request2;

	@BeforeEach
	void setUp() {
		request2 = new PostAnswerRequest("1", "Answer text");
		request = new PostAnswerRequest();
		request.setUserId("1");
		request.setAnswerText("Answer text");
	}


	@Test
	void getUserId() {
		assertEquals(request.getUserId(), request2.getUserId());
		assertEquals("1", request2.getUserId());
	}

	@Test
	void getAnswerText() {
		assertEquals("Answer text", request2.getAnswerText());
	}
}