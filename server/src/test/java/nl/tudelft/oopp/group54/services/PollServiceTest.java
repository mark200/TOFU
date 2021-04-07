package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import nl.tudelft.oopp.group54.controllers.polls.PollServiceImpl;
import nl.tudelft.oopp.group54.entities.*;
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

    private Lecture lecture1;
    private Lecture lecture2;
    private User user1;
    private User student1;

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

    @BeforeEach
    public void init() {
        Date date = new Date();

        user1 = new User(new UserKey(1,1), "Bob", "127.0.0.1", new Date(), 1);
        student1 = new User(new UserKey(1,1), "Ivan", "127.0.0.1", new Date(), 3);

        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join",
                "lecturer-join", false);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join",
                "lecturer-join", true);
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
}
