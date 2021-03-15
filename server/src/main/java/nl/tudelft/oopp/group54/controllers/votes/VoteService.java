package nl.tudelft.oopp.group54.controllers.votes;

import java.util.Map;

public interface VoteService {
    Map<String, Object> voteOnQuestion(Integer lectureId,
                                       String userId,
                                       Integer questionId,
                                       boolean isUpvote);
}
