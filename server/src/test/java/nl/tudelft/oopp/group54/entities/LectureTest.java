package nl.tudelft.oopp.group54.entities;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.group54.entities.Lecture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LectureTest {
    static Lecture lecture1;
    static Lecture lecture2;
    static Lecture lecture3;


    /**
     * Init.
     */
    @BeforeAll
    public static void init() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture2 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture3 = new Lecture(12, "31", date, "123", "132", "123", true);
    }

    @Test
    public void equalsPrimaryKeys() {
        assertEquals(lecture1, lecture2);
        assertTrue(lecture1.equalsPrimaryKeys(lecture2));
    }

    @Test
    public void equalsSameObject() {
        assertEquals(lecture1, lecture1);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(lecture1, null);
    }

    @Test
    public void notEquals() {
        assertNotEquals(lecture1, lecture3);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(lecture1, "123");
    }


}
