package nl.tudelft.oopp.group54.controllers.questions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.MapLoggers;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.BanRepository;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImplementation implements QuestionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private BanRepository banRepository;

    /**
     * post a question.
     *
     * @param lectureId    desc
     * @param userId       desc
     * @param questionText desc
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
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);
        BanKey bk = new BanKey(userIp, lectureId);
        Optional<Ban> findBanRow = banRepository.findById(bk);

        if (findBanRow.isPresent()) {
            status.put("code", "401 UNAUTHORIZED");
            status.put("success", false);
            status.put("message", "Ip address has been banned from posting questions");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }

        if (findUserRow.isEmpty()) {
            status.put("code", "401 UNAUTHORIZED");
            status.put("success", false);
            status.put("message", "Unrecognized user ID or user not in lecture!");
            return status;
        }

        // don't let students post questions once the lecture has ended
        if (findUserRow.get().getRoleID().equals(3)) {
            if (!foundLecture.get().isLectureOngoing()) {
                status.put("success", false);
                status.put("message", "The lecture has ended.");
                return status;
            }
        }

        // don't let students post questions more than once in a minute.
        Date timeLimit = new Date(System.currentTimeMillis() - 60000);
        Date lastQuestion = findUserRow.get().getlastQuestion();
        if (findUserRow.get().getRoleID().equals(3) && lastQuestion != null) {
            if (lastQuestion.after(timeLimit)) {
                status.put("success", false);
                status.put("message", "Cannot post more than 1 question in 1 minute.");
                return status;
            }
        }

        QuestionKey newQuestionKey = new QuestionKey(null, lectureId);

        Question newQuestion = new Question();
        newQuestion.setPrimaryKey(newQuestionKey);
        newQuestion.setStudentId(findUserRow.get().getKey().getId());
        newQuestion.setContent(questionText);
        newQuestion.setCreatedAt(new Date());
        newQuestion.setStudentIp(userIp);
        findUserRow.get().setLastQuestion(new Date(System.currentTimeMillis()));

        questionRepository.flush();

        String logMessage = "User " + userId + " (" + findUserRow.get().getIpAddress() + ") asked a question " + questionText;
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        try {
            questionRepository.save(newQuestion);
            status.put("success", true);
            status.put("message", "question has been posted");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }

    /**
     * Returns all question asked by the User with userID in JSON format.
     *
     * @param lectureId the lecture where the questions have been asked
     * @param userId    the user that has asked the questions we return
     * @return Map
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

        String logMessage = "User " + userId + " (" + foundUser.get().getIpAddress() + ") requests all questions";
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

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

        if (userId == null) {
            status.put("success", false);
            status.put("message", "user id can't be null");
            return status;
        }

        // get question and author of request from database.
        Optional<Question> questionToBeDeleted = questionRepository.findById(new QuestionKey(questionId, lectureId));
        Optional<User> authorOfTheDeletionRequest = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        Integer requestAuthorLectureId = authorOfTheDeletionRequest.get().getKey().getLectureID();
        Integer questionAuthorLectureId = questionToBeDeleted.get().getPrimaryKey().getLectureId();

        // If the lectures of user and question do not match.
        if (!requestAuthorLectureId.equals(questionAuthorLectureId)) {
            status.put("success", false);
            status.put("message", "The question in different lecture can't be deleted!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized lecture.");
        }

        if (questionToBeDeleted.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized question. Incorrect combination of lecture and question ids");
            return status;
        }

        if (authorOfTheDeletionRequest.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user id or specified lecture does not exist!");
            return status;
        }

        Integer requestAuthorId = authorOfTheDeletionRequest.get().getKey().getId();
        Integer questionAuthorId = questionToBeDeleted.get().getStudentId();
        Integer requestAuthorRole = authorOfTheDeletionRequest.get().getRoleID();

        // 1 - lecturer
        // 2 - moderator
        // 3 - student

        // don't let students post questions once the lecture has ended
        if (authorOfTheDeletionRequest.get().getRoleID().equals(3)) {
            // don't let students delete questions once the lecture has ended
            if (!foundLecture.get().isLectureOngoing()) {
                status.put("success", false);
                status.put("message", "The lecture has ended.");
                return status;
            }
        }

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

        String logMessage = "User "
                + userId
                + " ("
                + authorOfTheDeletionRequest.get().getIpAddress()
                + ") deletes question "
                + questionId;
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        return status;
    }
    
    
    @Override
    public Map<String, Object> editQuestion(Integer lectureId, Integer questionId, String userId, String newContent) {
        Map<String, Object> status = new LinkedHashMap<>();

        if (questionId == null) {
            status.put("success", false);
            status.put("message", "Question id can't be null");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "Lecture id can't be null");
            return status;
        }

        if (newContent == null || newContent.length() <= 0 || newContent.length() > 420) {
            status.put("code", "422 UNPROCESSABLE ENTRY");
            status.put("success", false);
            status.put("message", "Question cannot be null and must be between 1 and 420 characters.");
            return status;
        }

        // get question and author of request from database.
        Optional<Question> questionToBeEdited = questionRepository.findById(new QuestionKey(questionId, lectureId));
        Optional<User> authorOfTheEditRequest = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);
        
        Integer requestAuthorLectureId = authorOfTheEditRequest.get().getKey().getLectureID();
        Integer questionAuthorLectureId = questionToBeEdited.get().getPrimaryKey().getLectureId();
        
        // If the lectures of user and question do not match.
        if (!requestAuthorLectureId.equals(questionAuthorLectureId)) {
            status.put("success", false);
            status.put("message", "The question in different lecture can't be edited!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized lecture.");
        }

        if (questionToBeEdited.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized question. Incorrect combination of lecture and question ids");
            return status;
        }

        if (authorOfTheEditRequest.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user id or specified lecture does not exist!");
            return status;
        }
        
        Integer requestAuthorId = authorOfTheEditRequest.get().getKey().getId();
        Integer questionAuthorId = questionToBeEdited.get().getStudentId();
        Integer requestAuthorRole = authorOfTheEditRequest.get().getRoleID();

        // 1 - lecturer
        // 2 - moderator
        // 3 - student

        //students cant edit questions
        if (requestAuthorRole.equals(3)) {
            status.put("code", "401 UNAUTHORIZED");
            status.put("success", false);
            status.put("message", "You are not authorized to edit this question!");
            return status;
        }
        
        questionRepository.flush();
        
        try {
            questionToBeEdited.get().setContent(newContent);
            questionRepository.save(questionToBeEdited.get());
            status.put("success", true);
            status.put("questionId", questionToBeEdited.get().getPrimaryKey().getId());
            status.put("message", "message was edited successfully!");
            
            questionRepository.flush();
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        String logMessage = "User "
                + userId
                + " ("
                + authorOfTheEditRequest.get().getIpAddress()
                + ") edits question "
                + questionId;
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        return status;
    }

    /**
     * Transform a question into JSON format.
     *
     * @param q an object of type Question
     * @return A JSON representation of the object
     */
    public Map<String, Object> transformQuestion(Question q, int lectureId) {
        if (q == null) {
            return null;
        }

        Optional<User> author = userRepository.findById(new UserKey(q.getStudentId(), lectureId));

        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("questionId", q.getPrimaryKey().getId());
        toBeReturned.put("userId", q.getStudentId());
        toBeReturned.put("userIp", q.getStudentIp());
        toBeReturned.put("userName", author.get().getName());
        toBeReturned.put("questionText", q.getContent());
        toBeReturned.put("score", q.getVoteCounter());
        toBeReturned.put("answered", q.getAnswered());
        toBeReturned.put("createdAt", q.getCreatedAt());

        if (q.getAnswered()) {
            toBeReturned.put("answerText", q.getAnswerText());
        }

        return toBeReturned;
    }
}
