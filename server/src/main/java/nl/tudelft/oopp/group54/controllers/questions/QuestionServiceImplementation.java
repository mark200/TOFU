package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImplementation implements QuestionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * post a question
     * @param lectureId
     * @param userId
     * @param questionText
     * @return status of the request
     */
    @Override
    public Map<String, Object> postQuestion(Integer lectureId, String userId, String questionText) {
        Map<String, Object> status = new TreeMap<>();

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }

        if (questionText == null || questionText.length() <= 0 || questionText.length() > 420) {
            status.put("code", "422 UNPROCESSABLE ENTRY");
            status.put("success", false);
            status.put("message", "Question cannot be null and must be between 1 and 420 characters.");
            return status;
        }

        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));

        if (findUserRow.isEmpty()) {
            status.put("code", "401 UNAUTHORIZED");
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        QuestionKey newQuestionKey = new QuestionKey(new Random().nextInt(), lectureId);

        Question newQuestion = new Question();
        newQuestion.setPrimaryKey(newQuestionKey);
        newQuestion.setStudent_id(findUserRow.get().getKey().getId());
        newQuestion.setContent(questionText);
        newQuestion.setCreated_at(new Date());

        questionRepository.flush();

        try {
            questionRepository.save(newQuestion);
            status.put("success", true);
            status.put("questionID", newQuestion.getPrimaryKey().getId());
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }

    @Override
    public List<Question> getAllQuestions(Integer lectureId, String userId) {

        if (lectureId == null) {
            throw new IllegalArgumentException("LectureID cannot be null!");
        }

        if (userId == null) {
            throw new IllegalArgumentException("UserID cannot be null!");
        }

        Optional<User> findUserRow = userRepository.findById(
                new UserKey(Integer.parseInt(userId), lectureId)
        );

        if (findUserRow.isEmpty()) {
            throw new IllegalArgumentException(
                    "Unrecognized user ID or specified lecture does not exist!"
            );
        }

        List<Question> test = questionRepository.findByLectureId(lectureId);


        return test;
    }
}
