package nl.tudelft.oopp.group54.controllers.moderator;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r/lectures")
public class ModeratorController {

    @Autowired
    MockModeratorServiceImplementation moderatorService;

    /**
     * Ban student ip map.
     *
     * @param lectureID      the lecture id
     * @param requestPayload the request payload
     * @return the map
     */
    @PutMapping(
            value = "/{lectureID}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> banStudentIP(@PathVariable("lectureID") Long lectureID,
                                            @RequestBody Map<String, Object> requestPayload) {
        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userID")
        );

        if (!containsNecessaryData) {

            // FIXME: This logic can be abstracted to an object that returns
            //  predefined errors. Better yet, we can have Error entities
            //  that we return instead of a Map<String, Object>.
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Here's why you couldn't ban this person");

            return toBeReturned;
        }

        String userID = "123456";

        return moderatorService.banStudentIP(lectureID, userID);
    }
}
