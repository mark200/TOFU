package nl.tudelft.oopp.group54.repositories;

import java.util.List;
import nl.tudelft.oopp.group54.entities.PollVote;
import nl.tudelft.oopp.group54.entities.PollVoteKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollVoteRepository extends JpaRepository<PollVote, PollVoteKey> {

    public List<PollVote> findAllByLectureIdAndPollId(Integer lectureId, Integer pollId);
}
