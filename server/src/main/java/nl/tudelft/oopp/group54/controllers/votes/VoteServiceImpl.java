package nl.tudelft.oopp.group54.controllers.votes;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.entities.Vote;
import nl.tudelft.oopp.group54.entities.VoteKey;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import nl.tudelft.oopp.group54.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    VoteRepository voteRepository;

    @Override
    public Map<String, Object> voteOnQuestion(Integer lectureId, String userId, Integer questionId, boolean isUpvote) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        if (lectureId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "LectureID cannot be null.");
            return toBeReturned;
        }

        if (userId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "UserID cannot be null.");
            return toBeReturned;
        }

        if (questionId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "QuestionID cannot be null.");
            return toBeReturned;
        }

        Optional<User> foundUser = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Question> foundQuestion = questionRepository.findById(new QuestionKey(questionId, lectureId));
        Optional<Vote> foundVote = voteRepository.findById(new VoteKey(Integer.parseInt(userId), lectureId, questionId));

        if (foundUser.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Unrecognized userID.");
            return toBeReturned;
        }

        if (foundQuestion.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "There does not exist a question with this ID.");
            return toBeReturned;
        }

        Vote newVote = null;

        if (foundVote.isEmpty()) {
            // This handles the case where users vote on their own question.
            if (userId.equals(foundQuestion.get().getStudentId().toString())) {
                toBeReturned.put("success", false);
                toBeReturned.put("message", "Users cannot vote on their own question");
                return toBeReturned;
            }
            newVote = new Vote(new VoteKey(Integer.parseInt(userId), lectureId, questionId), 1);
        } else {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Cannot vote more than once on the same question");
            return toBeReturned;
        }

        voteRepository.flush();
        questionRepository.flush();
        foundQuestion.get().setVoteCounter(foundQuestion.get().getVoteCounter() + 1);

        try {
            voteRepository.save(newVote);
            questionRepository.save(foundQuestion.get());
            toBeReturned.put("success", true);
        } catch (Exception e) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", e.toString());
        }

        return toBeReturned;
    }
}
