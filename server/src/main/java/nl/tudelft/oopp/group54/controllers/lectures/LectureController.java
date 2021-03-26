package nl.tudelft.oopp.group54.controllers.lectures;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: Validation of input should be happening in a seperate object!

@RestController
@RequestMapping(value = "/lectures")
public class LectureController {

    @Autowired
    LectureServiceImpl lectureService;

    public void setLectureService(LectureServiceImpl service) {
        this.lectureService = service;
    }


    @PutMapping(
            value = "/e/{lectureId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> endLecture(@RequestParam String userId, @PathVariable String lectureId) {
        return lectureService.endLecture(Integer.parseInt(userId), Integer.parseInt(lectureId));
    }

    /**
     * Create new lecture map.
     *
     * @param requestPayload the request payload
     * @return the map
     */
    @PostMapping(
            value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> createNewLecture(@RequestBody Map<String, Object> requestPayload) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("startTime", "lectureName")
        );

        if (!containsNecessaryData) {

            // FIXME: This logic can be abstracted to an object that returns
            //  predefined errors. Better yet, we can have Error entities
            //  that we return instead of a Map<String, Object>.
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureName and startTime to be provided.");

            return toBeReturned;
        }

        Date startTime;
        String lectureName;

        try {
            long startTimeTimestamp = Long.parseLong(requestPayload.get("startTime").toString());
            startTime = new Date(startTimeTimestamp);
            lectureName = (String) requestPayload.get("lectureName");

            System.out.println(startTime.toString());

        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return lectureService.createNewLecture(startTime, lectureName);
    }

    /**
     * Join ongoing lecture map.
     *
     * @param lectureID      the lecture id
     * @param roleCode       the role code
     * @param requestPayload the request payload
     * @return the map
     */
    @PostMapping(
            value = "/j/{lectureID}/{roleCode}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> joinOngoingLecture(@PathVariable(value = "lectureID") Integer lectureID,
                                                  @PathVariable(value = "roleCode") String roleCode,
                                                  @RequestBody Map<String, Object> requestPayload) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Collections.singletonList("userName")
        );

        if (!containsNecessaryData) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userName to be provided.");
            return toBeReturned;
        }

        String userName;
        try {
            userName = (String) requestPayload.get("userName");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return lectureService.joinOngoingLecture(lectureID, roleCode, userName);
    }

    @GetMapping(
            value = "/{lectureID}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getLectureMetadata(@PathVariable("lectureID") Integer lectureID) {
        return lectureService.getLectureMetadata(lectureID);
    }

    /**
     * Post lecture feedback. If the poster is a student, it will add to the amount of people
     * who have the same feedback regarding a lecture. If the poster is a lecturer or moderator,
     * it will clear (remove) all feedback given on the specific matter.
     * @param lectureID The lecture for which this feedback is being given.
     * @param requestPayload The specifics of the actual feedback.
     *                       Check the LectureFeedbackCode enum for specifics.
     * @return Whether the operation was successful.
     */
    @PostMapping(
            value = "/{lectureID}/feedback",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> postLectureFeedback(@PathVariable("lectureID") Integer lectureID,
                                                   @RequestBody Map<String, Object> requestPayload) {

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "lectureFeedbackCode")
        );

        if (!containsNecessaryData) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userId and lectureFeedbackId to be provided.");
            return toBeReturned;
        }

        String userId;
        Integer lectureFeedbackCode;

        try {
            userId = requestPayload.get("userId").toString();
            lectureFeedbackCode = (Integer) requestPayload.get("lectureFeedbackCode");
        } catch (Exception e) {
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());
            return toBeReturned;
        }

        return lectureService.postLectureFeedback(lectureID, userId, lectureFeedbackCode);
    }

    @GetMapping(
            value = "/{lectureID}/feedback",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getLectureFeedback(@PathVariable("lectureID") Integer lectureID,
                                                  @RequestParam String userId) {

        return lectureService.getLectureFeedback(lectureID, userId);
    }

}
