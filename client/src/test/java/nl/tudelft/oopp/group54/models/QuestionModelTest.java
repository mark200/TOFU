package nl.tudelft.oopp.group54.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class QuestionModelTest {

  QuestionModel emptyModel;
  QuestionModel otherModel;

  String userIp = "192.168.0.1";
  String one = "1";
  String userName = "Student";
  String questionText = "Question";
  String answerText = "Answer";
  Integer defaultScore = 0;
  Boolean isAnswered = false;
  Date date = new Date();


  @BeforeEach
  void setUp() {

    otherModel = new QuestionModel();

    otherModel.setUserIp(userIp);
    otherModel.setQuestionId(one);
    otherModel.setUserId(one);
    otherModel.setUserName(userName);
    otherModel.setScore(defaultScore);
    otherModel.setAnswered(isAnswered);
    otherModel.setCreatedAt(date);
    otherModel.setAnswerText(answerText);
    otherModel.setQuestionText(questionText);

  }

  @Test
  void getUserIp() {
    assertEquals(userIp, otherModel.getUserIp());
  }

  @Test
  void getQuestionId() {
    assertEquals(one, otherModel.getQuestionId());
  }


  @Test
  void getUserId() {
    assertEquals(one, otherModel.getUserId());
  }


  @Test
  void getUserName() {
    assertEquals(userName, otherModel.getUserName());
  }


  @Test
  void getQuestionText() {
    assertEquals(questionText, otherModel.getQuestionText());
  }

  @Test
  void getScore() {
    assertEquals(defaultScore, otherModel.getScore());
  }

  @Test
  void isAnswered() {
    assertEquals(isAnswered, otherModel.isAnswered());
  }


  @Test
  void getAnswerText() {
    assertEquals(answerText, otherModel.getAnswerText());
  }

  @Test
  void getCreatedAt() {
    assertEquals(date, otherModel.getCreatedAt());
  }

  @Test
  void testToString() {
//    String emptyString = "";
    String nonEmptyString = "Q: Question\nA: Answer\ncreated at";
    String emptyString = "Q: Question\nA: No answer for this question";

    assertTrue(otherModel.toString().startsWith(nonEmptyString));

    otherModel.setAnswerText(null);

    assertTrue(otherModel.toString().startsWith(emptyString));

  }
}