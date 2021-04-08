package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndPollResponseTest {

	EndPollResponse response;
	EndPollResponse response2;

	@BeforeEach
	void setUp() {
		Boolean success = true;
		String message = "Success message";

		response = new EndPollResponse();
		response2 = new EndPollResponse(success, message);
		response.setSuccess(success);
		response.setMessage(message);
	}

	@Test
	void getSuccess() {
		assertEquals(response.getSuccess(), true);
		assertEquals(response.getSuccess(), response2.getSuccess());
	}

	@Test
	void getMessage() {
		assertEquals(response.getMessage(), "Success message");
		assertEquals(response.getMessage(), response2.getMessage());

	}
}