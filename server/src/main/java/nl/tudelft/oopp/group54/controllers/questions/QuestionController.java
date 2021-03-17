package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.demo.entities.Quote;

import nl.tudelft.oopp.group54.controllers.ParamResolver;
import nl.tudelft.oopp.group54.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                                            @RequestBody Map<String, Object> requestPayload){

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userId", "questionText")
        );

        if(!containsNecessaryData){

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected lectureId," +
                    " userId and question Text to be provided");

            return toBeReturned;
        }

        String userId;
        String questionText;

        try {
            userId = (String) requestPayload.get("userId");
            questionText = (String) requestPayload.get("questionText");
        } catch (Exception e){
            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());

            return toBeReturned;
        }

        return questionService.postQuestion(lectureId, userId, questionText);
    }



    @GetMapping(value = "/{lectureID}/questions",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getAllQuestions(@PathVariable(value = "lectureID") Integer lectureID,
                                               @RequestBody Map<String, Object> requestPayload){

        boolean containsNecessaryData = ParamResolver.checkContainsRequiredParams(
                requestPayload,
                Arrays.asList("userID")
        );

        if(!containsNecessaryData){

            Map<String, Object> toBeReturned = new TreeMap<>();
            toBeReturned.put("success", "false");
            toBeReturned.put("message", "Expected userID" +
                    " to be provided");

            return toBeReturned;
        }

        String userId;

        Map<String, Object> toBeReturned = new TreeMap<>();
        List<Question> questions;
        try {
            userId = (String) requestPayload.get("userID");
        } catch (Exception e){
            toBeReturned.put("success", "false");
            toBeReturned.put("message", e.getMessage());
            return toBeReturned;
        }

        return questionService.getAllQuestions(lectureID, userId);
    }
}