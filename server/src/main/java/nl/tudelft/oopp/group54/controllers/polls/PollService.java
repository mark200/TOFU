package nl.tudelft.oopp.group54.controllers.polls;

import java.util.Map;

public interface PollService {
    Map<String, Object> postPoll(Integer lectureId, String userId, Integer optionCount, String correctAnswer, String title);
}
