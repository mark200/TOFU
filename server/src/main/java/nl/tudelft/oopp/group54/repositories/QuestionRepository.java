package nl.tudelft.oopp.group54.repositories;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, QuestionKey> {
    List<Question> findByAnswered(Boolean answered);
}
