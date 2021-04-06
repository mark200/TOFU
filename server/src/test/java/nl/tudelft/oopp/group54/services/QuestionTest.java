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

import nl.tudelft.oopp.group54.controllers.bans.BanService;
import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.controllers.questions.QuestionServiceImplementation;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.BanRepository;
import nl.tudelft.oopp.group54.repositories.LectureFeedbackRepository;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
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
@RunWith(MockitoJUnitRunner.class)
public class QuestionTest {

    @Mock
    private LectureRepository lectureRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private QuestionRepository questionRepositoryMock;

    @Mock
    private BanRepository banRepositoryMock;

    @InjectMocks
    private QuestionServiceImplementation questionService;

    private static Integer lecture1Id;
    private static Integer lecture2Id;
    private static String lecturerId;
    private static String student1Id;
    private static String student2Id;
    private static String lecturerIp;
    private static String questionText = "This is a question";
    private static String newQuestionText = "This is an editedquestion";

    private static Lecture lecture1;
    private static Lecture lecture2;
    
    private static User lecturer;
    private static User student1;
    private static User student2;
    
    private static Question question1;
    private static Question question2;
    private static Question question3;
    private static Question question4;
    private static Question question5;
    
    private static Integer question1Id;
    private static Integer question2Id;
    private static Integer question3Id;
    private static Integer question4Id;
    private static Integer question5Id;

    /**
     * Initializes the lectures.
     */
    @BeforeAll
    public static void init() {
        Date date = new Date();
        lecture1 = new Lecture(0, "OOPP", date, "student-join", "moderator-join", "lecturer-join", true);
        lecture2 = new Lecture(1, "LA", date, "student-join", "moderator-join", "lecturer-join", false);
        lecture1Id = lecture1.getId();
        lecture2Id = lecture2.getId();
        lecturer = new User(new UserKey(0, 0), "Lec", "help1", new Date(0), 1);
        lecturerId = lecturer.getKey().getId().toString();
        lecturerIp = lecturer.getIpAddress();
        student1 = new User(new UserKey(1, 0), "Stud1", "help2", new Date(0), 3);
        student1Id = student1.getKey().getId().toString();
        student2 = new User(new UserKey(0, 1), "Stud2", "help3", new Date(0), 3);
        student2Id = student2.getKey().getId().toString();
        
        question1 = new Question(new QuestionKey(0, 0), student1.getKey().getId(), student1.getIpAddress(), 
                                 questionText, 0, true, new Date());
        question2 = new Question(new QuestionKey(1, 0), student1.getKey().getId(), student1.getIpAddress(), 
                                 questionText, 0, false, new Date());
        question3 = new Question(new QuestionKey(2, 0), lecturer.getKey().getId(), lecturer.getIpAddress(), 
                                 questionText, 0, false, new Date());
        question4 = new Question(new QuestionKey(3, 0), student1.getKey().getId(), student1.getIpAddress(), 
                                 questionText, 0, false, new Date());
        question5 = new Question(new QuestionKey(0, 1), student2.getKey().getId(), student2.getIpAddress(), 
                questionText, 0, false, new Date());
        
        question1Id = question1.getPrimaryKey().getId();
        question2Id = question2.getPrimaryKey().getId();
        question3Id = question3.getPrimaryKey().getId();
        question4Id = question4.getPrimaryKey().getId();
        question5Id = question5.getPrimaryKey().getId();

    }
    
