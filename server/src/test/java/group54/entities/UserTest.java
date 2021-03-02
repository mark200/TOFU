package group54.entities;

import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private UserKey key = new UserKey(2, 3);
    private Date date = new Date();
    private User u = new User(key, "Ivan Ivanov", "127.0.0.1", date, 1);

    @Test
    public void testConstructor() {
        assertNotNull(u);
    }

    @Test
    public void getKeyTest() {
        assertEquals(new UserKey(2, 3), u.getKey());
    }

    @Test
    public void setKeyTest() {
        UserKey newKey = new UserKey(3, 4);
        u.setKey(newKey);
        assertEquals(newKey, u.getKey());
    }

    @Test
    public void testGetterName() {
        assertEquals("Ivan Ivanov", u.getName());
    }

    @Test
    public void testSetterName() {
        u.setName("Pencho Penchev");
        assertEquals("Pencho Penchev", u.getName());
    }

    @Test
    public void testGetterIpAddress() {
        assertEquals("127.0.0.1", u.getIpAddress());
    }

    @Test
    public void testSetterIpAddress() {
        u.setIpAddress("255.255.255.8");
        assertEquals("255.255.255.8", u.getIpAddress());
    }

    @Test
    public void testGetterRoleId() {
        assertEquals(1, u.getRoleID());
    }

    @Test
    public void testSetterRoleId() {
        u.setRoleID(2);
        assertEquals(2, u.getRoleID());
    }

    @Test
    public void testEquals() {
        User u1 = new User(key, "Ivan Ivanov", "127.0.0.1", date, 1);
        assertEquals(u1, u);
    }

    @Test
    public void testNotEquals() {
        User u1 = new User(new UserKey(3, 3), "Ivan Ivanov", "127.0.0.1", date, 1);
        assertNotEquals(u1, u);
    }
}
