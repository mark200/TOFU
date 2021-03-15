package nl.tudelft.oopp.group54.controllers.votes;

import nl.tudelft.oopp.group54.entities.*;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import nl.tudelft.oopp.group54.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
            newVote = new Vote(new VoteKey(Integer.parseInt(userId), lectureId, questionId), 0);
        } else {
            newVote = foundVote.get();
            if (isUpvote) {
                newVote.setVoteValue(newVote.getVoteValue() + 1);
            } else {
                newVote.setVoteValue(newVote.getVoteValue() - 1);
            }
        }

        voteRepository.flush();

        try {
            voteRepository.save(newVote);
            toBeReturned.put("success", true);
        } catch (Exception e) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", e.toString());
        }

        return toBeReturned;
    }
}
