package nl.tudelft.oopp.group54.controllers.bans;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.controllers.lectures.LectureController;
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
public class BanController {

    private Logger logger = LoggerFactory.getLogger(BanController.class);

    @Autowired
    BanServiceImpl banService;

    /**
     * An API call for banning users by their IP address.
     * @param lectureId ID of lecture where the user is misbehaving
     * @param questionId ID of question
     * @param requestPayload JSON payload that contains the IP address
     *                       of the User
     * @return
     */
    @PostMapping(value = "{lectureID}/questions/{questionID}/ban",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> banIp(@PathVariable(value = "lectureID") Integer lectureId,
                                              @PathVariable(value = "questionID") Integer questionId,
                                              @RequestBody Map<String, Object> requestPayload) {
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userIp")
        );

        if (!containsNecessaryData) {

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userIp"
                    + " to be provided");

            return toBeReturned;
        }

        String userIp;

        try {
            userIp = (String) requestPayload.get("userIp");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        String logMessage = "User with IP address " + userIp + " was banned for question " + questionId;
        logger.info(logMessage);
        MapLoggers.getInstance().logWarning(lectureId,
                new Date() + " - " + logMessage,
                "server/Logs/" + lectureId + ".log");

        return banService.banIp(lectureId, questionId, userIp);
    }
}
