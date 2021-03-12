package nl.tudelft.oopp.group54.repositories;

import nl.tudelft.oopp.group54.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

}
