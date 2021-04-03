package nl.tudelft.oopp.group54.controllers.votes;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.controllers.questions.MockQuestionServiceImplementation;
import nl.tudelft.oopp.group54.controllers.questions.QuestionController;
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
@RequestMapping(value = "/lectures/{lectureID}/questions/{questionID}/")
public class VoteController {

    private Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    VoteServiceImpl voteService;

    /**
     * An API call for a User to vote on a Question.
     * @param lectureId ID of the lecture where the question has been asked
     * @param questionId ID of the question for which the user wants to vote
     * @param requestPayload Payload that contains the ID of the user that is voting
     * @return
     */
    @PostMapping(value = "votes",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> voteOnQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                              @PathVariable(value = "questionID") Integer questionId,
                                              @RequestBody Map<String, Object> requestPayload) {
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected isUpvote,"
                    + " userId to be provided");

            return toBeReturned;
        }

        String userId;
        Boolean isUpvote;

        try {
            userId = (String) requestPayload.get("userId");
            isUpvote = (Boolean) requestPayload.get("upvote");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        String logMessage = "User " + userId + " votes on question " + questionId;
        logger.info(logMessage);

        return voteService.voteOnQuestion(lectureId, userId, questionId, isUpvote);
    }
}
