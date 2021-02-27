package nl.tudelft.oopp.group54.controllers.lectures;


import nl.tudelft.oopp.group54.controllers.ParamResolver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


// TODO: Validation of input should be happening in a seperate object!

@RestController
@RequestMapping(value="/lectures")
public class LectureController {

  LectureService lectureService = new MockLectureServiceImplementation();

  @PostMapping(
          value = "",
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public Map<String, Object> createNewLecture(@RequestBody Map<String, Object> requestPayload) {

    boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
            requestPayload,
            Arrays.asList("startTime", "lectureName")
    );

    if(!containsNecessaryData) {

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
      long startTimeTimestamp = (long) requestPayload.get("startTime");
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
          value = "/j/{lectureID}/{userID}",
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public Map<String, Object> joinOngoingLecture(@PathVariable(value="lectureID") Long lectureID,
                                                @PathVariable(value="userID") Long userID,
                                                @RequestBody Map<String, Object> requestPayload) {

    boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
            requestPayload,
            Collections.singletonList("userName")
    );

    if(!containsNecessaryData) {
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

    return lectureService.joinOngoingLecture(lectureID, userID, userName);
  }



}
