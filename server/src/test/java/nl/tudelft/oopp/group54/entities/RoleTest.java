package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RoleTest {

    private Role roleLec;
    private Role roleMod;
    private Role roleStud;

    @BeforeEach
    void setUp() {
        roleLec = new Role(1, "lecturer");
        roleMod = new Role(2, "moderator");
        roleStud = new Role(3, "student");
    }

    @Test
    public void testSetID() {
        roleLec.setId(4);
        assertEquals(4, roleLec.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("lecturer", roleLec.getName());
    }

    @Test
    public void testSetName() {
        roleLec.setName("new name");
        assertEquals("new name", roleLec.getName());
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(roleLec, roleLec);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(roleMod, null);
    }

    @Test
    public void notEquals() {
        assertNotEquals(roleLec, roleMod);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(roleLec, "123");
    }

    @Test
    public void testEqualsTwoRolesWithSameAttributes() {
        assertEquals(roleLec, new Role(1, "lecturer"));
    }

    @Test
    public void testRoleHashCode() {
        assertEquals(-2004703933, roleMod.hashCode());
    }

    @Test
    public void testRoleToString() {
        String expected = "Role{id=3 - name='student'}";
        assertEquals(expected, roleStud.toString());
    }
}