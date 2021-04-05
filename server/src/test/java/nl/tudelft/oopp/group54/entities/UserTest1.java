package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserTest1 {
    private UserKey key = new UserKey(2, 3);
    private Date date = new Date();
    private User user = new User(key, "Ivan Ivanov", "127.0.0.1", date, 1);

    @Test
    public void testEmptyConstructor() {
        User newUser = new User();
        assertNotNull(newUser);
    }

    @Test
    public void testConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testKeyEmptyConstructor() {
        UserKey newKey = new UserKey();
        assertNotNull(newKey);
    }

    @Test
    public void getKeyTest() {
        assertEquals(new UserKey(2, 3), user.getKey());
    }

    @Test
    public void setKeyTest() {
        user.setKey(new UserKey(3, 4));
        assertEquals(3, user.getKey().getId());
        assertEquals(4, user.getKey().getLectureID());
    }

    @Test
    public void testGetterName() {
        assertEquals("Ivan Ivanov", user.getName());
    }

    @Test
    public void testSetterName() {
        user.setName("Pencho Penchev");
        assertEquals("Pencho Penchev", user.getName());
    }

    @Test
    public void testKeyCompareTo() {
        UserKey key1 = new UserKey(2, 3);
        assertEquals(0, key.compareTo(key1));
    }

    @Test
    public void testKeySetID() {
        UserKey key1 = new UserKey(2, 3);
        key1.setId(566);
        assertEquals(566, key1.getId());
    }

    @Test
    public void testGetLastQuestion() {
        assertEquals(date, user.getlastQuestion());
    }

    @Test
    public void testSetLastQuestion() {
        Date newDate = new Date();
        user.setLastQuestion(newDate);
        assertEquals(newDate, user.getlastQuestion());
    }

    @Test
    public void testKeySetLectureID() {
        UserKey key1 = new UserKey(2, 3);
        key1.setLectureID(566);
        assertEquals(566, key1.getLectureID());
    }

    @Test
    public void testKeyNotEqualsDifferentInstance() {
        assertNotEquals(key, "a, b, c");
    }

    @Test
    public void testGetterIpAddress() {
        assertEquals("127.0.0.1", user.getIpAddress());
    }

    @Test
    public void testSetterIpAddress() {
        user.setIpAddress("255.255.255.8");
        assertEquals("255.255.255.8", user.getIpAddress());
    }

    @Test
    public void testGetterRoleId() {
        assertEquals(1, user.getRoleID());
    }

    @Test
    public void testSetterRoleId() {
        user.setRoleID(2);
        assertEquals(2, user.getRoleID());
    }

    @Test
    public void testEquals() {
        User u1 = new User(key, "Ivan Ivanov", "127.0.0.1", date, 1);
        assertEquals(u1, user);
    }

    @Test
    public void testNotEquals() {
        User u1 = new User(new UserKey(3, 3), "Ivan Ivanov", "127.0.0.1", date, 1);
        assertNotEquals(u1, user);
    }

    @Test
    public void equalsSameObject() {
        assertEquals(user, user);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(user, null);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(user, "123");
    }

    @Test
    public void testKeyHashCode() {
        assertEquals(65, key.hashCode());
    }

    @Test
    public void testKeyToString() {
        assertEquals("UserKey{id=2, lecture_id=3}", key.toString());
    }

    @Test
    public void testUserHashCode() {
        User user1 = new User(key, "Ivan Ivanov", "127.0.0.1", new Date(0), 1);
        assertEquals(-1204285864, user1.hashCode());
    }

    @Test
    public void testUserToString() {
        User user1 = new User(key, "Ivan Ivanov", "127.0.0.1", null, 1);
        String expected = "User{primaryKey=UserKey{id=2, "
                + "lecture_id=3}, name='Ivan Ivanov', "
                + "ipAddress='127.0.0.1', "
                + "lastQuestion=null"
                + ", roleID=1}";
        assertEquals(expected, user1.toString());
    }

    @Test
    public void testNotEqualsWrongRole() {
        User user1 = new User(key, "Ivan Ivanov", "127.0.0.1", date, 2);
        assertFalse(user.equals(user1));
    }

    @Test
    public void testNotEqualsWrongName() {
        User user1 = new User(key, "Pencho Penchev", "127.0.0.1", date, 1);
        assertFalse(user.equals(user1));
    }

    @Test
    public void testNotEqualsWrongIpAddress() {
        User user1 = new User(key, "Ivan Ivanov", "255.255.255.255", date, 1);
        assertFalse(user.equals(user1));
    }
}
