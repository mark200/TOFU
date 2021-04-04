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
import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.entities.Lecture;
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
    @BeforeAll
    public static void init() {
        Date date = new Date();
        lecture1 = new Lecture(1, "OOPP", date, "student-join", "moderator-join", "lecturer-join", true);
        lecture2 = new Lecture(2, "LA", date, "student-join", "moderator-join", "lecturer-join", true);

    }

    @BeforeEach
    public void initEach() {
        System.out.println(lectureRepositoryMock);
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
    public void  joinLecture() {
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
}