package nl.tudelft.oopp.group54.models.requestentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoinLectureRequestTest {
    JoinLectureRequest request;

    @BeforeEach
    void setUp() {
        request = new JoinLectureRequest("bob", 1, "1");
    }

    @Test
    void getUserName() {
        assertEquals(request.getUserName(), "bob");
    }

    @Test
    void setUserName() {
        request.setUserName("nik");
        assertEquals(request.getUserName(), "nik");
    }

    @Test
    void getLectureID() {
        assertEquals(request.getLectureID(), 1);
    }

    @Test
    void setLectureID() {
        request.setLectureID(2);
        assertEquals(request.getLectureID(), 2);
    }

    @Test
    void getUserID() {
        assertEquals(request.getUserID(), "1");
    }

    @Test
    void setUserID() {
        request.setUserID("23");
        assertEquals(request.getUserID(), "23");
    }

    @Test
    void testToString() {
        String test = "JoinLectureRequest{userName='bob', lectureID=1, userID=1}";
        assertEquals(request.toString(), test);
    }
}