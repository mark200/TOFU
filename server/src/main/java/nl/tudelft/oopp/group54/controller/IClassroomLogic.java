package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.Date;

public interface IClassroomLogic {

  ResponseEntity<String> createNewLecture(Date startTime, String lectureName);

  ResponseEntity<String> joinOngoingLecture(String lectureId, String joinId, String userName);

  ResponseEntity<String> postQuestion(String lectureId, String userId, String questionText);

  ResponseEntity<String> voteOnQuestion(String lectureId,
                                        String userId,
                                        String questionId,
                                        boolean isUpvote);

  ResponseEntity<String> answerQuestion(String lectureId,
                                        String userId,
                                        String questionId,
                                        @Nullable String answerText);

  //
  Question getAllQuestions(String lectureId, String userId);

  ResponseEntity<String> getLectureMetadata(String lectureId, String userId);

  ResponseEntity<String> banStudentIP(String lectureId, String ip);   // FIXME: Find a better datatype

}
