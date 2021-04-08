package nl.tudelft.oopp.group54.models.requestentities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateLectureRequestTest {
    CreateLectureRequest request;
    CreateLectureRequest requestb;

    @BeforeEach
    void setUp() {
        request = new CreateLectureRequest();
        requestb = new CreateLectureRequest("oopp", 0L);
    }

    @Test
    void getLectureName() {
        assertNull(request.getLectureName());
        assertEquals(requestb.getLectureName(), "oopp");
    }

    @Test
    void setLectureName() {
        request.setLectureName("RnL");
        assertEquals(request.getLectureName(), "RnL");
    }

    @Test
    void getStartTime() {
        assertEquals(request.getStartTime(), 0L);
    }

    @Test
    void setStartTime() {
        requestb.setStartTime(1L);
        assertEquals(requestb.getStartTime(), 1L);
    }

    @Test
    void testToString() {
        String test = "CreateLectureRequest{lectureName='oopp', startTime=0}";
        assertEquals(requestb.toString(), test);
    }
}