package nl.tudelft.oopp.group54.controllers.bans;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/lectures")
public class BanController {
    @Autowired
    BanServiceImpl banService;

    @PostMapping(value = "{lectureID}/questions/{questionID}/ban",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> answerQuestion(@PathVariable(value = "lectureID") Integer lectureId,
                                              @PathVariable(value = "questionID") Integer questionId,
                                              @RequestBody Map<String, Object> requestPayload) {
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userIp")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userIp" +
                    " to be provided");

            return toBeReturned;
        }

        String userIp;

        try {
            userIp = (String) requestPayload.get("userIp");
        } catch (Exception e){
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return banService.banIp(lectureId, questionId, userIp);
    }
}
