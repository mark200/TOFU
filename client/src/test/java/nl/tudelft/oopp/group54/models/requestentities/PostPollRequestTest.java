package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostPollRequestTest {

	PostPollRequest request;
	PostPollRequest request2;

	@BeforeEach
	void setUp() {
		request2 = new PostPollRequest(1, "bob", "correct answer", "title");

		request = new PostPollRequest();
		request.setOptionCount(1);
		request.setUserId("bob");
		request.setCorrectAnswer("correct answer");
		request.setTitle("title");
	}


	@Test
	void getOptionCount() {
		assertEquals(request.getOptionCount(), request2.getOptionCount());
		assertEquals(1, request.getOptionCount());
	}

	@Test
	void getCorrectAnswer() {
		assertEquals(request.getCorrectAnswer(), request2.getCorrectAnswer());
		assertEquals("correct answer", request.getCorrectAnswer());
	}

	@Test
	void getUserId() {
		assertEquals(request.getUserId(), request2.getUserId());
		assertEquals("bob", request.getUserId());
	}

	@Test
	void getTitle() {
		assertEquals(request.getTitle(), request2.getTitle());
		assertEquals("title", request.getTitle());
	}
}