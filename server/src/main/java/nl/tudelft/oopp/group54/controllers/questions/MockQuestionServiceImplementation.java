package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MockQuestionServiceImplementation implements QuestionService {
    private static Map<QuestionKey, Question> questionMap = new TreeMap<>();


    /**
     * post a question
     *
     * @param lectureId
     * @param userId
     * @param questionText
     * @return status of the request
     */
    @Override
    public Map<String, Object> postQuestion(Integer lectureId, String userId, String questionText) {

        Map<String, Object> status = new TreeMap<>();
        QuestionKey mockQuestionKey = new QuestionKey(123, 123);

        Question mockQuestion = new Question(mockQuestionKey, 13, questionText, 0, false, new Date());
        questionMap.put(mockQuestionKey, mockQuestion);

        status.put("success", true);
        status.put("questionID", 123);


        System.out.println(questionMap.toString());
        return status;
    }

    @Override
    public Map<String, Object> getAllQuestions(Integer lectureId, String userId) {

        // List<Question> questions = new ArrayList<>(questionMap.values());

        // return questions;

        return null;
    }

    @Override
    public Map<String, Object> deleteQuestion(Integer lectureId, Integer questionId, String userId) {
        return null;
    }
}