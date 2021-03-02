package nl.tudelft.oopp.group54.controllers.answers;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class MockAnswerServiceImpl implements AnswerService {

    @Override
    public Map<String, Object> answerQuestion(Long lectureId, String userId, Long questionId, String answerText) {

        Map<String, Object> status = new TreeMap<>();

        status.put("sucess", true);
        status.put("answer", answerText);

        return status;
    }
}
