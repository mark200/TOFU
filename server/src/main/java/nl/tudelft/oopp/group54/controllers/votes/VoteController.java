package nl.tudelft.oopp.group54.controllers.votes;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/lectures/{lectureID}/questions/{questionID}/")
public class VoteController {
    VoteService voteService = new MockVoteServiceImpl();

    @PostMapping(value = "votes",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> answerQuestion(@PathVariable(value = "lectureID") Long lectureId,
                                              @PathVariable(value = "questionID") Long questionId,
                                              @RequestBody Map<String, Object> requestPayload){
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userID", "isUpvote")
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
            userId = (String) requestPayload.get("userID");
            isUpvote = (Boolean) requestPayload.get("isUpvote");
        } catch (Exception e){
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return voteService.voteOnQuestion(lectureId, userId, questionId, isUpvote);
    }
}
