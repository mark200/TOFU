package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import nl.tudelft.oopp.group54.controllers.answers.AnswerService;
import nl.tudelft.oopp.group54.controllers.answers.AnswerServiceImpl;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.AnswerRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnswerTest {

    @Mock
    AnswerRepository answerRepositoryMock;

    @Mock
    QuestionRepository questionRepositoryMock;

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    AnswerServiceImpl answerService;

    Lecture lecture1;
    Lecture lecture2;

    User user1;
    User user2;

    Question question1;
    Question question2;

    /**
     * Initialize the entities.
     */
    @BeforeEach
    public void init() {
        Date date = new Date();

        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join",
                "lecturer-join", true);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join",
                "lecturer-join", true);

        user1 = new User(new UserKey(1,1), "Bob", "127.0.0.1", new Date(), 1);
        user2 = new User(new UserKey(2,1), "Nik", "127.0.0.1", new Date(), 3);

        question1 = new Question(new QuestionKey(1,1), user1.getKey().getId(), user1.getIpAddress(),
                "hello", 0, false, new Date());
        question2 = new Question(new QuestionKey(2,1), user2.getKey().getId(), user2.getIpAddress(),
                "hey", 0, false, new Date());
    }

    @Test
    public void answerQuestion_lectureIdNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "LectureID cannot be null.");

        Map<String, Object> created = answerService.answerQuestion(null, "1", 1, "answer");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_userIdNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserID cannot be null.");

        Map<String, Object> created = answerService.answerQuestion(1, null, 1, "answer");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_nullQuestionId() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "QuestionID cannot be null.");

        Map<String, Object> created = answerService.answerQuestion(1, "1", null, "answer");

        assertEquals(toBeReturned, created);
    }
    @Test
    public void answerQuestion_LongText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Answer text was unacceptable.");

        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "Hello, everyone! This"
                +
                " is the LONGEST TEXT EVER! I was inspired by the various other \"longest texts ever\" on the internet, a"
                +
                "nd I wanted to make my own. So here it is! This is going to be a WORLD RECORD! This is actua"
                +
                "lly my third attempt at doing this. The first time, I didn't save it. The second time, the Neocitie"
                +
                "s editor crashed. Now I'm writing this in Notepad, then copying it into the Neocities editor instead of typi"
                +
                "ng it directly in the Neocities editor to avoid crashing. It sucks that my past two attempts are gone "
                +
                "now. Those actua"
                +
                "lly got pretty long. Not the longest, but still pretty long. I hope this one won't get lost somehow."
                +
                " Anyways, let's talk ab"
                +
                "out WAFFLES! I like waffles. Waffles are cool. Waffles is a funny word. ");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_emptyUser() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized userID.");
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.empty());
        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "answer");
        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_emptyQuestion() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question with this ID does not exist or has been asked in another lecture.");
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(questionRepositoryMock.findById(any(QuestionKey.class))).thenReturn(Optional.empty());
        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "answer");
        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_asStudent() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "This user is not authorized to provide answers. ");
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user2));
        when(questionRepositoryMock.findById(any(QuestionKey.class))).thenReturn(Optional.of(question1));
        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "answer");
        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_asModeratorOrLecturer() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(questionRepositoryMock.findById(any(QuestionKey.class))).thenReturn(Optional.of(question1));
        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "answer");
        assertEquals(toBeReturned, created);
    }

    @Test
    public void answerQuestion_exception() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "java.lang.IllegalArgumentException: not good");
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user1));
        when(questionRepositoryMock.findById(any(QuestionKey.class))).thenReturn(Optional.of(question1));
        when(questionRepositoryMock.save(any(Question.class))).thenThrow(new IllegalArgumentException("not good"));
        Map<String, Object> created = answerService.answerQuestion(1, "1", 1, "answer");
        assertEquals(toBeReturned, created);
    }




}
