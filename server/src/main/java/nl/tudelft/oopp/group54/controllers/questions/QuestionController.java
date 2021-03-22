package nl.tudelft.oopp.group54.controllers.questions;

        import nl.tudelft.oopp.group54.controllers.ParamResolver;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.*;

        import java.util.Arrays;
        import java.util.Map;
        import java.util.TreeMap;

@RestController
@RequestMapping(value = "/lectures")
public class QuestionController {


//     @Autowired
//     MockQuestionServiceImplementation questionService;

    @Autowired
    QuestionServiceImplementation questionService;

    @PostMapping(value = "/{lectureID}/questions",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> postQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                            @RequestBody Map<String, Object> requestPayload) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "questionText", "UserIp")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId," +
                    " userId and question Text to be provided");

            return toBeReturned;
        }

        String userId;
        String questionText;
        String userIp;

        try {
            userId = (String) requestPayload.get("userId");
            questionText = (String) requestPayload.get("questionText");
            userIp = (String) requestPayload.get("userIp");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return questionService.postQuestion(lectureId, userId, questionText, userIp);
    }


    @GetMapping(value = "/{lectureId}/questions",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getAllQuestions(@PathVariable(value = "lectureId") Integer lectureId,
                                               @RequestParam String userId) {
        return questionService.getAllQuestions(lectureId, userId);
    }

    @DeleteMapping(value = "/{lectureId}/questions/{questionId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> deleteQuestion(@PathVariable(value = "lectureId") Integer lectureId,
                                              @PathVariable(value = "questionId") Integer questionId,
                                              @RequestParam String userId) {
        System.out.println("requested");
        return questionService.deleteQuestion(lectureId, questionId, userId);
    }
}
