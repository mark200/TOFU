package nl.tudelft.oopp.group54.models.responseentities;

import nl.tudelft.oopp.group54.models.requestentities.PostQuestionRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetLectureFeedbackResponseTest {
  GetLectureFeedbackResponse response;
  GetLectureFeedbackResponse response2;
  GetLectureFeedbackResponse response3;

  HashMap<String, Integer> responseMessage;
  HashMap<String, Integer> responseMessage2;

  @BeforeEach
  void setUp() {

    responseMessage = new HashMap<>();
    responseMessage2 = new HashMap<>();

    responseMessage.put("msg1", 1);
    responseMessage.put("msg2", 2);
    responseMessage2.put("msg1", 1);

    response = new GetLectureFeedbackResponse();
    response.setSuccess(true);
    response.setMessage("Message");
    response.setLectureFeedbackMap(responseMessage);

    response2 = new GetLectureFeedbackResponse(
            true, "Message", responseMessage
    );

    response3 = new GetLectureFeedbackResponse();

  }


  @Test
  void getSuccess() {
    assertEquals(response.getSuccess(), response2.getSuccess());
    assertEquals(true, response.getSuccess());
    assertEquals(false, response3.getSuccess());
  }

  @Test
  void getMessage() {
    assertEquals(response.getMessage(), response2.getMessage());
    assertEquals("Message", response.getMessage());
    assertEquals(null, response3.getMessage());

  }

  @Test
  void getLectureFeedbackMap() {
    assertEquals(response.getLectureFeedbackMap(), response2.getLectureFeedbackMap());
    assertEquals(responseMessage, response.getLectureFeedbackMap());
    assertEquals(null, response3.getLectureFeedbackMap());
  }

  @Test
  void testToString() {
    assertEquals(response.toString(), response2.toString());
  }

  @Test
  void testEquals() {
    assertEquals(response, response);
    assertEquals(response, response2);

    assertNotEquals(null, response2);
    assertNotEquals(response, null);
    assertNotEquals(response, response3);
    assertNotEquals(response, 1);

    response.setMessage("");
    assertNotEquals(response, response2);
    response.setMessage(response2.getMessage());

    response.setSuccess(!response.getSuccess());
    assertNotEquals(response, response2);
    response.setSuccess(response2.getSuccess());

    response.setLectureFeedbackMap(null);
    assertNotEquals(response, response2);
    response.setLectureFeedbackMap(response2.getLectureFeedbackMap());
  }

  @Test
  void testHashCode() {
    assertNotNull(response.hashCode());
  }
}