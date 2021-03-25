package nl.tudelft.oopp.group54.controllers.answers;

import java.util.Map;
import org.springframework.lang.Nullable;

public interface AnswerService {

    Map<String, Object> answerQuestion(Integer lectureId,
                                       String userId,
                                       Integer questionId,
                                       @Nullable String answerText);
}
