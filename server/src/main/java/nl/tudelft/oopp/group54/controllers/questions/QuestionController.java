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
    public Map<String, Object> getAllQuestions(@PathVariable(value = "lectureID") Long lectureID,
                                               @RequestParam String userId){


        List<Question> questions = questionService.getAllQuestions(lectureID, userId);
        Map<String, Object> toBeReturned = new TreeMap<>();
        Map<String, Object> innerObject = new TreeMap<>();
        List<Map<String, Object>> answeredList = new ArrayList<>();
        List<Map<String, Object>> unansweredList = new ArrayList<>();

        /**
         * Inflate answeredList
         */
        Map<String, Object> answeredQuestion = new TreeMap<>();
        answeredQuestion.put("userID", "ID of the user who asked the question");
        answeredQuestion.put("userName", "John Doe");
        answeredQuestion.put("questionText", "this is the actual text that comprises the question");
        answeredQuestion.put("answerText", "this field does not need to exist in the final response");
        answeredQuestion.put("score", 42);
        answeredQuestion.put("answered", false);

        answeredList.add(answeredQuestion);

        /**
         * Inflate unansweredList
         */
        Map<String, Object> unansweredQuestion = new TreeMap<>();
        unansweredQuestion.put("userID", "ID of the user who asked the question");
        unansweredQuestion.put("userName", "John Doe");
        unansweredQuestion.put("questionText", "this is the actual text that comprises the question");
        unansweredQuestion.put("score", 42);
        unansweredQuestion.put("answered", false);

        unansweredList.add(unansweredQuestion);




        innerObject.put("answered", answeredList);
        innerObject.put("unanswered", unansweredList);


        toBeReturned.put("sucess", true);
        toBeReturned.put("count", questions.size());
        toBeReturned.put("userId", 13); //FIXME: change to userName.
        toBeReturned.put("questions", innerObject);

        return toBeReturned;

    }
}