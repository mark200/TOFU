package nl.tudelft.oopp.group54.services;

import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.controllers.votes.VoteService;
import nl.tudelft.oopp.group54.controllers.votes.VoteServiceImpl;
import nl.tudelft.oopp.group54.entities.*;
import nl.tudelft.oopp.group54.repositories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    private LectureRepository lectureRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private QuestionRepository questionRepositoryMock;

    @Mock
    private VoteRepository voteRepositoryMock;

    @InjectMocks
    private VoteServiceImpl voteService;

    @InjectMocks
    private LectureServiceImpl lectureService;

    Lecture lecture1;
    Lecture lecture2;

    User user1;
    User user2;

    Question question1;
    Question question2;


    @BeforeEach
    public void initEach() {
        System.out.println(voteRepositoryMock);
        Date date = new Date();
        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join", "lecturer-join", true);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join", "lecturer-join", true);

        user1 = new User(new UserKey(1,1), "Bob", "127.0.0.1", new Date(), 1);
        user2 = new User(new UserKey(2,1), "Nik", "127.0.0.1", new Date(), 3);

        question1 = new Question(new QuestionKey(1,1), user1.getKey().getId(), user1.getIpAddress(),
                "hello", 0, false, new Date());
        question2 = new Question(new QuestionKey(2,1), user2.getKey().getId(), user2.getIpAddress(),
                "hey", 0, false, new Date());

        lectureRepositoryMock.save(lecture1);
        lectureRepositoryMock.save(lecture2);
        userRepositoryMock.save(user1);
        userRepositoryMock.save(user2);
        questionRepositoryMock.save(question1);
        questionRepositoryMock.save(question2);
    }

    @Test
    void voteOnQuestion() {
        Map<String, Object> status = new TreeMap<>();

        status.put("success", true);

        Vote vote = new Vote(new VoteKey(user1.getKey().getId(), 1, question1.getPrimaryKey().getId()),
                1);

        Map<String, Object> voted = voteService.voteOnQuestion(1, user1.getKey().getId().toString(), 2, true);

        assertEquals(status, voted);
    }
}