package nl.tudelft.oopp.group54.controllers.polls;

import java.util.Map;

public interface PollService {
    Map<String, Object> postPoll(Integer lectureId, String userId, Integer optionCount, String correctAnswer, String title);
    
    Map<String, Object> votePoll(Integer lectureId, String userId, String vote);
    
    Map<String, Object> getCurrentPoll(Integer lectureId, String userId);
    
    Map<String, Object> endCurrentPoll(Integer lectureId, Integer userId);
    
    Map<String, Object> getStatistics(Integer lectureId, String userId);
}
