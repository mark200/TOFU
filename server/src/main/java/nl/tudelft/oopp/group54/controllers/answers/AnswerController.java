package nl.tudelft.oopp.group54.controllers.answers;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/lectures")
public class AnswerController {
    AnswerService answerService = new MockAnswerServiceImpl();

    @PostMapping(value = "{lectureID}/questions/{questionID}/answer",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> answerQuestion(@PathVariable(value = "lectureID") Long lectureId,
                                              @PathVariable(value = "questionID") Long questionId,
                                              @RequestBody Map<String, Object> requestPayload){
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "answerText")
        );

        if(!containsNecessaryData){

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userId," +
                    "  and answerText to be provided");

            return toBeReturned;
        }

        String userId;
        String answerText;

        try {
            userId = (String) requestPayload.get("userId");
            answerText = (String) requestPayload.get("answerText");
        } catch (Exception e){
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());
            toBeReturned.put("score", "catch");
            return toBeReturned;
        }

        return answerService.answerQuestion(lectureId, userId, questionId, answerText);
    }
}
