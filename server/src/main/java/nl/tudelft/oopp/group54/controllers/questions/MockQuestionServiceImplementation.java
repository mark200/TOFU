package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.controllers.questions.QuestionService;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Service
public class MockQuestionServiceImplementation implements QuestionService {
  private static Map<QuestionKey, Question> questionMap = new TreeMap<>();


  /**
   * post a question
   * @param lectureId
   * @param userId
   * @param questionText
   * @return status of the requst
   */
  @Override
  public Map<String, Object> postQuestion(Long lectureId, String userId, String questionText) {

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
  public List<Question> getAllQuestions(Long lectureId, String userId) {

    List<Question> questions = new ArrayList<>(questionMap.values());

    return questions;
  }
}