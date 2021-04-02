package nl.tudelft.oopp.group54.controllers.questions;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.controllers.lectures.LectureController;
import nl.tudelft.oopp.group54.entities.MapLoggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lectures")
public class QuestionController {

    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionServiceImplementation questionService;

    /**
     * Posts a question.
     * @param lectureId ID of lecture that the new question refers to
     * @param requestPayload JSON package that contains ID of User that is asking the question,
     *                       the content of the question and
     *                       the IP address of the User
     * @return
     */
    @PostMapping(value = "/{lectureID}/questions",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> postQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                            @RequestBody Map<String, Object> requestPayload,
                                            HttpServletRequest request) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "questionText")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId,"
                    + " userId and question Text to be provided");

            return toBeReturned;
        }

        String userId;
        String questionText;

        try {
            userId = (String) requestPayload.get("userId");
            questionText = (String) requestPayload.get("questionText");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        String userIp = request.getRemoteAddr();

        String logMessage = "User " + userId + " asked a question " + questionText;
        logger.info(logMessage);
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        return questionService.postQuestion(lectureId, userId, userIp, questionText);
    }


    @GetMapping(value = "/{lectureId}/questions",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getAllQuestions(@PathVariable(value = "lectureId") Integer lectureId,
                                               @RequestParam String userId) {
        return questionService.getAllQuestions(lectureId, userId);
    }

    /**
     * Deletes a question.
     * @param lectureId the lecture id
     * @param questionId the question to be deleted
     * @param userId the user id
     * @return
     */
    @DeleteMapping(value = "/{lectureId}/questions/{questionId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> deleteQuestion(@PathVariable(value = "lectureId") Integer lectureId,
                                              @PathVariable(value = "questionId") Integer questionId,
                                              @RequestParam String userId) {
        System.out.println("requested");

        String logMessage = "User " + userId + " deletes question " + questionId;
        logger.info(logMessage);
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        return questionService.deleteQuestion(lectureId, questionId, userId);
    }
    
    /**
     * Edits a question.
     * 
     * @param lectureId the lecture id
     * @param requestPayload the request body
     * @param userId the userId
     * @return 
     */
    @PostMapping(value = "/{lectureId}/questions/edit",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> editQuestion(@PathVariable(value = "lectureId") Integer lectureId,
                                            @RequestBody Map<String, Object> requestPayload,
                                            @RequestParam String userId) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("questionId", "newContent")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId and new Content to be provided");

            return toBeReturned;
        }
        
        if (!(requestPayload.get("questionId") instanceof String && requestPayload.get("questionId") instanceof String)) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId and new Content to be strings");

            return toBeReturned;
        }
        
        Integer questionId = Integer.parseInt((String)requestPayload.get("questionId"));
        String newContent = (String)requestPayload.get("newContent");

        String logMessage = "User " + userId + " edits question " + questionId;
        logger.info(logMessage);
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);
        
        return questionService.editQuestion(lectureId, questionId, userId, newContent);
    }
}
