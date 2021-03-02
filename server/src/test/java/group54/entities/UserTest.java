package group54.entities;

import nl.tudelft.oopp.group54.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private Date date = new Date();
    private User u = new User(123, "Ivan Ivanov", 57, "127.0.0.1", date, 1);

    @Test
    public void testConstructor() {
        assertNotNull(u);
    }

    @Test
    public void testGetterId() {
        assertEquals(123, u.getId());
    }

    @Test
    public void testSetterId() {
        u.setId(124);
        assertEquals(124, u.getId());
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
    public void testGetterLectureId() {
        assertEquals(57, u.getLectureID());
    }

    @Test
    public void testSetterLectureId() {
        u.setLectureID(58);
        assertEquals(58, u.getLectureID());
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
        User u1 = new User(123, "Ivan Ivanov", 57, "127.0.0.1", date, 1);
        assertEquals(u1, u);
    }

    @Test
    public void testNotEquals() {
        User u1 = new User(124, "Ivan Ivanovv", 69, "127.0.0.1", date, 1);
        assertNotEquals(u1, u);
    }
}
