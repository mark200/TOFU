package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BanTest {

    static Ban ban;
    static Ban banDuplicate;
    static BanKey key;
    static BanKey keyDuplicate;
    static Ban ban1;

    /**
     * Init.
     */
    @BeforeAll
    public static void init() {
        key = new BanKey("192.158.1.38", 321);
        keyDuplicate = new BanKey("192.158.1.38", 321);
        ban = new Ban(key);
        banDuplicate = new Ban(keyDuplicate);
        ban1 = new Ban(new BanKey("192.158.1.20", 302));

    }

    @Test
    public void equalsPrimaryKeys() {
        assertEquals(ban, banDuplicate);
    }

    @Test
    public void equalsSameObject() {
        assertEquals(ban, ban);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(ban, null);
    }

    @Test
    public void notEquals() {
        assertNotEquals(ban1, ban);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(ban, "123");
    }

}

