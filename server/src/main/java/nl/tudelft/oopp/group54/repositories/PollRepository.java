package nl.tudelft.oopp.group54.repositories;

import nl.tudelft.oopp.group54.entities.Poll;
import nl.tudelft.oopp.group54.entities.PollKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, PollKey> {

}
