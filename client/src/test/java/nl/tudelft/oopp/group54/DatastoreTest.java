package nl.tudelft.oopp.group54;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.group54.widgets.QuestionView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatastoreTest {
    Datastore datastore;
    @BeforeEach
    void setUp() {
        datastore = Datastore.getInstance();
    }

    @Test
    void getInstance() {
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
        ObservableList<QuestionView> list = FXCollections.observableArrayList();
        datastore.setCurrentUnansweredQuestionViews(list);
        assertEquals(datastore.getCurrentAnsweredQuestionViews(), list);
    }

    @Test
    void getCurrentAnsweredQuestionViews() {
        assertNotNull(datastore.getCurrentAnsweredQuestionViews());
    }

    @Test
    void setCurrentAnsweredQuestionViews() {
    }

    @Test
    void addUnansweredQuestion() {
    }

    @Test
    void addAnsweredQuestion() {
    }

    @Test
    void containsUnansweredQuestion() {
    }

    @Test
    void containsAnsweredQuestion() {
    }

    @Test
    void updateQuestion() {
    }

    @Test
    void getCreateLectureResponse() {
    }

    @Test
    void setCreateLectureResponse() {
    }

    @Test
    void getJoinLectureResponse() {
    }

    @Test
    void setJoinLectureResponse() {
    }

    @Test
    void setLectureId() {
    }

    @Test
    void getLectureId() {
    }

    @Test
    void setUserId() {
    }

    @Test
    void getUserId() {
    }

    @Test
    void getPrivilegeId() {
    }

    @Test
    void setPrivilegeId() {
    }

    @Test
    void getVoteOnQuestion() {
    }

    @Test
    void getQuestions() {
    }

    @Test
    void deleteUnansweredQuestionView() {
    }
}