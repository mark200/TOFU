package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;
import org.springframework.lang.Nullable;

public interface QuestionService {
    String postQuestion(String lectureId, String userId, String questionText);

    String voteOnQuestion(String lectureId,
                          String userId,
                          String questionId,
                          boolean isUpvote);

    String answerQuestion(String lectureId,
                          String userId,
                          String questionId,
                          @Nullable String answerText);

    Question getAllQuestions(String lectureId, String userId);
}
