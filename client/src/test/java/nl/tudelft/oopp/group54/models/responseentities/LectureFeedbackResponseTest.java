package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LectureFeedbackResponseTest {

	LectureFeedbackResponse response;
	LectureFeedbackResponse response2;

	@BeforeEach
	void setUp() {
		Boolean success = true;
		String message = "Success message";
		String code = "200";

		response = new LectureFeedbackResponse();
		response2 = new LectureFeedbackResponse(success, message, code);
		response.setSuccess(success);
		response.setMessage(message);
		response.setCode(code);
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
	void getCode() {
		assertEquals(response.getCode(), response2.getCode());
	}
}