    @Test
    public void postQuestionWithoutLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "LectureID cannot be null!");
        
        Map<String, Object> created = questionService.postQuestion(null, lecturerId, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionWithoutUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserID cannot be null!");
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, null, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionWithoutUserIp() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserIP cannot be null!");
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, lecturerId, null, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionWithNullText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, lecturerId, lecturerIp, null);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionWithEmptyText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, lecturerId, lecturerIp, "");
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionWithLongText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, lecturerId, lecturerIp, 
            "41e4a5dabecdba3c33ba24ebac2e153bac5c44d552435ebedad141d5a11d5e353c34cc51cee1a52a11c1caeacd2c3cdcbd3ea13a512abcd1c45" 
            + "e12b53552ddb4432ebec5513ce2aa2ee33db4bd422a312243dda34b13d1cc4e5322c1d22dcee5c2d4ded215cce255a4d553ea5eac1c3244de"
            + "da3bbcde4dee353ba3ad3bac4c43e5c323a5a3ce5db533d3413b341e2bdc15dde2cbacac5314e2d41b14a3c5b22bd224b444ec3cceeccb453"
            + "dee1ec4244532ad231245115a1e3e11b42aa34dc113e33c1eca4bababb1e3cd2e2cac4adb53a32b1");
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionInvalidLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "There does not exist a lecture with this id.");
        
        when(userRepositoryMock.findById(new UserKey(0, 32))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(32)).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.postQuestion(32, lecturerId, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionNonExistentUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "401 UNAUTHORIZED");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user ID or user not in lecture!");
        
        when(userRepositoryMock.findById(new UserKey(32, 0))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        
        Map<String, Object> created = questionService.postQuestion(lecture1Id, "32", lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionUserNotInLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "401 UNAUTHORIZED");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user ID or user not in lecture!");
        
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        
        Map<String, Object> created = questionService.postQuestion(1, student1Id, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionLectureClosed() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "The lecture has ended.");
        
        when(userRepositoryMock.findById(new UserKey(0, 1))).thenReturn(Optional.of(student2));
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        
        Map<String, Object> created = questionService.postQuestion(lecture2Id, student2Id, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionTooSoon() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Cannot post more than 1 question in 1 minute.");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        
        //this might create timing issues
        student1.setLastQuestion(new Date());
        Map<String, Object> created = questionService.postQuestion(lecture1Id, student1Id, lecturerIp, questionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test()
    public void postQuestionException() {
        NullPointerException e = new NullPointerException("Beep boop");
        
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", e.toString());
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.save(any(Question.class))).thenThrow(e);
        
        student1.setLastQuestion(new Date(0));
        Map<String, Object> created = questionService.postQuestion(lecture1Id, student1Id, lecturerIp, questionText);

        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionStudentLastQuestionExists() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("message", "question has been posted");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        
        student1.setLastQuestion(new Date(0));
        Map<String, Object> created = questionService.postQuestion(lecture1Id, student1Id, lecturerIp, questionText);

        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void postQuestionStudentLastQuestionDoesntExist() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("message", "question has been posted");

        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));

        student1.setLastQuestion(null);
        Map<String, Object> created = questionService.postQuestion(lecture1Id, student1Id, lecturerIp, questionText);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void postQuestionLecturer() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("message", "question has been posted");

        when(userRepositoryMock.findById(new UserKey(0, 0))).thenReturn(Optional.of(lecturer));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));

        student1.setLastQuestion(new Date(0));
        Map<String, Object> created = questionService.postQuestion(lecture1Id, lecturerId, lecturerIp, questionText);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getQuestionsWithoutLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "LectureId cannot be null.");
        
        Map<String, Object> created = questionService.getAllQuestions(null, lecturerId);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void getQuestionsWithoutUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "userId cannot be null.");
        
        Map<String, Object> created = questionService.getAllQuestions(lecture1Id, null);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void getQuestionsInvalidLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture by this ID does not exist.");
        
        when(userRepositoryMock.findById(new UserKey(0, 32))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(32)).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.getAllQuestions(32, lecturerId);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void getQuestionsNonExistentUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized userID.");
        
        when(userRepositoryMock.findById(new UserKey(32, 0))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        
        Map<String, Object> created = questionService.getAllQuestions(lecture1Id, "32");
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void getQuestionsUserNotInLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized userID.");
        
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        
        Map<String, Object> created = questionService.getAllQuestions(1, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void getAllQuestionsEmpty() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("answered", new ArrayList<Map<String, Object>>());
        toBeReturned.put("unanswered", new ArrayList<Map<String, Object>>());
        toBeReturned.put("success", true);
        toBeReturned.put("count", 0);
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));

        Map<String, Object> created = questionService.getAllQuestions(lecture1Id, student1Id);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getAllQuestionsAnswered() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        List<Map<String, Object>> answered = new ArrayList<Map<String, Object>>();
        toBeReturned.put("answered", answered);
        toBeReturned.put("unanswered", new ArrayList<Map<String, Object>>());
        toBeReturned.put("success", true);
        toBeReturned.put("count", 1);

        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findByLectureId(0)).thenReturn(List.of(question1));

        answered.add(questionService.transformQuestion(question1, 0));

        Map<String, Object> created = questionService.getAllQuestions(lecture1Id, student1Id);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void getAllQuestionsUnanswered() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        List<Map<String, Object>> unanswered = new ArrayList<Map<String, Object>>();
        toBeReturned.put("answered", new ArrayList<Map<String, Object>>());
        toBeReturned.put("unanswered", unanswered);
        toBeReturned.put("success", true);
        toBeReturned.put("count", 1);

        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findByLectureId(0)).thenReturn(List.of(question2));

        unanswered.add(questionService.transformQuestion(question2, 0));

        Map<String, Object> created = questionService.getAllQuestions(lecture1Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }

    @Test
    public void deleteQuestionWithoutLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture id can't be null");
        
        Map<String, Object> created = questionService.deleteQuestion(null, question1Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionWithoutUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "user id can't be null");
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question1Id, null);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionWithoutQuestionId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question id can't be null");
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, null, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionInvalidLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized lecture.");
        
        when(userRepositoryMock.findById(new UserKey(0, 32))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(32)).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.deleteQuestion(32, question1Id, lecturerId);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionNonExistentUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user id or specified lecture does not exist!");
        
        when(userRepositoryMock.findById(new UserKey(32, 0))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(0, 0))).thenReturn(Optional.of(question1));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question1Id, "32");
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionUserNotInLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user id or specified lecture does not exist!");
        
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        when(questionRepositoryMock.findById(new QuestionKey(0, 1))).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.deleteQuestion(1, question1Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionNonExistentQuestionId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized question. Incorrect combination of lecture and question ids");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(32, 0))).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, 32, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionAnswered() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Answered questions cannot be deleted!");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(0, 0))).thenReturn(Optional.of(question1));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question1Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionStudentLectureEnded() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "The lecture has ended.");
        
        when(userRepositoryMock.findById(new UserKey(0, 1))).thenReturn(Optional.of(student2));
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        when(questionRepositoryMock.findById(new QuestionKey(0, 1))).thenReturn(Optional.of(question5));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture2Id, question5Id, student2Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionStudentNotAuthorized() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "401 UNAUTHORIZED");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "You are not authorized to delete this question!");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(2, 0))).thenReturn(Optional.of(question3));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question3Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionException() {
        NullPointerException e = new NullPointerException("Beep boop");
    
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", e.toString());
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
        Mockito.doThrow(e).when(questionRepositoryMock).delete(any(Question.class));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question4Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionStudent() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("questionId", 3);
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question4Id, student1Id);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void deleteQuestionLecturer() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("questionId", 3);
        toBeReturned.put("message", "message was deleted successfully!");
        
        when(userRepositoryMock.findById(new UserKey(0, 0))).thenReturn(Optional.of(lecturer));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
        
        Map<String, Object> created = questionService.deleteQuestion(lecture1Id, question4Id, lecturerId);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionWithoutLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Lecture id can't be null");
        
        Map<String, Object> created = questionService.editQuestion(null, question1Id, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionWithoutUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "UserID cannot be null!");
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, null, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionWithoutQuestionId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question id can't be null");
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, null, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionWithNullText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");

        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, lecturerId, null);

        assertEquals(toBeReturned, created);
    }

    @Test
    public void editQuestionWithEmptyText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");

        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, lecturerId, "");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void editQuestionWithLongText() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "422 UNPROCESSABLE ENTRY");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Question cannot be null and must be between 1 and 420 characters.");

        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, lecturerId,
            "41e4a5dabecdba3c33ba24ebac2e153bac5c44d552435ebedad141d5a11d5e353c34cc51cee1a52a11c1caeacd2c3cdcbd3ea13a512abcd1c45"
            + "e12b53552ddb4432ebec5513ce2aa2ee33db4bd422a312243dda34b13d1cc4e5322c1d22dcee5c2d4ded215cce255a4d553ea5eac1c3244de"
            + "da3bbcde4dee353ba3ad3bac4c43e5c323a5a3ce5db533d3413b341e2bdc15dde2cbacac5314e2d41b14a3c5b22bd224b444ec3cceeccb453"
            + "dee1ec4244532ad231245115a1e3e11b42aa34dc113e33c1eca4bababb1e3cd2e2cac4adb53a32b1");

        assertEquals(toBeReturned, created);
    }

    @Test
    public void editQuestionInvalidLectureId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized lecture.");
        
        when(userRepositoryMock.findById(new UserKey(0, 32))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(32)).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.editQuestion(32, question1Id, lecturerId, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionNonExistentUserId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user id or specified lecture does not exist!");
        
        when(userRepositoryMock.findById(new UserKey(32, 0))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(0, 0))).thenReturn(Optional.of(question1));
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, "32", newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionUserNotInLecture() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized user id or specified lecture does not exist!");
        
        when(userRepositoryMock.findById(new UserKey(1, 1))).thenReturn(Optional.empty());
        when(lectureRepositoryMock.findById(1)).thenReturn(Optional.of(lecture2));
        when(questionRepositoryMock.findById(new QuestionKey(0, 1))).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.editQuestion(1, question1Id, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionNonExistentQuestionId() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Unrecognized question. Incorrect combination of lecture and question ids");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(32, 0))).thenReturn(Optional.empty());
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, 32, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionAnswered() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", "Answered questions cannot be edited!");

        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(0, 0))).thenReturn(Optional.of(question1));
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question1Id, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionStudent() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("code", "401 UNAUTHORIZED");
        toBeReturned.put("success", false);
        toBeReturned.put("message", "You are not authorized to edit this question!");
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question4Id, student1Id, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestionException() {
        NullPointerException e = new NullPointerException("Beep boop");
        
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", false);
        toBeReturned.put("message", e.toString());
        
        when(userRepositoryMock.findById(new UserKey(0, 0))).thenReturn(Optional.of(lecturer));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
        when(questionRepositoryMock.save(any(Question.class))).thenThrow(e);
        
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question4Id, lecturerId, newQuestionText);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void editQuestion() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("questionId", 3);
        toBeReturned.put("message", "message was edited successfully!");
         
        when(userRepositoryMock.findById(new UserKey(0, 0))).thenReturn(Optional.of(lecturer));
        when(lectureRepositoryMock.findById(0)).thenReturn(Optional.of(lecture1));
        when(questionRepositoryMock.findById(new QuestionKey(3, 0))).thenReturn(Optional.of(question4));
         
        Map<String, Object> created = questionService.editQuestion(lecture1Id, question4Id, lecturerId, newQuestionText);
         
        assertEquals(toBeReturned, created);
    }
    
    
    @Test
    public void transformQuestionNull() {
        assertEquals(null, questionService.transformQuestion(null, 0));
    }
    
    @Test
    public void transformQuestion() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("questionId", question2.getPrimaryKey().getId());
        toBeReturned.put("userId", question2.getStudentId());
        toBeReturned.put("userIp", question2.getStudentIp());
        toBeReturned.put("userName", student1.getName());
        toBeReturned.put("questionText", question2.getContent());
        toBeReturned.put("score", question2.getVoteCounter());
        toBeReturned.put("answered", question2.getAnswered());
        toBeReturned.put("createdAt", question2.getCreatedAt());
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        
        Map<String, Object> created = questionService.transformQuestion(question2, 0);
        
        assertEquals(toBeReturned, created);
    }
    
    @Test
    public void transformAnsweredQuestion() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("questionId", question1.getPrimaryKey().getId());
        toBeReturned.put("userId", question1.getStudentId());
        toBeReturned.put("userIp", question1.getStudentIp());
        toBeReturned.put("userName", student1.getName());
        toBeReturned.put("questionText", question1.getContent());
        toBeReturned.put("score", question1.getVoteCounter());
        toBeReturned.put("answered", question1.getAnswered());
        toBeReturned.put("createdAt", question1.getCreatedAt());
        toBeReturned.put("answerText", question1.getAnswerText());
        
        when(userRepositoryMock.findById(new UserKey(1, 0))).thenReturn(Optional.of(student1));
        
        Map<String, Object> created = questionService.transformQuestion(question1, 0);
        
        assertEquals(toBeReturned, created);
    }

}