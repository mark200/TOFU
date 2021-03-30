package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LectureFeedbackTest {

    static LectureFeedback lectureFeedback;
    static LectureFeedback lectureFeedbackDuplicate;
    static LectureFeedback lectureFeedback1;
    static LectureFeedbackKey lectureFeedbackKey;
    static LectureFeedbackKey lectureFeedbackKeyDuplicate;
    static LectureFeedbackKey lectureFeedbackKey1;
    static UserKey userKey;
    static UserKey userKeyDuplicate;
    static LectureFeedbackCode lectureFeedbackCode1 = LectureFeedbackCode.LECTURE_IS_TOO_FAST;
    static LectureFeedbackCode lectureFeedbackCode2 = LectureFeedbackCode.LECTURE_IS_TOO_SLOW;


    @BeforeEach
    public void init() {
        userKey = new UserKey(2, 3);
        userKeyDuplicate = new UserKey(2,3);
        lectureFeedback = new LectureFeedback(userKey, lectureFeedbackCode1.getValue());
        lectureFeedbackDuplicate = new LectureFeedback(userKeyDuplicate, lectureFeedbackCode1.getValue());
        lectureFeedback1 = new LectureFeedback(userKey, lectureFeedbackCode2.getValue());
        lectureFeedbackKey = new LectureFeedbackKey(userKey, lectureFeedbackCode1.getValue());
        lectureFeedbackKeyDuplicate = new LectureFeedbackKey(userKeyDuplicate, lectureFeedbackCode1.getValue());
        lectureFeedbackKey1 = new LectureFeedbackKey(userKey, lectureFeedbackCode2.getValue());
    }

    @Test
    public void testEquals() {
        assertEquals(lectureFeedback, lectureFeedbackDuplicate);
    }

    @Test
    public void equalsSameObjectTest() {
        assertEquals(lectureFeedback, lectureFeedback);
    }

    @Test
    public void equalsNullTest() {
        assertNotEquals(lectureFeedback, null);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(lectureFeedback, lectureFeedback1);
    }

    @Test
    public void equalsDifferentObjectTest() {
        assertNotEquals(lectureFeedback, "123");
    }

    @Test
    public void testEmptyLectureFeedbackConstructor() {
        LectureFeedback newLectureFeedback = new LectureFeedback();
        assertNotNull(newLectureFeedback);
    }

    @Test
    public void testLectureFeedbackConstructor() {
        assertNotNull(lectureFeedback);
    }

    @Test
    public void testUserKeyEmptyConstructor() {
        UserKey newKey = new UserKey();
        assertNotNull(newKey);
    }

    @Test
    public void getUserKeyTest() {
        assertEquals(new UserKey(2, 3), lectureFeedback.getUserKey());
    }

    @Test
    public void setUserKeyTest() {
        lectureFeedback.setUserKey(new UserKey(3, 4));
        assertEquals(3, lectureFeedback.getUserKey().getId());
        assertEquals(4, lectureFeedback.getUserKey().getLectureID());
    }

    @Test
    public void getLectureFeedbackCodeAsIntegerTest() {
        assertEquals(1, lectureFeedback.getLectureFeedbackCodeAsInteger());
    }

    @Test
    public void setLectureFeedbackCodeAsIntegerTest() {
        lectureFeedback.setLectureFeedbackCodeAsInteger(2);
        assertEquals(2, lectureFeedback.getLectureFeedbackCodeAsInteger());
    }

    @Test
    public void testLectureFeedbackHashcode() {
        assertEquals(2977,lectureFeedback.hashCode());
    }

    @Test
    public void testLectureFeedbackToString() {
        String expected = "LectureFeedback{userKey=UserKey{id=2, lecture_id=3}, lectureFeedbackCodeAsInteger=1}";
        assertEquals(expected, lectureFeedback.toString());
    }

    @Test
    public void equalsPrimaryKeyslectureFeedbackKeyTest() {
        assertEquals(lectureFeedbackKey, lectureFeedbackKeyDuplicate);
    }

    @Test
    public void equalsSameObjectLectureFeedbackKeyTest() {
        assertEquals(lectureFeedbackKey, lectureFeedbackKey);
    }

    @Test
    public void equalsNullLectureFeedbackKeyTest() {
        assertNotEquals(lectureFeedbackKey, null);
    }

    @Test
    public void notEqualsLectureFeedbackKeyTest() {
        assertNotEquals(lectureFeedbackKey, lectureFeedbackKey1);
    }

    @Test
    public void equalsDifferentObjectLectureFeedbackKeyTest() {
        assertNotEquals(lectureFeedbackKey, "123");
    }

    @Test
    public void testEmptyLectureFeedbackKeyConstructor() {
        LectureFeedbackKey newLectureFeedbackKey = new LectureFeedbackKey();
        assertNotNull(newLectureFeedbackKey);
    }

    @Test
    public void testLectureFeedbackKeyConstructor() {
        assertNotNull(lectureFeedbackKey);
    }

    @Test
    public void getUserKeyLectureFeedbackKeyTest() {
        assertEquals(new UserKey(2, 3), lectureFeedbackKey.getUserKey());
    }

    @Test
    public void setUserKeyLectureFeedbackKeyTest() {
        lectureFeedbackKey.setUserKey(new UserKey(3, 4));
        assertEquals(3, lectureFeedbackKey.getUserKey().getId());
        assertEquals(4, lectureFeedbackKey.getUserKey().getLectureID());
    }

    @Test
    public void getLectureFeedbackCodeTest() {
        assertEquals(1, lectureFeedbackKey.getLectureFeedbackCode());
    }

    @Test
    public void setLectureFeedbackCodeTest() {
        lectureFeedbackKey.setLectureFeedbackCode(LectureFeedbackCode.LECTURE_IS_TOO_SLOW);
        assertEquals(2, lectureFeedbackKey.getLectureFeedbackCode());
    }

    @Test
    public void testLectureFeedbackKeyHashcode() {
        assertEquals(2977,lectureFeedbackKey.hashCode());
    }

}
