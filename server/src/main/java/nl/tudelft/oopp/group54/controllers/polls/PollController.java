package nl.tudelft.oopp.group54.controllers.polls;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/lectures")
public class PollController {

    @Autowired
    PollServiceImpl pollService;

    /**
     * Posts a poll.
     * @param lectureId ID of lecture that the new question refers to
     * @param requestPayload JSON package that contains ID of User that is posting the poll,
     *                       the content of the poll
     *                       the number of choices
     *                       and the correct choice
     * @return
     */

    @PostMapping(value = "/{lectureID}/polls",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> postPoll(@PathVariable(value = "lectureID") Integer lectureId,
                                            @RequestBody Map<String, Object> requestPayload
                                        ) {

        System.out.println(requestPayload);
        System.out.println(requestPayload.get("title"));
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "optionCount", "correctAnswer", "title")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId,"
                    + " userId and question Text to be provided");

            return toBeReturned;
        }

        String userId;
        Integer optionCount;
        String correctAnswer;
        String title;

        try {
            userId = (String) requestPayload.get("userId");
            optionCount = (Integer) requestPayload.get("optionCount");
            correctAnswer = (String) requestPayload.get("correctAnswer");
            title = (String) requestPayload.get("title");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return pollService.postPoll(lectureId, userId, optionCount, correctAnswer, title);
    }
}
