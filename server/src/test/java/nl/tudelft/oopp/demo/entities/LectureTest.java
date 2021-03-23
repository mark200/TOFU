package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.group54.entities.Lecture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LectureTest {
    static Lecture lecture1;
    static Lecture lecture2;



    @BeforeAll
    public static void init(){
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture2 = new Lecture(123, "312", date, "123", "132", "123", true);
    }

    @Test
    public void equalsPrimaryKeys(){
        assertEquals(lecture1, lecture2);
        assertTrue(lecture1.equalsPrimaryKeys(lecture2));
    }


}
