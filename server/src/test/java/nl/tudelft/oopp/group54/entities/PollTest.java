package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PollTest {

    private static PollKey key;
    private static Poll poll;
    private static Date date = new Date();

    @BeforeEach
    public void init() {
        key = new PollKey(2, 3);
        poll = new Poll(key, "Title #1", false, 0, "Correct choice", date);
    }

    @Test
    public void testEmptyConstructorKey() {
        PollKey key1 = new PollKey();
        assertNotNull(key1);
    }

    @Test
    public void testConstructorKey() {
        assertNotNull(key);
    }

    @Test
    public void testKeyGetId() {
        assertEquals(2, key.getId());
    }

    @Test
    public void testKeySetId() {
        key.setId(5);
        assertEquals(5, key.getId());
    }

    @Test
    public void testKeyGetLectureId() {
        assertEquals(3, key.getLectureId());
    }

    @Test
    public void testKeySetLectureId() {
        key.setLectureId(56);
        assertEquals(56, key.getLectureId());
    }

    @Test
    public void testKeyCompareTo() {
        PollKey key1 = new PollKey(2, 3);
        assertEquals(0, key.compareTo(key1));
    }

    @Test
    public void testKeyEqualsSameObject() {
        assertEquals(key, key);
    }

    @Test
    public void testKeyEqualsNull() {
        assertNotEquals(key, null);
    }

    @Test
    public void testKeyEquals() {
        PollKey key1 = new PollKey(2, 3);
        assertEquals(key, key1);
    }

    @Test
    public void testKeyNotEquals() {
        PollKey key1 = new PollKey(4, 4);
        assertNotEquals(key, key1);
    }

    @Test
    public void testKeyHashCode() {
        PollKey key = new PollKey(3, 5);
        assertEquals(1059, key.hashCode());
    }

    @Test
    public void testEmptyConstructor() {
        Poll emptyPoll = new Poll();
        assertNotNull(emptyPoll);
    }

    @Test
    public void testConstructor() {
        assertNotNull(poll);
    }

    @Test
    public void testGetPrimaryKey() {
        assertEquals(new PollKey(2, 3), poll.getPrimaryKey());
    }

    @Test
    public void testSetPrimaryKey() {
        poll.setPrimaryKey(new PollKey(6, 9));
        assertEquals(6, poll.getPrimaryKey().getId());
        assertEquals(9, poll.getPrimaryKey().getLectureId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title #1", poll.getTitle());
    }

    @Test
    public void testSetTitle() {
        poll.setTitle("Title #2");
        assertEquals("Title #2", poll.getTitle());
    }

    @Test
    public void testGetClosed() {
        assertFalse(poll.getClosed());
    }

    @Test
    public void testSetClosed() {
        poll.setClosed(true);
        assertTrue(poll.getClosed());
    }

    @Test
    public void testGetOptionCount() {
        poll.setOptionCount(2);
        assertEquals(2, poll.getOptionCount());
    }

    @Test
    public void testSetCorrectChoice() {
        poll.setCorrectChoice("Another correct choice...");
        assertEquals("Another correct choice...", poll.getCorrectChoice());
    }

    @Test
    public void testSetCreatedAt() {
        Date newDate = new Date();
        poll.setCreatedAt(newDate);
        assertEquals(newDate, poll.getCreatedAt());
    }
}
