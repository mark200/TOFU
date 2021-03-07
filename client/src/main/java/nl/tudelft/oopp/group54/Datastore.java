package nl.tudelft.oopp.group54;

import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.widgets.QuestionView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Datastore {

  static Datastore instance;

  ObservableList<QuestionView> currentUnansweredQuestionViews;
  ObservableList<QuestionView> currentAnsweredQuestionViews;

  CreateLectureResponse createLectureResponse;

  String serviceEndpoint = "http://localhost:8080";

  private Datastore() {
    this.currentAnsweredQuestionViews = FXCollections.observableArrayList();
    this.currentUnansweredQuestionViews = FXCollections.observableArrayList();
    createLectureResponse = null;
  }

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

  public void setCurrentUnansweredQuestionViews(ObservableList<QuestionView> currentUnansweredQuestionViews) {
    this.currentUnansweredQuestionViews = currentUnansweredQuestionViews;
  }

  public ObservableList<QuestionView> getCurrentAnsweredQuestionViews() {
    return currentAnsweredQuestionViews;
  }

  public void setCurrentAnsweredQuestionViews(ObservableList<QuestionView> currentAnsweredQuestionViews) {
    this.currentAnsweredQuestionViews = currentAnsweredQuestionViews;
  }

  public void addUnansweredQuestion(String question){
    QuestionView q = new QuestionView(question);
    this.currentUnansweredQuestionViews.add(q);
  }

  public void addAnsweredQuestion(String question){
    QuestionView q = new QuestionView(question);
    this.currentAnsweredQuestionViews.add(q);
  }


  public CreateLectureResponse getCreateLectureResponse() {
    return createLectureResponse;
  }

  public void setCreateLectureResponse(CreateLectureResponse createLectureResponse) {
    this.createLectureResponse = createLectureResponse;
  }
}
