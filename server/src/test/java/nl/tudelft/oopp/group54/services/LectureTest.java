package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import junit.runner.Version;
import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.LectureFeedback;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.LectureFeedbackRepository;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
//import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ExtendWith(MockitoExtension.class)
public class LectureTest {

    @Mock
    private LectureRepository lectureRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private LectureFeedbackRepository lectureFeedbackRepositoryMock;

    @InjectMocks
    private LectureServiceImpl lectureService;


    static Lecture lecture1;
    static Lecture lecture2;

    /**
     * Initializes the lectures.
     */
    @BeforeEach
    public void init() {
        Date date = new Date();
        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join", "lecturer-join", true);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join", "lecturer-join", true);

    }

    @Test
    public void createLecture_NullStartTime() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "Null is not acceptable as a start time");

        Map<String, Object> created =  lectureService.createNewLecture(null, "OOPP");

        assertEquals(status, created);
    }

    @Test
    public void createInstantLecture() {

        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("lectureId", null);

        when(lectureRepositoryMock.save(any(Lecture.class))).thenReturn(new Lecture());

        Map<String, Object> created =  lectureService.createNewLecture(new Date(0), "OOPP");
        System.out.println(created);

        assertEquals(toBeReturned.get("lectureId"), created.get("lectureId"));
        assertEquals(toBeReturned.get("success"), created.get("success"));
    }

    @Test
    public void createLectureStartTimeInThePast() {
        Date timeInPast = new Date(1000);

        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture start time was unacceptable");

        Map<String, Object> created = lectureService.createNewLecture(timeInPast, "OOPP");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void createLectureNameNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        Date date = new Date(9617564911237L);

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Null is not acceptable as lecture name");

        Map<String, Object> created = lectureService.createNewLecture(date, null);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void createLectureNameTooLong() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        Date date = new Date(9617564911237L);

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture name should be shorter");

        Map<String, Object> created = lectureService.createNewLecture(date, "This is a verry long OOPP lecture");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void createLectureNameTooShort() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        Date date = new Date(9617564911237L);

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture name should be longer");

        Map<String, Object> created = lectureService.createNewLecture(date, "");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void createScheduledLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("lectureId", null);

        when(lectureRepositoryMock.save(any(Lecture.class))).thenReturn(new Lecture());

        Date date = new Date(9617964911237L);
        Map<String, Object> created = lectureService.createNewLecture(date, "OOPP");

        assertEquals(toBeReturned.get("success"), created.get("success"));
        assertEquals(toBeReturned.get("lectureId"), null);
    }

    @Test
    public void joinLecture_lectureIdNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "LectureId cannot be null.");

        Map<String, Object> created = lectureService.joinOngoingLecture(null, "moderator-join", "mod1");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_roleCodeNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "JoinId cannot be null.");

        Map<String, Object> created = lectureService.joinOngoingLecture(123, null, "mod1");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_userNameNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserName cannot be null.");

        Map<String, Object> created = lectureService.joinOngoingLecture(123, "moderator-join", null);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_userNameTooLong() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserName is unacceptable.");

        Map<String, Object> created = lectureService.joinOngoingLecture(123, "mod", "this user name is way too long...........");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_noLectureFound() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture by this ID does not exist.");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.joinOngoingLecture(1, "mod", "Moderator");
        System.out.println(Version.id());
        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_startTimeInTheFuture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "The lecture has not started yet.");

        Date date = new Date(9617964911237L);
        lecture1.setStartTime(date);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        Map<String, Object> created = lectureService.joinOngoingLecture(1, "mod", "Moderator");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLecture_unrecognizedRoleCode() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized roleCode/user ID.");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> created = lectureService.joinOngoingLecture(1, "mod", "Moderator");

        assertEquals(toBeReturned, created);
    }


    @Test
    public void joinLecture_studentJoinEndedLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "The lecture has ended.");

        lecture1.setLectureOngoing(false);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> created = lectureService.joinOngoingLecture(1, "student-join", "stud");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void joinLectureSuccessfully_moderator() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 0);
        toBeReturned.put("userName", "Moderator");
        toBeReturned.put("role", "Moderator");
        toBeReturned.put("privilegeId", 2);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> joined = lectureService.joinOngoingLecture(1, "moderator-join", "Moderator");

        assertEquals(joined, toBeReturned);
    }

    @Test
    public void joinLectureSuccessfully_lecturer() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 0);
        toBeReturned.put("userName", "Steve");
        toBeReturned.put("role", "Lecturer");
        toBeReturned.put("privilegeId", 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> joined = lectureService.joinOngoingLecture(1, "lecturer-join", "Steve");

        assertEquals(joined, toBeReturned);
    }

    @Test
    public void  joinLectureSuccessfully_student() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 0);
        toBeReturned.put("userName", "Martynas");
        toBeReturned.put("role", "Student");
        toBeReturned.put("privilegeId", 3);

        List<Lecture> lectures = new ArrayList<>();
        Mockito.when(lectureRepositoryMock.save(any(Lecture.class))).then(i -> i.getArguments()[0]);
        lectures.add(lectureRepositoryMock.save(lecture1));
        lectures.add(lectureRepositoryMock.save(lecture2));

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> joined = lectureService.joinOngoingLecture(1, "student-join", "Martynas");

        assertEquals(toBeReturned, joined);
    }

    @Test
    public void joinLecture_exception() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "java.lang.IllegalArgumentException: this is not good");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.save(any(User.class))).thenThrow(new IllegalArgumentException("this is not good"));

        Map<String, Object> joined = lectureService.joinOngoingLecture(1, "lecturer-join", "Steve");

        assertEquals(joined, toBeReturned);
    }

    @Test
    public void joinLecture_streamFilter() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 1);
        toBeReturned.put("userName", "Steve");
        toBeReturned.put("role", "Lecturer");
        toBeReturned.put("privilegeId", 1);

        User user1 = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);
        User user2 = new User(new UserKey(2, 2), "Ivan Ivanov", "127.0.0.1", new Date(), 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(user1, user2));

        Map<String, Object> joined = lectureService.joinOngoingLecture(1, "lecturer-join", "Steve");

        assertEquals(toBeReturned, joined);
    }

    @Test
    public void getLectureMetadata_lectureIdNull() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", false);
        toBeReturned.put("message", "LectureID cannot be null.");

        Map<String, Object> created = lectureService.getLectureMetadata(null);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getLectureMetadata_emptyLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "The lecture does not exist or could not be found by ID.");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.empty());
        Map<String, Object> created = lectureService.getLectureMetadata(1);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getLectureMetadata_successfully() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("lectureID", lecture1.getId());
        toBeReturned.put("people", 0);
        toBeReturned.put("studentJoinID", lecture1.getStudentJoinId());
        toBeReturned.put("moderatorJoinID", lecture1.getModeratorJoinId());
        toBeReturned.put("lecturerJoinID", lecture1.getLecturerJoinId());
        toBeReturned.put("lectureOngoing", lecture1.isLectureOngoing());

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findAll()).thenReturn(new ArrayList<User>(0));

        Map<String, Object> created = lectureService.getLectureMetadata(1);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getLectureMetadata_streamFilter() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("lectureID", lecture1.getId());
        toBeReturned.put("people", 1);
        toBeReturned.put("studentJoinID", lecture1.getStudentJoinId());
        toBeReturned.put("moderatorJoinID", lecture1.getModeratorJoinId());
        toBeReturned.put("lecturerJoinID", lecture1.getLecturerJoinId());
        toBeReturned.put("lectureOngoing", lecture1.isLectureOngoing());

        User user1 = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);
        User user2 = new User(new UserKey(2, 2), "Ivan Ivanov", "127.0.0.1", new Date(), 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(user1, user2));

        Map<String, Object> joined = lectureService.getLectureMetadata(1);

        assertEquals(toBeReturned, joined);
    }



    @Test
    public void endLecture_lectureIdNull() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "LectureID cannot be null!");

        Map<String, Object> created = lectureService.endLecture(1, null);
        assertEquals(status, created);
    }

    @Test
    public void endLecture_userIdNull() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "UserID cannot be null!");

        Map<String, Object> created = lectureService.endLecture(null, 1);
        assertEquals(status, created);
    }

    @Test
    public void endLecture_emptyLecture() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "Unrecognized lecture. Incorrect combination of lecture and question ids");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.empty());
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void endLecture_emptyUser() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "Unrecognized user id or specified lecture does not exist!");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void endLecture_unauthorized() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "You are not authorized to end this lecture!");

        User user = new User(new UserKey(2, 2), "Ivan Ivanov", "127.0.0.1", new Date(), 3);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.of(user));

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void endLecture_lectureHasAlreadyEnded() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "The lecture has already been ended.");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);
        lecture1.setLectureOngoing(false);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.of(user));

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void endLecture_Successfully() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", true);
        status.put("message", "The lecture was successfully ended.");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.of(user));

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void endLecture_Exception() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("success", false);
        status.put("message", "java.lang.IllegalArgumentException: This is illegal");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.of(user));
        when(lectureRepositoryMock.save(any(Lecture.class))).thenThrow(new IllegalArgumentException("This is illegal"));

        Map<String, Object> created = lectureService.endLecture(1, 1);

        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_emptyLecture() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "There does not exist a lecture with this id.");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_endedLecture() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "The lecture has already ended.");

        lecture1.setLectureOngoing(false);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_emptyUser() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("code", "401 UNAUTHORIZED");
        status.put("message", "Unrecognized user ID or specified lecture does not exist!");


        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_student() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);
        status.put("message", "The lecture was successfully ended.");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 3);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));

        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_lectuterOrModerator() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);
        status.put("message", "The lecture was cleared successfully.");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));


        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_studentException() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "java.lang.IllegalArgumentException: not good");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 3);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));
        when(lectureFeedbackRepositoryMock.save(any(LectureFeedback.class))).thenThrow(new IllegalArgumentException("not good"));


        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }

    @Test
    public void postLectureFeedback_lectuterOrModeratorException() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "java.lang.IllegalArgumentException: hmm");

        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 1);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));
        doThrow(new IllegalArgumentException("hmm")).when(lectureFeedbackRepositoryMock).deleteAllByLectureFeedbackCode(1);


        Map<String, Object> created = lectureService.postLectureFeedback(1, "1", 1);
        assertEquals(status, created);
    }


    @Test
    public void getLectureFeedback_emptyLecture() {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "There does not exist a lecture with this id.");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.getLectureFeedback(1, "1");

        assertEquals(status, created);
    }

    @Test
    public void getLectureFeedback_endedLecture() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "The lecture has already ended.");

        lecture1.setLectureOngoing(false);
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));

        Map<String, Object> created = lectureService.getLectureFeedback(1, "1");
        assertEquals(status, created);
    }

    @Test
    public void getLectureFeedback_emptyUser() {
        Map<String, Object> status = new TreeMap<>();
        status.put("code", "401 UNAUTHORIZED");
        status.put("success", false);
        status.put("message", "Unrecognized user ID or specified lecture does not exist!");

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.empty());

        Map<String, Object> created = lectureService.getLectureFeedback(1, "1");
        assertEquals(status, created);
    }

    @Test
    public void getLectureFeedback_success() {
        Map<String, Object> status = new TreeMap<>();
        HashMap<String, Integer> lectureFeedbackMap = new HashMap<>();
        lectureFeedbackMap.put("1", 5);
        lectureFeedbackMap.put("2", 7);
        status.put("lectureFeedbackMap", lectureFeedbackMap);
        status.put("success", true);


        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 3);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));
        when(lectureFeedbackRepositoryMock.countAllByLectureFeedbackCode(1)).thenReturn(5);
        when(lectureFeedbackRepositoryMock.countAllByLectureFeedbackCode(2)).thenReturn(7);


        Map<String, Object> created = lectureService.getLectureFeedback(1, "1");
        assertEquals(status, created);
    }

    @Test
    public void getLectureFeedback_exception() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "java.lang.IllegalArgumentException: hmm");


        User user = new User(new UserKey(1, 1), "Ivan Ivanov", "127.0.0.1", new Date(), 3);

        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture1));
        when(userRepositoryMock.findById(any(UserKey.class))).thenReturn(Optional.of(user));
        when(lectureFeedbackRepositoryMock.countAllByLectureFeedbackCode(1)).thenReturn(5);
        when(lectureFeedbackRepositoryMock.countAllByLectureFeedbackCode(2)).thenThrow(new IllegalArgumentException("hmm"));


        Map<String, Object> created = lectureService.getLectureFeedback(1, "1");
        assertEquals(status, created);
    }

}