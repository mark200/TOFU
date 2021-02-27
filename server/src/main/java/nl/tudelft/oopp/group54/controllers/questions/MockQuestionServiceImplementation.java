package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.Question;

import java.util.List;
import java.util.Map;

public class MockQuestionServiceImplementation implements QuestionService {

  @Override
  public Map<String, Object> postQuestion(String lectureId, String userId, String questionText) {
    return null;
  }

  @Override
  public Map<String, Object> voteOnQuestion(String lectureId, String userId, String questionId, boolean isUpvote) {
    return null;
  }

  @Override
  public Map<String, Object> answerQuestion(String lectureId, String userId, String questionId, String answerText) {
    return null;
  }

  @Override
  public List<Question> getAllQuestions(String lectureId, String userId) {
    return null;
  }
}
