package nl.tudelft.oopp.group54.controllers.answers;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class AnswerServiceImpl implements AnswerService{
    @Override
    public Map<String, Object> answerQuestion(Long lectureId, String userId, Long questionId, String answerText) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        if (lectureId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "LectureID cannot be null.");
            return toBeReturned;
        }

        if (userId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "UserID cannot be null.");
            return toBeReturned;
        }

        if (questionId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "QuestionID cannot be null.");
            return toBeReturned;
        }

        return toBeReturned;
    }
}
