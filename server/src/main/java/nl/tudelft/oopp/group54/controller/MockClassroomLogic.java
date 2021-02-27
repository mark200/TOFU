package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public class MockClassroomLogic implements IClassroomLogic {

  @Override
  public ResponseEntity<String> createNewLecture(Date startTime, String lectureName) {
    return null;
  }

  @Override
  public ResponseEntity<String> joinOngoingLecture(String lectureId, String joinId, String userName) {
    return null;
  }

  @Override
  public ResponseEntity<String> postQuestion(String lectureId, String userId, String questionText) {
    return null;
  }

  @Override
  public ResponseEntity<String> voteOnQuestion(String lectureId, String userId, String questionId, boolean isUpvote) {
    return null;
  }

  @Override
  public ResponseEntity<String> answerQuestion(String lectureId, String userId, String questionId, String answerText) {
    return null;
  }

  @Override
  public Question getAllQuestions(String lectureId, String userId) {
    return null;
  }

  @Override
  public ResponseEntity<String> getLectureMetadata(String lectureId, String userId) {
    return null;
  }

  @Override
  public ResponseEntity<String> banStudentIP(String lectureId, String ip) {
    return null;
  }
}
