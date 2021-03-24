package nl.tudelft.oopp.group54.repositories;

import java.util.List;

import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface QuestionRepository extends JpaRepository<Question, QuestionKey> {
    List<Question> findByAnswered(Boolean answered);

    @Query("SELECT q FROM Question q WHERE q.primaryKey.lectureId = ?1")
    List<Question> findByLectureId(Integer lectureId);
}
