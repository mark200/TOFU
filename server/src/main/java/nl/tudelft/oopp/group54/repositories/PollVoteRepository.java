package nl.tudelft.oopp.group54.repositories;

import java.util.List;
import nl.tudelft.oopp.group54.entities.PollVote;
import nl.tudelft.oopp.group54.entities.PollVoteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PollVoteRepository extends JpaRepository<PollVote, PollVoteKey> {

    @Query("SELECT v FROM PollVote v WHERE v.primaryKey.lectureId = ?1 AND v.primaryKey.pollId = ?2")
    public List<PollVote> findAllByLectureIdAndPollId(Integer lectureId, Integer pollId);
}
