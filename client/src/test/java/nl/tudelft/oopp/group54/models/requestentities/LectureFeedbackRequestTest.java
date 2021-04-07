package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LectureFeedbackRequestTest {

  LectureFeedbackRequest request;
  LectureFeedbackRequest request2;

  @BeforeEach
  void setUp() {
    request2 = new LectureFeedbackRequest(1L,42);
    request = new LectureFeedbackRequest();
    request.setUserId(1L);
    request.setLectureFeedbackCode(42);
  }


  @Test
  void getUserId() {
    assertEquals(request.getUserId(), request2.getUserId());
    assertEquals(1L, request.getUserId());
  }

  @Test
  void getLectureFeedbackCode() {
    assertEquals(request.getLectureFeedbackCode(), request2.getLectureFeedbackCode());
    assertEquals(42, request.getLectureFeedbackCode());
  }


  @Test
  void testToString() {
    assertEquals(request.toString(), request2.toString());
    assertTrue(request.toString().contains("42"));
    assertTrue(request.toString().contains("1"));
  }
}