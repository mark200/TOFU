package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostQuestionRequestTest {
  PostQuestionRequest request;
  PostQuestionRequest request2;

  @BeforeEach
  void setUp() {
    request = new PostQuestionRequest("QuestionText","bob");
    request2 = new PostQuestionRequest("QuestionText2","bob2");
    request2.setUserId("bob");
    request2.setQuestionText("QuestionText");
  }

  @Test
  void getQuestionText() {
    assertEquals(request.getQuestionText(), request2.getQuestionText());
    assertEquals("QuestionText", request2.getQuestionText());
  }

  @Test
  void getUserId() {
    assertEquals(request.getUserId(), request2.getUserId());
    assertEquals("bob", request2.getUserId());
  }

  @Test
  void testToString() {
    assertTrue(request.toString().contains("bob"));
    assertTrue(request.toString().contains("QuestionText"));
    assertEquals(request2.toString(), request.toString());
  }
}