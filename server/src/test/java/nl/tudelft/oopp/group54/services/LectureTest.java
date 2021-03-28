package nl.tudelft.oopp.group54.services;

import java.util.Date;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@DataJpaTest
public class LectureTest {

    //@Autowired
    //private LectureRepository repository;
    static Lecture lecture1;

    @BeforeAll
    public static void init() {
        Date date = new Date();
    }
}