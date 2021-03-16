package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.*;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImplementation implements QuestionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LectureRepository lectureRepository;

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
    public Map<String, Object> getAllQuestions(Integer lectureId, String userId) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        if (lectureId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "LectureId cannot be null.");
            return toBeReturned;
        }

        if (userId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "userId cannot be null.");
            return toBeReturned;
        }

        Optional<User> foundUser = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (foundLecture.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture by this ID does not exist.");
            return toBeReturned;
        }

        if (foundUser.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Unrecognized userID.");
            return toBeReturned;
        }

        Map<String, Object> questionsMap = new TreeMap<>();
        String userName = foundUser.get().getName();

        List<Map<String, Object>> answeredQuestions = questionRepository.findAll().stream()
                                                                        .filter(x -> x.getAnswered() == true)
                                                                        .map(x -> transformQuestion(x, userName))
                                                                        .collect(Collectors.toList());

        List<Map<String, Object>> unAnsweredQuestions = questionRepository.findAll().stream()
                                                                        .filter(x -> x.getAnswered() == false)
                                                                        .map(x -> transformQuestion(x, userName))
                                                                        .collect(Collectors.toList());

        questionsMap.put("answered", answeredQuestions);
        questionsMap.put("unanswered", unAnsweredQuestions);

        toBeReturned.put("success", true);
        toBeReturned.put("count", answeredQuestions.size() + unAnsweredQuestions.size());
        toBeReturned.put("userName", foundUser.get().getName());
        toBeReturned.put("questions", questionsMap);

        return toBeReturned;
    }

    /**
     * Transform a question into JSON format
     * @param q an object of type Question
     * @return A JSON representation of the object
     */
    public Map<String, Object> transformQuestion(Question q, String userName) {
        if (q == null) {
            return null;
        }

        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("userID", q.getStudent_id());
        toBeReturned.put("userName", userName);
        toBeReturned.put("questionText", q.getContent());
        toBeReturned.put("score", q.getVote_counter());
        toBeReturned.put("answered", q.getAnswered());

        if (q.getAnswered())
            toBeReturned.put("answerText", q.getAnswerText());

        return toBeReturned;
    }
}
