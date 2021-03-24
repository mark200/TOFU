package nl.tudelft.oopp.demo.entities;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

public class UserTest {
    private UserKey key = new UserKey(2, 3);
    private Date date = new Date();
    private User user = new User(key, "Ivan Ivanov", "127.0.0.1", date, 1);

    @Test
    public void testConstructor() {
        assertNotNull(user);
    }

    @Test
    public void getKeyTest() {
        assertEquals(new UserKey(2, 3), user.getKey());
    }

    @Test
    public void setKeyTest() {
        UserKey newKey = new UserKey(3, 4);
        user.setKey(newKey);
        assertEquals(newKey, user.getKey());
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
}

