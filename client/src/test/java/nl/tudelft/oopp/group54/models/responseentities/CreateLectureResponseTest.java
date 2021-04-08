package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateLectureResponseTest {

	CreateLectureResponse response;
	CreateLectureResponse response2;
	CreateLectureResponse response3;

	@BeforeEach
	void setUp() {

		Boolean success = true;
		Integer lectureId = 1;
		String studentId = "studentId";
		String lecturerId = "lecturerId";
		String moderatorId = "moderatorId";
		String message = "Message";

		response = new CreateLectureResponse();
		response.setSuccess(success);
		response.setLectureId(lectureId);
		response.setStudentId(studentId);
		response.setLecturerId(lecturerId);
		response.setModeratorId(moderatorId);
		response.setMessage(message);

		response2 = new CreateLectureResponse(
				success,
				lectureId,
				lecturerId,
				studentId,
				moderatorId,
				message
		);

		response3 = new CreateLectureResponse();
	}


	@Test
	void getSuccess() {
		assertEquals(response.getSuccess(), response2.getSuccess());
		assertEquals(false, response3.getSuccess());
		response3.setSuccess(null);
		assertFalse(response3.getSuccess());
	}

	@Test
	void getMessage() {
		assertEquals(response.getMessage(), response2.getMessage());
		assertEquals("", response3.getMessage());
	}

	@Test
	void getLectureId() {
		assertEquals(response.getSuccess(), response2.getSuccess());
		assertEquals(0, response3.getLectureId());
	}

	@Test
	void getStudentId() {
		assertEquals(response.getStudentId(), response2.getStudentId());
		assertEquals("", response3.getStudentId());
	}

	@Test
	void getLecturerId() {
		assertEquals(response.getLecturerId(), response2.getLecturerId());
		assertEquals("", response3.getLecturerId());
	}

	@Test
	void getModeratorId() {
		assertEquals(response.getModeratorId(), response2.getModeratorId());
		assertEquals("", response3.getModeratorId());
	}


	@Test
	void testToString() {
		assertEquals(response.toString(), response2.toString());
		String responseString = response.toString();

		assertTrue(responseString.contains(response.getSuccess().toString()));
		assertTrue(responseString.contains(response.getLecturerId().toString()));
		assertTrue(responseString.contains(response.getStudentId().toString()));
		assertTrue(responseString.contains(response.getModeratorId().toString()));
		assertTrue(responseString.contains(response.getLectureId().toString()));
		assertTrue(responseString.contains(response.getMessage().toString()));
	}
}