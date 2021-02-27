package nl.tudelft.oopp.group54.controller;

import nl.tudelft.oopp.group54.entities.Question;
import org.springframework.web.bind.annotation.ResponseBody;

public class MockQuestionServiceImplementation implements QuestionService {

    @Override
    @ResponseBody
    public String postQuestion(String lectureId, String userId, String questionText) {
        return null;
    }

    @Override
    public String voteOnQuestion(String lectureId, String userId, String questionId, boolean isUpvote) {
        return null;
    }

    @Override
    public String answerQuestion(String lectureId, String userId, String questionId, String answerText) {
        return null;
    }

    @Override
    public Question getAllQuestions(String lectureId, String userId) {
        return null;
    }
}
