package nl.tudelft.oopp.group54.repositories;

import nl.tudelft.oopp.group54.entities.Answer;
import nl.tudelft.oopp.group54.entities.AnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {
}
