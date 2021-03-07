package nl.tudelft.oopp.group54.repositories;

import nl.tudelft.oopp.group54.entities.Vote;
import nl.tudelft.oopp.group54.entities.VoteKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, VoteKey> {
}
