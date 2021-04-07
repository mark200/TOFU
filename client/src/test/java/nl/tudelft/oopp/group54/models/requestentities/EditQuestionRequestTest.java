package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditQuestionRequestTest {
  EditQuestionRequest request;
  EditQuestionRequest request2;

  @BeforeEach
  void setUp() {
    request2 = new EditQuestionRequest("1", "Question text");
    request = new EditQuestionRequest();
    request.setQuestionId("1");
    request.setNewContent("Question text");
  }

  @Test
  void getQuestionId() {
    assertEquals(request.getQuestionId(), request2.getQuestionId());
    assertEquals("1", request2.getQuestionId());
  }

  @Test
  void getNewContent() {
    assertEquals(request.getNewContent(), request2.getNewContent());
    assertEquals("Question text", request2.getNewContent());
  }
}