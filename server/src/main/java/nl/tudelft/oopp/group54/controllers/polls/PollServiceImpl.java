package nl.tudelft.oopp.group54.controllers.polls;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.Poll;
import nl.tudelft.oopp.group54.entities.PollKey;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.PollRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    PollRepository pollRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public Map<String, Object> postPoll(Integer lectureId, String userId, Integer optionCount,
                                        String correctAnswer, String title) {
        Map<String, Object> status = new LinkedHashMap<>();

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }

        if (optionCount == null || optionCount < 2) {
            status.put("success", false);
            status.put("message", "optionCount cannot be null or be less than 2!");
            return status;
        }

        if (correctAnswer == null) {
            status.put("success", false);
            System.out.println(correctAnswer);
            status.put("message", "Question cannot be null and must be between 1 and 420 characters.");
            return status;
        }

        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }

        // don't let students make polls
        if (findUserRow.get().getRoleID().equals(3)) {
            if (!foundLecture.get().isLectureOngoing()) {
                status.put("success", false);
                status.put("message", "You are unauthorized to create a poll.");
                return status;
            }
        }

        System.out.println("this is executed");

        Poll newPoll = new Poll(new PollKey(null, lectureId), title, false,2, "asd", new Date());
        pollRepository.flush();

        try {
            pollRepository.save(newPoll);
            status.put("success", true);
            status.put("message", "Poll has been created");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;

    }
}
