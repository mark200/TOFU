package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;
import org.h2.util.json.JSONObject;
import org.springframework.lang.Nullable;

import java.util.List;

public interface QuestionService {
    JSONObject postQuestion(String lectureId, String userId, String questionText);

    JSONObject voteOnQuestion(String lectureId,
                              String userId,
                              String questionId,
                              boolean isUpvote);

    JSONObject answerQuestion(String lectureId,
                              String userId,
                              String questionId,
                              @Nullable String answerText);

    List<Question> getAllQuestions(String lectureId, String userId);
}
