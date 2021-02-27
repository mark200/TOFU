package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MockQuestionServiceImplementation implements QuestionService {
    private static Map<QuestionKey, Question> questionMap = new TreeMap<>();
    private static Map<String>
    @Override
    public JSONObject postQuestion(String lectureId, String userId, String questionText) {
        QuestionKey mockQuestionKey = new QuestionKey(123, 123);
        Question mockQuestion = new Question(mockQuestionKey, 13, questionText, 0, false, new Date());
        questionMap.put(mockQuestionKey, mockQuestion);


    }


    @Override
    public JSONObject voteOnQuestion(String lectureId, String userId, String questionId, boolean isUpvote) {

    }

    @Override
    public JSONObject answerQuestion(String lectureId, String userId, String questionId, String answerText) {
        return null;
    }

    @Override
    public List<Question> getAllQuestions(String lectureId, String userId) {
        return null;
    }
}
