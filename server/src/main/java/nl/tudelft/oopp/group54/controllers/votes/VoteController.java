package nl.tudelft.oopp.group54.controllers.votes;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.controllers.questions.MockQuestionServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/lectures/{lectureID}/questions/{questionID}/")
public class VoteController {

    @Autowired
    VoteServiceImpl voteService;

    @PostMapping(value = "votes",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> voteOnQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                              @PathVariable(value = "questionID") Integer questionId,
                                              @RequestBody Map<String, Object> requestPayload){
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId")
        );

        if(!containsNecessaryData){

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected isUpvote," +
                    " userId to be provided");

            return toBeReturned;
        }

        String userId;
        Boolean isUpvote;

        try {
            userId = (String) requestPayload.get("userId");
            isUpvote = (Boolean) requestPayload.get("upvote");
        } catch (Exception e){
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return voteService.voteOnQuestion(lectureId, userId, questionId, isUpvote);
    }
}
