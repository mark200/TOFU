package nl.tudelft.oopp.group54.models.responseentities;

import nl.tudelft.oopp.group54.models.requestentities.PostQuestionRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostQuestionResponseTest {
  PostQuestionResponse response;
  PostQuestionResponse response2;
  PostQuestionResponse response3;

  @BeforeEach
  void setUp() {

    boolean success = true;
    String message = "Success message";
    Integer questionID = 123123;
    String code = "200";

    response = new PostQuestionResponse();
    response.setSuccess(success);
    response.setMessage(message);
    response.setQuestionID(questionID);
    response.setCode(code);

    response2 = new PostQuestionResponse(
            success,
            message,
            questionID,
            code
    );

    response3 = new PostQuestionResponse();
  }


  @Test
  void getSuccess() {
    assertEquals(response.getSuccess(), response2.getSuccess());
  }

  @Test
  void getQuestionID() {
    assertEquals(response.getQuestionID(), response2.getQuestionID());
  }

  @Test
  void getMessage() {
    assertEquals(response.getMessage(), response2.getMessage());
  }

  @Test
  void getCode() {
    assertEquals(response.getCode(), response2.getCode());
  }


  @Test
  void testToString() {
    assertEquals(response.toString(), response2.toString());

    String responseString = response.toString();

    assertTrue(responseString.contains(String.valueOf(response.getSuccess())));
    assertTrue(responseString.contains(response.getMessage()));

  }
}