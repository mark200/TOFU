package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditQuestionResponseTest {

  EditQuestionResponse response;
  EditQuestionResponse response2;

  @BeforeEach
  void setUp() {
    Boolean success = true;
    String message = "Success message";
    String questionId = "1";
    String code = "200";

    response = new EditQuestionResponse();
    response2 = new EditQuestionResponse();
    response.setSuccess(success);
    response.setMessage(message);
    response.setQuestionId(questionId);
    response.setCode(code);
  }



  @Test
  void getSuccess() {
    assertNotEquals(response.getSuccess(), response2.getSuccess());
  }

  @Test
  void getCode() {
    assertNotEquals(response.getCode(), response2.getCode());
  }

  @Test
  void getQuestionId() {
    assertNotEquals(response.getQuestionId(), response2.getQuestionId());
  }

  @Test
  void getMessage() {
    assertNotEquals(response.getMessage(), response2.getMessage());
  }

}