package nl.tudelft.oopp.group54;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nl.tudelft.oopp.group54.controllers.LectureRoomSceneController;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.widgets.AnsweredQuestionView;
import nl.tudelft.oopp.group54.widgets.QuestionView;
import nl.tudelft.oopp.group54.widgets.UnansweredQuestionView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class DatastoreTest {

    Field instance;
    Datastore datastore;


    void resetSingleton() throws IllegalAccessException, NoSuchFieldException {
        instance = Datastore.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);

        datastore = Datastore.getInstance();

    }

    @BeforeEach
    void setUp() {
        try {
            resetSingleton();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance() {
        assertNotNull(datastore);
        datastore = Datastore.getInstance();
        assertNotNull(datastore);
    }

    @Test
    void getServiceEndpoint() {
        assertEquals(datastore.getServiceEndpoint(),"http://localhost:8080");
    }

    @Test
    void getCurrentUnansweredQuestionViews() {
        assertNotNull(datastore.getCurrentUnansweredQuestionViews());
    }

    @Test
    void setCurrentUnansweredQuestionViews() {
        datastore.setCurrentUnansweredQuestionViews(null);
        assertNotNull(datastore.getCurrentUnansweredQuestionViews());
        assertTrue(datastore.getCurrentUnansweredQuestionViews().isEmpty());

        ObservableList<QuestionView> list = FXCollections.observableArrayList();
        datastore.setCurrentUnansweredQuestionViews(list);
        assertEquals(datastore.getCurrentUnansweredQuestionViews(), list);
    }

    @Test
    void getCurrentAnsweredQuestionViews() {
        assertNotNull(datastore.getCurrentAnsweredQuestionViews());
    }

    @Test
    void setCurrentAnsweredQuestionViews() {

        datastore.setCurrentAnsweredQuestionViews(null);
        assertNotNull(datastore.getCurrentAnsweredQuestionViews());
        assertTrue(datastore.getCurrentAnsweredQuestionViews().isEmpty());

        ObservableList<QuestionView> list = FXCollections.observableArrayList();
        datastore.setCurrentAnsweredQuestionViews(list);
        assertEquals(datastore.getCurrentAnsweredQuestionViews(), list);
    }

    @Test
    void addUnansweredQuestion() {
        datastore.addUnansweredQuestion(null, null);
        QuestionModel testQuestion = new QuestionModel();
        datastore.addUnansweredQuestion(testQuestion, null);
        LectureRoomSceneController testLrc = new LectureRoomSceneController();

    }

    @Test
    void addAnsweredQuestion() {
        datastore.addAnsweredQuestion(null, null);
        QuestionModel testQuestion = new QuestionModel();
        datastore.addAnsweredQuestion(testQuestion, null);
        LectureRoomSceneController testLrc = new LectureRoomSceneController();
    }

    @Test
    void containsUnansweredQuestion() {
        assertFalse(datastore.containsUnansweredQuestion(null));
    }

    @Test
    void containsAnsweredQuestion() {
        assertFalse(datastore.containsAnsweredQuestion(null));
    }

    @Test
    void updateQuestion() {
        datastore.updateQuestion(null, null);
    }

    @Test
    void getCreateLectureResponse() {
        assertNull(datastore.getCreateLectureResponse());
    }

    @Test
    void setCreateLectureResponse() {
        CreateLectureResponse clr = new CreateLectureResponse();
        datastore.setCreateLectureResponse(clr);
        assertNotNull(datastore.getCreateLectureResponse());
    }

    @Test
    void getJoinLectureResponse() {
        assertNull(datastore.getJoinLectureResponse());
    }

    @Test
    void setJoinLectureResponse() {
        JoinLectureResponse jlr = new JoinLectureResponse();
        datastore.setJoinLectureResponse(jlr);
        assertNotNull(datastore.getJoinLectureResponse());
    }

    @Test
    void setLectureId() {
        datastore.setLectureId(42);
        assertEquals(42, datastore.getLectureId());
    }

    @Test
    void getLectureId() {
        assertEquals(0, datastore.getLectureId());
    }

    @Test
    void setUserId() {
        datastore.setUserId(42L);
        assertEquals(42L, datastore.getUserId());
    }

    @Test
    void getUserId() {
        assertEquals(0L, datastore.getUserId());
    }

    @Test
    void getPrivilegeId() {
        assertEquals(0, datastore.getPrivilegeId());
    }

    @Test
    void setPrivilegeId() {
        datastore.setPrivilegeId(3);
        assertEquals(3, datastore.getPrivilegeId());
    }

    @Test
    void getVoteOnQuestion() {
        assertNull(this.datastore.getVoteOnQuestion("12"));
    }

    @Test
    void getQuestions() {
        assertNotNull(this.datastore.getQuestions());
    }

    @Test
    void deleteUnansweredQuestionView() {
        datastore.deleteUnansweredQuestionView(null);
    }
}