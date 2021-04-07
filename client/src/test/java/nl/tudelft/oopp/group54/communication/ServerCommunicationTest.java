package nl.tudelft.oopp.group54.communication;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerCommunicationTest {

  @Test
  void postLecture() {

    try {
      assertNotNull(ServerCommunication.postLecture(0L, "TestLecture"));
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

  }

  @Test
  void joinLecture() {

    try {
      assertNotNull(
              ServerCommunication.joinLecture(
                      "userName",
                      0,
                      "/j/fakeid/fakeid"
              )
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void postQuestion() {

    try {
      assertNotNull(
              ServerCommunication.postQuestion("Test question")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

  }

  @Test
  void getAllQuestions() {
    try {
      assertNotNull(
              ServerCommunication.getAllQuestions()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void postAnswer() {
    try {
      assertNotNull(
              ServerCommunication.postAnswer("1", "answerText"
              )
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void deleteQuestion() {
    try {
      assertNotNull(
              ServerCommunication.deleteQuestion("1")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void editQuestion() {
    try {
      assertNotNull(
              ServerCommunication.editQuestion("1", "newText")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void endLecture() {
    try {
      assertNotNull(
              ServerCommunication.endLecture()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void voteOnQuestion() {
    try {
      assertNotNull(
              ServerCommunication.voteOnQuestion(1)
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void banIp() {
    try {
      assertNotNull(
              ServerCommunication.banIp("1", "127.0.0.1")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getLectureMetadata() {
    try {
      assertNotNull(
              ServerCommunication.getLectureMetadata()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getLectureFeedback() {
    try {
      assertNotNull(
              ServerCommunication.getLectureFeedback()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void sendLectureFeedback() {
    try {
      assertNotNull(
              ServerCommunication.sendLectureFeedback(0L, 1)
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void postPoll() {
    try {
      assertNotNull(
              ServerCommunication.postPoll("A", 3, "testPoll")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void postPollVote() {
    try {
      assertNotNull(
              ServerCommunication.postPollVote("SomeVote")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getCurrentPoll() {
    try {
      assertNotNull(
              ServerCommunication.getCurrentPoll()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void endCurrentPoll() {
    try {
      assertNotNull(
              ServerCommunication.endLecture()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getPollStats() {
    try {
      assertNotNull(
              ServerCommunication.getPollStats()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void reopenPoll() {
    try {
      assertNotNull(
              ServerCommunication.reopenPoll()
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}