package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.polls.PollServiceImpl;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.PollRepository;
import nl.tudelft.oopp.group54.repositories.PollVoteRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PollServiceTest {

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
    public void testPostPollCorrectAnswerNull() {
        Map<String, Object> expected = new TreeMap<>();
        expected.put("success", false);
        expected.put("message", "Question cannot be null and must be between 1 and 420 characters.");

        Map<String, Object> actual = pollService.postPoll(1, "1", 3, null, "title");

        assertEquals(expected, actual);
    }
}
