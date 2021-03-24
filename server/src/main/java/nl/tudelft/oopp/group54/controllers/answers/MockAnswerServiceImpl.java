package nl.tudelft.oopp.group54.controllers.answers;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;


@Service
public class MockAnswerServiceImpl implements AnswerService {

    @Override
    public Map<String, Object> answerQuestion(Integer lectureId, String userId, Integer questionId, String answerText) {

        Map<String, Object> status = new TreeMap<>();

        status.put("sucess", true);
        status.put("answer", answerText);

        return status;
    }
}
