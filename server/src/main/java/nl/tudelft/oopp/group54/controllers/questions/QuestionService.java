package nl.tudelft.oopp.group54.controllers.questions;

import java.util.Map;

public interface QuestionService {
    Map<String, Object> postQuestion(Integer lectureId, String userId, String questionText, String userIp);

    Map<String, Object> getAllQuestions(Integer lectureId, String userId);

    Map<String, Object> deleteQuestion(Integer lectureId, Integer questionId, String userId);
}
