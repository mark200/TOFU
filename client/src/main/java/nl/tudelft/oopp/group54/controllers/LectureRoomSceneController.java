package nl.tudelft.oopp.group54.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.EndLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureFeedbackResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureMetadataResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;
import nl.tudelft.oopp.group54.widgets.QuestionView;




public class LectureRoomSceneController extends AbstractApplicationController {

    //  @FXML
    //  ScrollPane questionScroll

    @FXML
    ListView<QuestionView> answeredQuestionView;

    @FXML
    ListView<QuestionView> unansweredQuestionView;

    @FXML
    TextField questionField;

    @FXML
    Button askButton;

    @FXML
    Button lecturerModeButton;

    @FXML
    Button endLectureButton;

    @FXML
    Button feedbackPanelButton;

    @FXML
    Button exportQuestionsButton;

    @FXML
    Accordion feedbackMenu;
    @FXML
    ColumnConstraints feedbackMenuContainer;
    Integer feedbackMenuContainerUnfoldedWidth = 140;

    @FXML
    Button lectureTooFastButton;

    @FXML
    Label lectureTooFastLabel;

    @FXML
    Button lectureTooSlowButton;

    @FXML
    Label lectureTooSlowLabel;

    @FXML
    MenuButton sortDrop;

    Datastore ds = Datastore.getInstance();

    private Boolean ended = false;
    private Boolean inLecturerMode = false;
    private boolean voteSort = false;


    @Override
    public void performControllerSpecificSetup() {
        answeredQuestionView.setItems(ds.getCurrentAnsweredQuestionViews());
        unansweredQuestionView.setItems(ds.getCurrentUnansweredQuestionViews());

        // lecturer
        if (this.ds.getPrivilegeId().equals(1)) {
            //TODO: GUI elements for the lecturer
            System.out.println("Lecturer-specific setup happens here.");
        }

        // moderator
        if (this.ds.getPrivilegeId().equals(2)) {
            //TODO: GUI elements for the moderator
            this.endLectureButton.setVisible(false);
            this.lecturerModeButton.setVisible(false);
        }

        // student
        if (this.ds.getPrivilegeId().equals(3)) {
            //TODO: GUI elements for the student
            this.endLectureButton.setVisible(false);
            this.lecturerModeButton.setVisible(false);
            this.exportQuestionsButton.setVisible(false);
        }

        System.out.println(this.ds.getPrivilegeId());

        updateOnQuestions(false);
        updateOnMetadata();

        questionField.setOnKeyPressed(event -> {
            keyPressed(event);
        });
    }

    public void askButtonClicked() {
        postQuestion();
        this.refreshButtonClickedAfter();
    }

    /**
     * Opens the popup to enter a file name.
     */
    public void exportQuestionsButtonClicked() {
        Optional<String> result = openDialog();

        if (!result.isPresent()) {
            displayStatusMessage("The input is empty");
        }

        String textFile = result.get();

        // contains illegal characters
        if (containsIllegals(textFile) || textFile.contains("/") || textFile.contains(".") || textFile.length() < 1) {
            displayStatusMessage("Your input contains illegal characters. Please use alphanumerics only.");
            return;
        }

        export(textFile);
    }

    /**
     * Opens TextInputDialog for a filename.
     * @return - Optional String with the filename.
     */
    public Optional<String> openDialog() {
        TextInputDialog dialog = new TextInputDialog("questions");
        dialog.setTitle("text file name");
        dialog.setHeaderText("");
        dialog.setContentText("Please enter the name of the file to export to:");
        Optional<String> result = dialog.showAndWait();
        return result;
    }

    /**
     * Populates the text file with the questions.
     * @param textFile - name of the to be created text file.
     */
    private void export(String textFile) {
        if (textFile == null) {
            displayStatusMessage("There was an error writing to file. Please try again");
            return;
        }

        GetAllQuestionsResponse response = getQuestions();
        List<QuestionModel> questions = sortQuestionResponse(response);
        FileWriter exports = createFile(textFile);
        writeToFile(questions, exports);
    }

