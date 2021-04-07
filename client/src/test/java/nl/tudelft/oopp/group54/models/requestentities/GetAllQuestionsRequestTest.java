package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetAllQuestionsRequestTest {
  GetAllQuestionsRequest request;
  GetAllQuestionsRequest request2;

  @BeforeEach
  void setUp() {
    request = new GetAllQuestionsRequest("bob");
    request2 = new GetAllQuestionsRequest("bob2");
    request2.setUserId("bob");
  }

  @Test
  void getUserId() {
    assertEquals("bob", request.getUserId());
  }


  @Test
  void testToString() {
    assertTrue(request.toString().contains("bob"));
    assertEquals(request2.toString(), request.toString());
  }
}