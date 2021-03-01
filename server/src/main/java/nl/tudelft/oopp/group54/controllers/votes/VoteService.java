package nl.tudelft.oopp.group54.controllers.votes;

import java.util.Map;

public interface VoteService {
    Map<String, Object> voteOnQuestion(Long lectureId,
                                       String userId,
                                       Long questionId,
                                       boolean isUpvote);
}
