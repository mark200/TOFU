package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoteTest {

    private Vote vote1;
    private Vote vote2;
    private Vote vote3;

    @BeforeEach
    void setUp() {
        vote1 = new Vote(new VoteKey(1, 1, 1), 1);
        vote2 = new Vote(new VoteKey(1, 1, 1), 1);
        vote3 = new Vote(new VoteKey(2, 2, 2), 2);
    }

    @Test
    public void testEquals() {
        assertEquals(vote1, vote2);
    }

    @Test
    public void equalsSameObject() {
        assertEquals(vote1, vote1);
    }

    @Test
    public void equalsNull() {
        assertNotEquals(vote1, null);
    }

    @Test
    public void notEquals() {
        assertNotEquals(vote1, vote3);
    }

    @Test
    public void equalsDifferentObject() {
        assertNotEquals(vote1, "123");
    }
}