package nl.tudelft.oopp.group54;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.oopp.group54.controllers.LectureRoomSceneController;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.util.MapQuestions;
import nl.tudelft.oopp.group54.widgets.AnsweredQuestionView;
import nl.tudelft.oopp.group54.widgets.QuestionView;
import nl.tudelft.oopp.group54.widgets.UnansweredQuestionView;

public class Datastore {

    static Datastore instance;

    ObservableList<QuestionView> currentUnansweredQuestionViews;
    ObservableList<QuestionView> currentAnsweredQuestionViews;

    private MapQuestions questions;

    CreateLectureResponse createLectureResponse;
    JoinLectureResponse joinLectureResponse;

    String serviceEndpoint = "http://localhost:8080";

    Long userId = 0L;
    Integer lectureId = 0;
    Integer privilegeId = 0;

    private Datastore() {
        this.currentUnansweredQuestionViews = FXCollections.observableArrayList();
        this.currentAnsweredQuestionViews = FXCollections.observableArrayList();
        this.questions = new MapQuestions();
        createLectureResponse = null;
        joinLectureResponse = null;
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Datastore getInstance() {
        if (instance == null) {
            instance = new Datastore();
        }
        return instance;
    }

    public String getServiceEndpoint() {
        return this.serviceEndpoint;
    }

    public ObservableList<QuestionView> getCurrentUnansweredQuestionViews() {
        return currentUnansweredQuestionViews;
    }

    /**
     * Sets current unanswered question views.
     *
     * @param currentUnansweredQuestionViews the current unanswered question views
     */
    public void setCurrentUnansweredQuestionViews(ObservableList<QuestionView> currentUnansweredQuestionViews) {
        if (currentUnansweredQuestionViews == null) {
            this.currentUnansweredQuestionViews.clear();
        } else {
            this.currentUnansweredQuestionViews = currentUnansweredQuestionViews;
        }
    }

    public ObservableList<QuestionView> getCurrentAnsweredQuestionViews() {
        return currentAnsweredQuestionViews;
    }

    /**
     * Sets current answered question views.
     *
     * @param currentAnsweredQuestionViews the current answered question views
     */
    public void setCurrentAnsweredQuestionViews(ObservableList<QuestionView> currentAnsweredQuestionViews) {
        if (currentAnsweredQuestionViews == null) {
            this.currentAnsweredQuestionViews.clear();
        } else {
            this.currentAnsweredQuestionViews = currentAnsweredQuestionViews;
        }
    }

    /**
     * Add unanswered question.
     *
     *
     * @param question        the question
     * @param sceneController the scene controller
     */
    public void addUnansweredQuestion(QuestionModel question, LectureRoomSceneController sceneController) {
        if (question == null) {
            return;
        }

        if (sceneController == null) {
            return;
        }

        QuestionView q = new UnansweredQuestionView(question.getQuestionText(), question.getQuestionId(),
                question.getUserName(), question.getUserIp(), question.getScore(), question.getUserId());

        q.setOwner(sceneController);
        q.updateQuestionView();

        this.questions.addUnansweredQuestion(question.getQuestionId(), question.getScore());

        if (this.getPrivilegeId().equals(1)) {
            q.toggleLecturerMode(sceneController.isInLecturerMode());
        }

        this.currentUnansweredQuestionViews.add(q);
    }

    /**
     * Add answered question.
     *
     * @param question        the question
     * @param sceneController the scene controller
     */
    public void addAnsweredQuestion(QuestionModel question, LectureRoomSceneController sceneController) {
        if (question == null) {
            return;
        }

        if (sceneController == null) {
            return;
        }

        QuestionView q = new AnsweredQuestionView(question.getQuestionText(), question.getQuestionId(),
                question.getUserName(), question.getUserIp(), question.getScore(), question.getAnswerText());

        q.setOwner(sceneController);
        q.updateQuestionView();

        this.questions.addAnsweredQuestion(question.getQuestionId(), question.getScore());
        this.currentAnsweredQuestionViews.add(q);
    }

    /**
     * Checks if datastore contains the ID of an unanswered question.
     *
     * @param id String representation of an ID of a question
     * @return True/False
     */
    public boolean containsUnansweredQuestion(String id) {
        if (id == null) {
            return false;
        }

        return this.questions.containsUnansweredQuestion(id);
    }

    /**
     * Checks if datastore contains the ID of an answered question.
     *
     * @param id String representation of an ID of a question
     * @return True/False
     */
    public boolean containsAnsweredQuestion(String id) {
        if (id == null) {
            return false;
        }

        return this.questions.containsAnsweredQuestion(id);
    }

    /**
     * Updates a QuestionView entity with a new one.
     * This may be cause if a User for example has voted on
     * this question and its view thus must be updated.
     * @param question to update with
     */
    public void updateQuestion(QuestionModel question) {
        if (question == null) {
            return;
        }

        this.questions.updateValue(question.getQuestionId(), question.getScore());

        QuestionView q = new UnansweredQuestionView(question.getQuestionText(), question.getQuestionId(),
                question.getUserName(), question.getUserIp(), question.getScore(), question.getUserId());

        int index = 0;
        for (int i = 0; i < this.currentUnansweredQuestionViews.size(); i++) {
            if (this.currentUnansweredQuestionViews.get(i).getQuestionId().equals(question.getQuestionId())) {
                index = i;
                break;
            }
        }

        this.currentUnansweredQuestionViews.set(index, q);
    }

    public CreateLectureResponse getCreateLectureResponse() {
        return createLectureResponse;
    }

    public void setCreateLectureResponse(CreateLectureResponse createLectureResponse) {
        this.createLectureResponse = createLectureResponse;
    }

    public JoinLectureResponse getJoinLectureResponse() {
        return joinLectureResponse;
    }

    public void setJoinLectureResponse(JoinLectureResponse joinLectureResponse) {
        this.joinLectureResponse = joinLectureResponse;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getLectureId() {
        return lectureId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Integer getVoteOnQuestion(String questionId) {
        return this.questions.getVoteCountUnanswered(questionId);
    }

    public MapQuestions getQuestions() {
        return this.questions;
    }

    /**
     * Deletes a question from the ObservableList of Views.
     * @param v the QuestionView we want to delete
     */
    public void deleteUnansweredQuestionView(QuestionView v) {
        if (v == null) {
            return;
        }

        this.currentUnansweredQuestionViews.remove(v);
        // this.questions.deleteQuestion(v.getQuestionId());
    }
}
