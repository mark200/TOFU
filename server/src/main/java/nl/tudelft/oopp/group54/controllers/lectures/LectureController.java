package nl.tudelft.oopp.group54.controllers.lectures;


import nl.tudelft.oopp.group54.controllers.ParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
            // Fixme: The Date constructor expects milliseconds. We should be able
            //   to differentiate between input given in milliseconds and seconds!
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


}
