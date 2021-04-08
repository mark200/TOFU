package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BanIpResponseTest {

	Boolean success = false;
	String message = "Stop right there criminal scum.";
	BanIpResponse bir;
	BanIpResponse bir2;

	@BeforeEach
	void setUp() {
		bir = new BanIpResponse();
		bir2 = new BanIpResponse(success, message);

		bir.setSuccess(success);
		bir.setMessage(message);
	}


	@Test
	void getSuccess() {
		assertEquals(success, bir.getSuccess());
	}

	@Test
	void getMessage() {
		assertEquals(message, bir.getMessage());
	}

	@Test
	void testToString() {
		String result = "BanIpResponse{success=false, message='Stop right there criminal scum.'}";
		assertEquals(result, bir.toString());
		assertEquals(bir2.toString(), bir.toString());
	}
}