    /**
     * Writes a list of questions to a file.
     * @param questions - List of questions.
     * @param exports - file to write to
     */
    private void writeToFile(List<QuestionModel> questions, FileWriter exports) {
        if (exports == null) {
            displayStatusMessage("There was an error writing to file. Please try again");
            return;
        }
        if (questions == null) {
            displayStatusMessage("There was an error writing to file. Please try again");
            return;
        }

        // if there are no questions
        if (questions.size() == 0) {
            try {
                exports.write("There are no questions");
            } catch (IOException e) {
                e.printStackTrace();
                displayStatusMessage("There was an error writing to file. Please try again");
            }
        }

        int counter = 1;
        for (QuestionModel q: questions) {
            try {
                String writeContent = "Question " + counter + ":\n" + q.toString()
                        +  "\n";
                exports.write(writeContent);
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
                displayStatusMessage("There was an error writing to file. Please try again");
            }
            counter++;
        }

        try {
            exports.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getCause().toString());
        }
    }

    /**
     * Sorts the questions in the response.
     * @param response - The response of the request.
     * @return - sorted list of questions.
     */
    private List<QuestionModel> sortQuestionResponse(GetAllQuestionsResponse response) {
        if (response == null) {
            return null;
        }

        // Concatenate answered and unanswered questions
        List<QuestionModel> newList = Stream.concat(response.getAnswered().stream(), response.getUnanswered().stream())
                .collect(Collectors.toList());

        // sort the list of questions
        // this way we can have the most important questions at the top.
        Collections.sort(newList, new Comparator<QuestionModel>() {
            @Override
            public int compare(QuestionModel o1, QuestionModel o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });

        return newList;
    }

    /**
     * Creates a file.
     * @param textFile - String of the filename
     * @return - File
     */
    private FileWriter createFile(String textFile) {
        if (textFile == null) {
            return null;
        }

        try {
            FileWriter exports = new FileWriter(textFile + ".txt");
            return exports;
        } catch (Exception e) {
            e.printStackTrace();
            displayStatusMessage("There was an error writing to file. Please try again");
            return null;
        }
    }


    /**
     * Returns true if input contains illegal characters
     * Source: https://stackoverflow.com/questions/14635391/java-function-to-return-if-string-contains-illegal-characters
     * @param toExamine - the input string
     * @return true if does not contain.
     */
    public boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    public void lectureTooFastButtonClicked() {
        lectureFeedbackButtonClicked(1);
        refreshButtonClickedAfter();
    }

    public void lectureTooSlowButtonClicked() {
        lectureFeedbackButtonClicked(2);
        refreshButtonClickedAfter();
    }

    private void lectureFeedbackButtonClicked(Integer lectureFeedbackCode) {
        try {
            ServerCommunication.sendLectureFeedback(ds.getUserId(), lectureFeedbackCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
        }
    }

    /**
     * Lecturer mode button clicked.
     */
    public void lecturerModeButtonClicked() {
        if (inLecturerMode) {
            exitLecturerMode();
        } else {
            enterLecturerMode();
        }
    }

    private void enterLecturerMode() {
        inLecturerMode = true;
        this.lecturerModeButton.setStyle("-fx-background-color: linear-gradient(#cccccc, #aaaaaa);");
        for (QuestionView q : unansweredQuestionView.getItems()) {
            q.toggleLecturerMode(true);
        }
    }

    private void exitLecturerMode() {
        inLecturerMode = false;
        this.lecturerModeButton.setStyle("");
        for (QuestionView q : unansweredQuestionView.getItems()) {
            q.toggleLecturerMode(false);
        }
    }

    public void endLectureButtonClicked() {
        endLecture();
    }

    /**
     * Key pressed.
     *
     * @param event the event
     */
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            postQuestion();
        }
    }

    private void postQuestion() {
        String questionText = questionField.getCharacters().toString();
        PostQuestionResponse response = null;
        try {
            response = ServerCommunication.postQuestion(questionText);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            this.displayStatusMessage(e.getMessage());
        }

        if (response.getSuccess()) {
            questionField.clear();
            this.refreshButtonClickedAfter();
        } else {
            this.displayStatusMessage(response.getMessage());
        }
    }


    /**
     * The actions that occur when the refresh button is clicked.
     * All existing questions will be updated.
     * New questions will be added.
     * Lecture metadata(whether it is ended) will be reflected.
     * Lecture feedback will be updated
     */
    public void refreshButtonClicked() {
        updateOnQuestions(true);
        updateOnMetadata();
        updateLectureFeedback();
    }

    /**
     * Same as refreshButtonClicked, but the refresh status message is suppressed.
     */
    public void refreshButtonClickedAfter() {
        updateOnQuestions(false);
        updateOnMetadata();
        updateLectureFeedback();
    }

    /**
     * Update on questions.
     *
     * @param statusDisplay the status display
     */
    public void updateOnQuestions(Boolean statusDisplay) {
        GetAllQuestionsResponse response = null;

        try {
            response = ServerCommunication.getAllQuestions();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getSuccess()) {
            if (statusDisplay) {
                this.displayStatusMessage("Refreshed succesfully.");
            }
            // The questions are already sorted by time so only sorting by score is required.
            List<QuestionModel> sortedUnanswered = response.getUnanswered();
            List<QuestionModel> sortedAnswered = response.getAnswered();

            sortQuestions(sortedUnanswered);
            sortQuestions(sortedAnswered);

            this.ds.setCurrentUnansweredQuestionViews(null);
            this.ds.setCurrentAnsweredQuestionViews(null);
            for (QuestionModel question : sortedAnswered) {
                this.ds.addAnsweredQuestion(question, this);
            }
            for (QuestionModel question : sortedUnanswered) {
                this.ds.addUnansweredQuestion(question, this);
            }
        }
    }

    /**
     * Sort questions.
     *
     * @param list the list
     */
    public void sortQuestions(List<QuestionModel> list) {
        if (voteSort) {
            list.sort(new Comparator<QuestionModel>() {
                @Override
                public int compare(QuestionModel o1, QuestionModel o2) {
                    return Integer.compare(o2.getScore(), o1.getScore());
                }
            });
        }
    }

    /**
     * Update on metadata.
     */
    public void updateOnMetadata() {
        GetLectureMetadataResponse metadataResponse = null;

        try {
            metadataResponse = ServerCommunication.getLectureMetadata();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (metadataResponse != null) {
            if (metadataResponse.getSuccess()) {
                if (!metadataResponse.getLectureOngoing()) {
                    //TODO: stuff when lecture has ended change to main menu view
                    endLectureGui();
                    //TODO: update status bar to ended
                    if (!ended) {
                        ended = true;
                        this.displayStatusMessage("The Lecture has been ended.");
                    }
                    if (ds.getPrivilegeId().equals(3)) {
                        MainView.changeSceneClearHistory(ApplicationScene.MAINVIEW, false, true);
                    }
                }
            }
        }

    }

    /**
     * Get the new state of the total lecture feedback.
     */
    public void updateLectureFeedback() {
        GetLectureFeedbackResponse lectureFeedbackResponse = null;

        try {
            lectureFeedbackResponse = ServerCommunication.getLectureFeedback();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            this.displayStatusMessage(e.getMessage());
        }

        if (lectureFeedbackResponse.getSuccess()) {
            this.lectureTooFastLabel.setText(
                    lectureFeedbackResponse.getLectureFeedbackMap().get("1").toString());
            this.lectureTooSlowLabel.setText(
                    lectureFeedbackResponse.getLectureFeedbackMap().get("2").toString()
            );

        } else {
            this.displayStatusMessage(lectureFeedbackResponse.getMessage());
        }

    }

    @FXML
    protected void initialize() {
        MenuItem item1 = new MenuItem("sort by votes");
        MenuItem item2 = new MenuItem("sort by recency");

        sortDrop.getItems().setAll(item1, item2);

        item1.setOnAction(event -> {
            voteSort = true;
            refreshButtonClickedAfter();
        });

        item2.setOnAction(event -> {
            voteSort = false;
            refreshButtonClickedAfter();
        });
    }

    /**
     * End lecture.
     */
    public void endLecture() {
        EndLectureResponse response = null;

        try {
            response = ServerCommunication.endLecture();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response == null) {
            this.displayStatusMessage("Could not end the lecture!");
            return;
        }

        this.displayStatusMessage(response.getMessage());

        if (response.getSuccess()) {
            endLectureGui();
        }
    }

    /**
     * Toggle feedback panel visibility.
     */
    public void toggleFeedbackPanelVisibility() {
        boolean vis = !this.feedbackMenu.isVisible();
        this.feedbackMenu.setVisible(vis);

        if (vis) {
            this.feedbackMenuContainer.setPrefWidth(feedbackMenuContainerUnfoldedWidth);
        } else {
            this.feedbackMenuContainer.setPrefWidth(0);
        }
    }

    /**
     * Get all questions response.
     * @return the response of the GET questions request.
     */
    private GetAllQuestionsResponse getQuestions() {
        GetAllQuestionsResponse response = null;

        try {
            response = ServerCommunication.getAllQuestions();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * sets GUI element to the state of lecture has finished.
     */
    public void endLectureGui() {
        this.questionField.setEditable(false);
        this.questionField.setDisable(true);
        this.askButton.setDisable(true);
        this.endLectureButton.setVisible(false);
    }


    public Datastore getDs() {
        return ds;
    }

    public Boolean isInLecturerMode() {
        return inLecturerMode;
    }

}
