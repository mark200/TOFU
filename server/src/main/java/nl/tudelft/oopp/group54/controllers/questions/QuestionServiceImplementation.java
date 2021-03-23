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
     *
     * @param lectureId
     * @param userId
     * @param questionText
     * @return status of the request
     */
    @Override
    public Map<String, Object> postQuestion(Integer lectureId, String userId, String userIp, String questionText) {
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

        if (userIp == null) {
            status.put("success", false);
            status.put("message", "UserIP cannot be null!");
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

    /**
     * Returns all question asked by the User with userID in JSON format
     *
     * @param lectureId the lecture where the questions have been asked
     * @param userId    the user that has asked the questions we return
     * @return
     */
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

        List<Question> allQuestions = questionRepository.findByLectureId(lectureId);

        List<Map<String, Object>> answeredQuestions = allQuestions.stream()
                .filter(x -> x.getAnswered() == true)
                .map(x -> transformQuestion(x, lectureId))
                .collect(Collectors.toList());

        List<Map<String, Object>> unAnsweredQuestions = allQuestions.stream()
                .filter(x -> x.getAnswered() == false)
                .map(x -> transformQuestion(x, lectureId))
                .collect(Collectors.toList());

        toBeReturned.put("answered", answeredQuestions);
        toBeReturned.put("unanswered", unAnsweredQuestions);
        toBeReturned.put("success", true);
        toBeReturned.put("count", answeredQuestions.size() + unAnsweredQuestions.size());

        return toBeReturned;
    }

    @Override
    public Map<String, Object> deleteQuestion(Integer lectureId, Integer questionId, String userId) {
        Map<String, Object> status = new LinkedHashMap<>();

        if (questionId == null) {
            status.put("success", false);
            status.put("message", "Question id can't be null");
            return status;
        }

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "Lecture id can't be null");
            return status;
        }

        // get question and author of request from database.
        Optional<Question> questionToBeDeleted = questionRepository.findById(new QuestionKey(questionId, lectureId));
        Optional<User> authorOfTheDeletionRequest = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));

        Integer requestAuthorLectureId = authorOfTheDeletionRequest.get().getKey().getLecture_id();
        Integer questionAuthorLectureId = questionToBeDeleted.get().getPrimaryKey().getLecture_id();

        // If the lectures of user and question do not match.
        if (!requestAuthorLectureId.equals(questionAuthorLectureId)) {
            status.put("success", false);
            status.put("message", "The question in different lecture can't be deleted!");
            return status;
        }

        if (questionToBeDeleted.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized question. Incorrect combination of lecture and question ids");
            return status;
        }

        if (authorOfTheDeletionRequest.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user id or specified lecture does not exist!");
        }

        Integer requestAuthorId = authorOfTheDeletionRequest.get().getKey().getId();
        Integer questionAuthorId = questionToBeDeleted.get().getStudent_id();
        Integer requestAuthorRole = authorOfTheDeletionRequest.get().getRoleID();

        // 1 - lecturer
        // 2 - moderator
        // 3 - student


        // If the user who made the request to delete question is not the owner of the question,
        // then delete question only if the user who sent delete request is a moderator/lecturer.
        if (!requestAuthorId.equals(questionAuthorId)) {
            // If request was made by user then he is not authorized to delete other's questions
            if (requestAuthorRole.equals(3)) {
                status.put("code", "401 UNAUTHORIZED");
                status.put("success", false);
                status.put("message", "You are not authorized to delete this question!");
                return status;
            }

            // If it was made by moderator or lecturer
            if (requestAuthorRole.equals(2) || requestAuthorRole.equals(1)) {

                try {
                    questionRepository.delete(questionToBeDeleted.get());
                    status.put("success", true);
                    status.put("questionId", questionToBeDeleted.get().getPrimaryKey().getId());
                    status.put("message", "message was deleted successfully!");
                } catch (Exception e) {
                    status.put("success", false);
                    status.put("message", e.toString());
                }

                return status;

            }

        }

        // The last case: the author of request is the owner of the question,
        // just delete the question.

        try {
            questionRepository.delete(questionToBeDeleted.get());
            status.put("success", true);
            status.put("questionId", questionToBeDeleted.get().getPrimaryKey().getId());
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }

    /**
     * Transform a question into JSON format
     *
     * @param q an object of type Question
     * @return A JSON representation of the object
     */
    public Map<String, Object> transformQuestion(Question q, int lecture_id) {
        if (q == null) {
            return null;
        }
        
        Optional<User> author = userRepository.findById(new UserKey(q.getStudent_id(), lecture_id));

        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("questionId", q.getPrimaryKey().getId());
        toBeReturned.put("userId", q.getStudent_id());
        toBeReturned.put("userName", author.get().getName());
        toBeReturned.put("questionText", q.getContent());
        toBeReturned.put("score", q.getVote_counter());
        toBeReturned.put("answered", q.getAnswered());

        if (q.getAnswered())
            toBeReturned.put("answerText", q.getAnswerText());

        return toBeReturned;
    }
}
