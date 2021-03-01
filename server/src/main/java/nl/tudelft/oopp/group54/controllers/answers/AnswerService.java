package nl.tudelft.oopp.group54.controllers.answers;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface AnswerService {

    Map<String, Object> answerQuestion(Long lectureId,
                                       String userId,
                                       Long questionId,
                                       @Nullable String answerText);
}
