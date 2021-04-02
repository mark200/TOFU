package nl.tudelft.oopp.group54.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.group54.entities.Poll;
import nl.tudelft.oopp.group54.entities.PollKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PollRepository extends JpaRepository<Poll, PollKey> {

    @Query("SELECT p FROM Poll p WHERE p.primaryKey.lectureId = ?1 AND p.closed = FALSE")
    public List<Poll> findOpenPoll(Integer lectureId);
    
    @Query("SELECT p FROM Poll p WHERE p.primaryKey.lectureId = ?1")
    public List<Poll> findAllByLectureId(Integer lectureId);

    //    Optional<Poll> findTopByOrderByIdDesc();
}
