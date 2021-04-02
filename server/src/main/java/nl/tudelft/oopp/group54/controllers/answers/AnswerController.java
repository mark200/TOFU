package nl.tudelft.oopp.group54.controllers.answers;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.controllers.bans.BanController;
import nl.tudelft.oopp.group54.entities.MapLoggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lectures")
public class AnswerController {

    private Logger logger = LoggerFactory.getLogger(BanController.class);

    @Autowired
    AnswerServiceImpl answerService;

    /**
     * Answer question map.
     *
     * @param lectureId      the lecture id
     * @param questionId     the question id
     * @param requestPayload the request payload
     * @return the map
     */
    @PostMapping(value = "{lectureID}/questions/{questionID}/answer",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> answerQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                              @PathVariable(value = "questionID") Integer questionId,
                                              @RequestBody Map<String, Object> requestPayload) {
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "answerText")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userId,"
                    + "  and answerText to be provided");

            return toBeReturned;
        }

        String userId;
        String answerText;

        try {
            userId = (String) requestPayload.get("userId");
            answerText = (String) requestPayload.get("answerText");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());
            toBeReturned.put("score", "catch");
            return toBeReturned;
        }

        String logMessage = "User " + userId + " answered question " + questionId;
        logger.info(logMessage);
        MapLoggers.getInstance().logWarning(lectureId, new Date() + " - " + logMessage);

        return answerService.answerQuestion(lectureId, userId, questionId, answerText);
    }
}
