package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LectureTest {
    static Lecture lecture1;
    static Lecture lecture2;
    static Lecture lecture3;

    @Test
    public void testSetOngoingLecture() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setLectureOngoing(false);
        assertFalse(lecture1.isLectureOngoing());
    }

    @Test
    public void testSetId() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setId(124);
        assertEquals(124, lecture1.getId());
    }

    @Test
    public void equalsPrimaryKeys() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture2 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertEquals(lecture1, lecture2);
        assertTrue(lecture1.equalsPrimaryKeys(lecture2));
    }

    @Test
    public void equalsSameObject() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertEquals(lecture1, lecture1);
    }

    @Test
    public void equalsNull() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertNotNull(lecture1);
    }

    @Test
    public void notEquals() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture3 = new Lecture(12, "31", date, "123", "132", "123", true);
        assertNotEquals(lecture1, lecture3);
    }

    @Test
    public void equalsDifferentObject() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertNotEquals(lecture1, "123");
    }

    @Test
    public void testIsJoinable() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertTrue(lecture1.isJoinable());
    }

    @Test
    public void testIsLectureStarted() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertTrue(lecture1.isLectureStarted());
    }

    @Test
    public void testIsLectureOngoing() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertTrue(lecture1.isLectureOngoing());
    }

    @Test
    public void testGetLectureName() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertEquals("312", lecture1.getLectureName());
    }

    @Test
    public void testSetLectureName() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setLectureName("Matrix Multiplication");
        assertEquals("Matrix Multiplication", lecture1.getLectureName());
    }

    @Test
    public void testSetStartTime() {
        Date date1 = new Date();
        Date date2 = new Date();
        lecture1 = new Lecture(123, "312", date1, "123", "132", "123", true);
        lecture1.setStartTime(date2);
        assertEquals(date2, lecture1.getStartTime());
    }

    @Test
    public void testSetStudentJoinId() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setStudentJoinId("1234");
        assertEquals("1234", lecture1.getStudentJoinId());
    }

    @Test
    public void testSetLecturerJoinId() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setLecturerJoinId("1234");
        assertEquals("1234", lecture1.getLecturerJoinId());
    }

    @Test
    public void testSetModeratorJoinId() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        lecture1.setModeratorJoinId("1234");
        assertEquals("1234", lecture1.getModeratorJoinId());
    }

    @Test
    public void testEqualsPrimaryKeysNull() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertFalse(lecture1.equalsPrimaryKeys(null));
    }

    @Test
    public void testEqualsPrimaryKeysSameObject() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        assertTrue(lecture1.equalsPrimaryKeys(lecture1));
    }

    @Test
    public void testHashCode() {
        lecture1 = new Lecture(123, "312", null, "123", "132", "123", true);
        assertEquals(-370676018, lecture1.hashCode());
    }

    @Test
    public void testToString() {
        Date date = new Date();
        lecture1 = new Lecture(123, "312", date, "123", "132", "123", true);
        String expected = "Lecture{id=123, lectureName='312', startTime="
                + date + ", studentJoinId=123, moderatorJoinId=132, lecturerJoinId=123}";
        assertEquals(expected, lecture1.toString());
    }

}
