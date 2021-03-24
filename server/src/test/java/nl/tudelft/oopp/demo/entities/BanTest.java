package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BanTest {

    static Ban ban;
    static Ban banDuplicate;
    static BanKey key;
    static BanKey keyDuplicate;

    @BeforeAll
    public static void init(){
        key = new BanKey("192.158.1.38", 321);
        keyDuplicate = new BanKey("192.158.1.38", 321);
        ban = new Ban(key);
        banDuplicate = new Ban(keyDuplicate);

    }

    @Test
    public void equalsPrimaryKeys(){
        assertEquals(ban, banDuplicate);
    }

}

