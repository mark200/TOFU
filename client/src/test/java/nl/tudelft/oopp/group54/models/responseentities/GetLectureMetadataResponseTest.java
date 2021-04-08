package nl.tudelft.oopp.group54.models.responseentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetLectureMetadataResponseTest {

    GetLectureMetadataResponse response;
    GetLectureMetadataResponse response2;
    GetLectureMetadataResponse response3;

    @BeforeEach
    void setUp() {

        final Boolean success = true;
        final String message = "Message";
        final Integer lectureId = 1;
        final Integer people = 123;
        final String studentJoinId = "studentJoinId";
        final String lecturerJoinId = "lecturerJoinId";
        final String moderatorJoinId = "moderatorJoinId";
        final Boolean lectureOngoing = true;

        response = new GetLectureMetadataResponse();
        response.setSuccess(success);
        response.setMessage(message);
        response.setLectureId(lectureId);
        response.setPeople(people);
        response.setStudentJoinId(studentJoinId);
        response.setLecturerJoinId(lecturerJoinId);
        response.setModeratorJoinId(moderatorJoinId);
        response.setLectureOngoing(lectureOngoing);

        response2 = new GetLectureMetadataResponse(
                success,
                message,
                lectureId,
                people,
                studentJoinId,
                lecturerJoinId,
                moderatorJoinId,
                lectureOngoing
        );

        response3 = new GetLectureMetadataResponse();
    }


    @Test
    void getSuccess() {
        assertEquals(response.getSuccess(), response2.getSuccess());
        assertEquals(false, response3.getSuccess());
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
    void getPeople() {
        assertEquals(response.getPeople(), response2.getPeople());
        assertEquals(0, response3.getPeople());
    }

    @Test
    void getStudentJoinId() {
        assertEquals(response.getStudentJoinId(), response2.getStudentJoinId());
        assertEquals("", response3.getStudentJoinId());
    }

    @Test
    void getLecturerJoinId() {
        assertEquals(response.getLecturerJoinId(), response2.getLecturerJoinId());
        assertEquals("", response3.getLecturerJoinId());
    }

    @Test
    void getModeratorJoinId() {
        assertEquals(response.getModeratorJoinId(), response2.getModeratorJoinId());
        assertEquals("", response3.getModeratorJoinId());
    }

    @Test
    void getLectureOngoing() {
        assertEquals(response.getSuccess(), response2.getLectureOngoing());
        assertEquals(true, response3.getLectureOngoing());
    }
}