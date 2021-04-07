package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetCurrentPollResponseTest {

  GetCurrentPollResponse response;
  GetCurrentPollResponse response2;
  GetCurrentPollResponse response3;

  @BeforeEach
  void setUp() {


    Boolean success = true;
    String message = "Some message";
    Integer optionCount = 42;
    Boolean closed = false;
    String title = "Test Question";
    String pollId = "RandomlyGeneratorPollId";

    response = new GetCurrentPollResponse();
    response.setSuccess(success);
    response.setMessage(message);
    response.setOptionCount(optionCount);
    response.setClosed(closed);
    response.setTitle(title);
    response.setPollId(); // What in the fresh fxck is this??

    response2 = new GetCurrentPollResponse(
            success,
            message,
            optionCount,
            closed,
            title,
            pollId
    );

    response3 = new GetCurrentPollResponse();
  }



  @Test
  void getSuccess() {
    assertEquals(response.getSuccess(), response2.getSuccess());
  }

  @Test
  void getMessage() {
    assertEquals(response.getMessage(), response2.getMessage());
  }

  @Test
  void getOptionCount() {
    assertEquals(response.getOptionCount(), response2.getOptionCount());
  }

  @Test
  void getClosed() {
    assertEquals(response.getClosed(), response2.getClosed());
  }

  @Test
  void getTitle() {
    assertEquals(response.getTitle(), response2.getTitle());
  }

  @Test
  void getPollId() {
    assertNotEquals(response.getPollId(), response2.getPollId());
    assertNull(response.getPollId());
    assertNotNull(response2.getPollId());
  }
}