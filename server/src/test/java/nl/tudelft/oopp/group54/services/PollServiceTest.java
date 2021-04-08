package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.polls.PollServiceImpl;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.Poll;
import nl.tudelft.oopp.group54.entities.PollKey;
import nl.tudelft.oopp.group54.entities.PollVote;
import nl.tudelft.oopp.group54.entities.PollVoteKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.PollRepository;
import nl.tudelft.oopp.group54.repositories.PollVoteRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PollServiceTest {

    private Date date;
    private Date date1;

    private Lecture lecture1;
    private Lecture lecture2;
    private User user1;
    private User student1;
    private Poll poll;
    private Poll poll1;
    private PollKey pollKey;

    @Mock
    PollRepository pollRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LectureRepository lectureRepository;

    @Mock
    PollVoteRepository pollVoteRepository;

    @InjectMocks
    PollServiceImpl pollService;

    /**
     * Executes before each test.
     */
    @BeforeEach
    public void init() {
        date = new Date(1617841943);
        date1 = new Date(1617842044);

        user1 = new User(new UserKey(1,1), "Bob", "127.0.0.1", new Date(), 1);
        student1 = new User(new UserKey(1,1), "Ivan", "127.0.0.1", new Date(), 3);

        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join",
                "lecturer-join", false);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join",
                "lecturer-join", true);

        pollKey = new PollKey(1, 2);
        poll = new Poll(pollKey, "title", false, 3, "A", date);
        poll1 = new Poll(pollKey, "title", true, 3, "A", date1);
    }

    @Test
    public void testPostPollLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.postPoll(null, "1", 1, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.postPoll(1, null, 1, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollOptionCountNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "optionCount cannot be null and must be between 2 and 10!");

        Map<String, Object> actual = pollService.postPoll(1, "1", null, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollOptionCountBellow2() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "optionCount cannot be null and must be between 2 and 10!");

        Map<String, Object> actual = pollService.postPoll(1, "1", 1, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollOptionCountMoreThan10() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "optionCount cannot be null and must be between 2 and 10!");

        Map<String, Object> actual = pollService.postPoll(1, "1", 11, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollCorrectAnswerNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Question cannot be null and must be between 1 and 420 characters.");

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, null, "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollNoUser() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollStudentCannotPostAPollOngoingLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There is currently an ongoing poll!");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(new Poll());

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture2));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollStudentCannotPostAPoll() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "You are unauthorized to create a poll/quiz.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollStudentNoPoll() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There is currently an ongoing poll!");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(new Poll());

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollException() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "java.lang.IllegalArgumentException");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.save(any(Poll.class))).thenThrow(new IllegalArgumentException());

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testPostPollSuccessful() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("message", "Poll/quiz has been created");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, "abc", "title");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.votePoll(null, "1", "A");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.votePoll(2, null, "A");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollVoteIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Vote must be A character between A and J!");

        Map<String, Object> actual = pollService.votePoll(2, "1", null);

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollNonExistentVote() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Vote must be A character between A and J!");

        Map<String, Object> actual = pollService.votePoll(2, "1", "Z");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollUserRowEmpty() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.votePoll(1, "1", "A");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.votePoll(1, "1", "A");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollNoOngoingPoll() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There is currently no ongoing poll!");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.votePoll(1, "1", "A");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollWrongChoice() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Vote must be between A and C!");

        List<Poll> pollList = new ArrayList<>();
        Poll newPoll = new Poll();
        newPoll.setOptionCount(3);
        pollList.add(newPoll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.votePoll(1, "1", "D");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollMoreThanOneVote() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "You can only vote once per poll/quiz!");

        List<Poll> pollList = new ArrayList<>();
        Poll newPoll = new Poll();
        newPoll.setPrimaryKey(new PollKey(1, 2));
        newPoll.setOptionCount(3);
        pollList.add(newPoll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);
        when(pollVoteRepository.findById(any(PollVoteKey.class))).thenReturn(Optional.of(new PollVote()));

        Map<String, Object> actual = pollService.votePoll(1, "1", "C");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollSuccessful() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("message", "Vote was placed");

        List<Poll> pollList = new ArrayList<>();
        Poll newPoll = new Poll();
        newPoll.setPrimaryKey(new PollKey(1, 2));
        newPoll.setOptionCount(3);
        pollList.add(newPoll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);
        when(pollVoteRepository.findById(any(PollVoteKey.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.votePoll(1, "1", "C");

        assertEquals(expected, actual);
    }

    @Test
    public void testVotePollException() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "java.lang.IllegalArgumentException");

        List<Poll> pollList = new ArrayList<>();
        Poll newPoll = new Poll();
        newPoll.setPrimaryKey(new PollKey(1, 2));
        newPoll.setOptionCount(3);
        pollList.add(newPoll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);
        when(pollVoteRepository.findById(any(PollVoteKey.class))).thenReturn(Optional.empty());
        when(pollVoteRepository.save(any(PollVote.class))).thenThrow(new IllegalArgumentException());

        Map<String, Object> actual = pollService.votePoll(1, "1", "C");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.getCurrentPoll(null, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.getCurrentPoll(1, null);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollNoUser() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.getCurrentPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.getCurrentPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollNoPollOngoing() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("closed", true);
        expected.put("message", "There is no poll ongoing!");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getCurrentPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentPollSuccess() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("closed", false);
        expected.put("optionCount", 3);
        expected.put("title", "title");
        expected.put("pollId", 1);

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getCurrentPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.endCurrentPoll(null, 123);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.endCurrentPoll(1, null);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollNoUser() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.endCurrentPoll(1, 123);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.endCurrentPoll(1, 123);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollNoOngoingPoll() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There is currently no ongoing poll/quiz!");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.endCurrentPoll(1,123);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollSuccessful() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("message", "Poll/Quiz was closed");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.endCurrentPoll(1,123);

        assertEquals(expected, actual);
    }

    @Test
    public void testEndCurrentPollException() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "java.lang.IllegalArgumentException");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findOpenPoll(1)).thenReturn(pollList);
        when(pollRepository.save(any(Poll.class))).thenThrow(new IllegalArgumentException());

        Map<String, Object> actual = pollService.endCurrentPoll(1,123);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.getStatistics(null, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.getStatistics(1, null);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsNoUser() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsOnePollNotClosed() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "This poll is still open");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsNewPollsNotClosed() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "This poll is still open");

        Poll poll2 = new Poll(new PollKey(34, 3), "some title", false, 3, "B", date1);
        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        pollList.add(poll2);
        pollList.add(poll2);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsNoPolls() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There has not been a poll/quiz");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsSuccessfulByStudent() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "This poll is still open");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsSuccessfulByOneStudent() {
        Map<String, Integer> voteMap = new TreeMap<>();
        voteMap.put("A", 2);
        voteMap.put("B", 0);
        voteMap.put("C", 1);

        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("correctAnswer", "");
        expected.put("statsMap", voteMap);
        expected.put("voteCount", 3);
        expected.put("optionCount", 3);

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        pollList.add(poll1);
        pollList.add(poll1);

        List<PollVote> pollVoteList = new ArrayList<>();
        PollVote pollVote1 = new PollVote();
        pollVote1.setVote("A");
        PollVote pollVote2 = new PollVote();
        pollVote2.setVote("A");
        PollVote pollVote3 = new PollVote();
        pollVote3.setVote("C");
        pollVoteList.add(pollVote1);
        pollVoteList.add(pollVote2);
        pollVoteList.add(pollVote3);
        when(pollVoteRepository.findAllByLectureIdAndPollId(1, 1)).thenReturn(pollVoteList);


        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatisticsSuccessfulNotByStudent() {
        Map<String, Integer> voteMap = new TreeMap<>();
        voteMap.put("A", 2);
        voteMap.put("B", 0);
        voteMap.put("C", 1);

        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("correctAnswer", "A");
        expected.put("statsMap", voteMap);
        expected.put("voteCount", 3);
        expected.put("optionCount", 3);

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);

        List<PollVote> pollVoteList = new ArrayList<>();
        PollVote pollVote1 = new PollVote();
        pollVote1.setVote("A");
        PollVote pollVote2 = new PollVote();
        pollVote2.setVote("A");
        PollVote pollVote3 = new PollVote();
        pollVote3.setVote("C");
        pollVoteList.add(pollVote1);
        pollVoteList.add(pollVote2);
        pollVoteList.add(pollVote3);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);
        when(pollVoteRepository.findAllByLectureIdAndPollId(1, 1)).thenReturn(pollVoteList);

        Map<String, Object> actual = pollService.getStatistics(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollLectureIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "LectureID cannot be null!");

        Map<String, Object> actual = pollService.reopenPoll(null, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollUserIdNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "UserID cannot be null!");

        Map<String, Object> actual = pollService.reopenPoll(1, null);

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollNoUser() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.empty());
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollNoLecture() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There does not exist a lecture with this id.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollStudentCannotReopen() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Only lecturers and moderators are allowed to reopen polls.");

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(student1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollNoPollsInHistory() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "There are no polls in the history!");

        List<Poll> pollList = new ArrayList<>();

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollSuccessful() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", true);
        expected.put("message", "Poll/Quiz was reopened");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        pollList.add(poll1);
        pollList.add(poll1);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }

    @Test
    public void testReopenPollException() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "java.lang.IllegalArgumentException");

        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        pollList.add(poll1);
        pollList.add(poll1);

        when(userRepository.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(lectureRepository.findById(any(Integer.class))).thenReturn(Optional.of(lecture1));
        when(pollRepository.findAllByLectureId(1)).thenReturn(pollList);
        when(pollRepository.save(any(Poll.class))).thenThrow(new IllegalArgumentException());

        Map<String, Object> actual = pollService.reopenPoll(1, "123");

        assertEquals(expected, actual);
    }
}
