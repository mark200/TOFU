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
    static LectureFeedback lectureFeedback2;
    static LectureFeedbackKey lectureFeedbackKey;
    static LectureFeedbackKey lectureFeedbackKeyDuplicate;
    static LectureFeedbackKey lectureFeedbackKey1;
    static LectureFeedbackKey lectureFeedbackKey2;
    static UserKey userKey;
    static UserKey userKeyDuplicate;
    static UserKey userKey2;
    static LectureFeedbackCode lectureFeedbackCode1 = LectureFeedbackCode.LECTURE_IS_TOO_FAST;
    static LectureFeedbackCode lectureFeedbackCode2 = LectureFeedbackCode.LECTURE_IS_TOO_SLOW;


    /**
     * Init.
     */
    @BeforeEach
    public void init() {
        userKey = new UserKey(2, 3);
        userKeyDuplicate = new UserKey(2,3);
        userKey2 = new UserKey(3, 3);
        lectureFeedback = new LectureFeedback(userKey, lectureFeedbackCode1.getValue());
        lectureFeedbackDuplicate = new LectureFeedback(userKeyDuplicate, lectureFeedbackCode1.getValue());
        lectureFeedback1 = new LectureFeedback(userKey, lectureFeedbackCode2.getValue());
        lectureFeedback2= new LectureFeedback(userKey2, lectureFeedbackCode1.getValue());
        lectureFeedbackKey = new LectureFeedbackKey(userKey, lectureFeedbackCode1.getValue());
        lectureFeedbackKeyDuplicate = new LectureFeedbackKey(userKeyDuplicate, lectureFeedbackCode1.getValue());
        lectureFeedbackKey2= new LectureFeedbackKey(userKey2, lectureFeedbackCode1.getValue());
        lectureFeedbackKey1 = new LectureFeedbackKey(userKey, lectureFeedbackCode2.getValue());
    }


    @Test
    public void testEquals() {
        assertNotEquals(lectureFeedback, "123");
        assertNotEquals(lectureFeedback, lectureFeedback1);
        assertNotEquals(lectureFeedback, null);
        assertNotEquals(lectureFeedback, lectureFeedback2);
        assertEquals(lectureFeedback, lectureFeedback);
        assertEquals(lectureFeedback, lectureFeedbackDuplicate);
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
    public void equalsLectureFeedbackKey() {
        assertEquals(lectureFeedbackKey, lectureFeedbackKeyDuplicate);
        assertEquals(lectureFeedbackKey, lectureFeedbackKey);
        assertNotEquals(lectureFeedbackKey, null);
        assertNotEquals(lectureFeedbackKey, lectureFeedbackKey1);
        assertNotEquals(lectureFeedbackKey, "123");
        assertNotEquals(lectureFeedbackKey, lectureFeedbackKey2);
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
