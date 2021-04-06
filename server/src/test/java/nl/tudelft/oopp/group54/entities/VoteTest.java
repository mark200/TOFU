package nl.tudelft.oopp.group54.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
    public void testVoteEmptyConstructor() {
        Vote newVote = new Vote();
        assertNotNull(newVote);
    }

    @Test
    public void testVoteKeyEmptyConstructor() {
        VoteKey newVoteKey = new VoteKey();
        assertNotNull(newVoteKey);
    }

    @Test
    public void testVoteKeySetStudentID() {
        VoteKey newVoteKey = new VoteKey(1, 1, 1);
        newVoteKey.setStudentID(2);
        assertEquals(2, newVoteKey.getStudentID());
    }

    @Test
    public void testVoteKeySetLectureID() {
        VoteKey newVoteKey = new VoteKey(1, 1, 1);
        newVoteKey.setLectureID(2);
        assertEquals(2, newVoteKey.getLectureID());
    }

    @Test
    public void testVoteKeySetQuestionID() {
        VoteKey newVoteKey = new VoteKey(1, 1, 1);
        newVoteKey.setQuestionID(2);
        assertEquals(2, newVoteKey.getQuestionID());
    }

    @Test
    public void testVoteKeyHashCode() {
        VoteKey newVoteKey = new VoteKey(1, 1, 1);
        assertEquals(993, newVoteKey.hashCode());
    }

    @Test
    public void testVoteKeyToString() {
        VoteKey newVoteKey = new VoteKey(1, 1, 1);
        String expected = "VoteKey{studentID = 1, lectureID = 1, roleID = 1}";
        assertEquals(expected, newVoteKey.toString());
    }

    @Test
    public void testVoteSetPrimaryKey() {
        vote1.setPrimaryKey(new VoteKey(2, 2, 2));
        assertEquals(new VoteKey(2, 2, 2), vote1.getPrimaryKey());
    }

    @Test
    public void testVoteKeyEqualsSameObject() {
        VoteKey newKey = new VoteKey(3, 4, 5);
        assertEquals(newKey, newKey);
    }

    @Test
    public void testVoteKeyNotEqualsDifferentInstance() {
        VoteKey newKey = new VoteKey(3, 4, 5);
        assertNotEquals(newKey, "string");
    }

    @Test
    public void testGetVoteValue() {
        Vote newVote = new Vote(new VoteKey(1, 2, 1), 1);
        assertEquals(1, newVote.getVoteValue());
    }

    @Test
    public void testSetVoteValue() {
        Vote newVote = new Vote(new VoteKey(1, 2, 1), 1);
        newVote.setVoteValue(2);
        assertEquals(2, newVote.getVoteValue());
    }

    @Test
    public void testVoteHashCode() {
        assertEquals(30784, vote1.hashCode());
    }

    @Test
    public void testVoteToString() {
        String expected = "Vote{primaryKey = "
                + "VoteKey{studentID = 1, "
                + "lectureID = 1, "
                + "roleID = 1}, "
                + "voteValue = 1}";
        assertEquals(expected, vote1.toString());
    }

    @Test
    public void testVoteKeyNotEqualsWrongLectureID() {
        Vote newVote = new Vote(new VoteKey(1, 2, 1), 1);
        assertNotEquals(vote1, newVote);
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