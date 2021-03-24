package nl.tudelft.oopp.group54.repositories;

import java.util.List;
import nl.tudelft.oopp.group54.entities.Answer;
import nl.tudelft.oopp.group54.entities.AnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {
}
