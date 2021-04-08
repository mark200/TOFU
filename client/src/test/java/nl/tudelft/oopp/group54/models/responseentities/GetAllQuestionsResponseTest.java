package nl.tudelft.oopp.group54.models.responseentities;

import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.requestentities.GetAllQuestionsRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetAllQuestionsResponseTest {
	GetAllQuestionsResponse response;
	GetAllQuestionsResponse response2;
	GetAllQuestionsResponse response3;


	@BeforeEach
	void setUp() {

		List<QuestionModel> answered = List.of(
				new QuestionModel(),
				new QuestionModel(),
				new QuestionModel()
		);
		List<QuestionModel> unanswered = new ArrayList<>();
		boolean success = true;
		int count = 3;

		response = new GetAllQuestionsResponse();
		response.setSuccess(success);
		response.setAnswered(answered);
		response.setUnanswered(unanswered);
		response.setCount(count);

		response2 = new GetAllQuestionsResponse(
				answered,
				unanswered,
				success,
				count
		);

		response3 = new GetAllQuestionsResponse();
	}


	@Test
	void getAnswered() {
		assertEquals(response.getAnswered(), response2.getAnswered());
	}

	@Test
	void getUnanswered() {
		assertEquals(response.getUnanswered(), response2.getUnanswered());
	}

	@Test
	void getSuccess() {
		assertEquals(response.getSuccess(), response2.getSuccess());
	}

	@Test
	void getCount() {
		assertEquals(response.getCount(), response2.getCount());
	}
}