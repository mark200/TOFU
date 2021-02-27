package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.Question;

import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.List;

public interface QuestionService {
    Map<String, Object> postQuestion(String lectureId, String userId, String questionText);

    Map<String, Object> voteOnQuestion(String lectureId,
                          String userId,
                          String questionId,
                          boolean isUpvote);

    Map<String, Object> answerQuestion(String lectureId,
                          String userId,
                          String questionId,
                          @Nullable String answerText);

    List<Question> getAllQuestions(String lectureId, String userId);
}
