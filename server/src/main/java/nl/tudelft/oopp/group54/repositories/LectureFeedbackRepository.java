package nl.tudelft.oopp.group54.repositories;

import java.util.List;

import nl.tudelft.oopp.group54.entities.LectureFeedback;
import nl.tudelft.oopp.group54.entities.UserKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface LectureFeedbackRepository
        extends JpaRepository<LectureFeedback, UserKey> {

    @Query("SELECT lf FROM LectureFeedback lf WHERE lf.lectureFeedbackCodeAsInteger = ?1")
    List<LectureFeedback> findAllByLectureFeedbackCode(Integer lectureFeedbackCode);

    @Query("SELECT COUNT(lf) FROM LectureFeedback lf WHERE lf.lectureFeedbackCodeAsInteger = ?1")
    Integer countAllByLectureFeedbackCode(Integer lectureFeedbackCode);

    @Transactional
    @Modifying
    @Query("DELETE FROM LectureFeedback lf WHERE lf.lectureFeedbackCodeAsInteger = ?1")
    void deleteAllByLectureFeedbackCode(Integer lectureFeedbackCode);

}
