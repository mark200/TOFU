package nl.tudelft.oopp.group54.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.EndLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.EndPollResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetCurrentPollResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureFeedbackResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureMetadataResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetPollStatsResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostPollResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostPollVoteResponse;
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
    Button refreshButton;

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

    @FXML
    TextField titleTextField;

    @FXML
    ChoiceBox<String> optionCountChoiceBox;

    @FXML
    ChoiceBox<String> correctAnswerChoiceBox;

    @FXML
    Button submitPoll;
    
    @FXML
    GridPane pollGridPane;

    @FXML
    TitledPane lectureFeedbackPane;

    @FXML
    TitledPane sortingPane;

    @FXML
    TitledPane pollPane;

    @FXML
    TitledPane lectureSettingsPane;

    @FXML
    ListView<GridPane> statsView;

    @FXML
    TabPane tabPane;

    @FXML
    Tab answerTab;

    @FXML
    Tab pollAndQuizTab;

    ChoiceBox voteBox;

    Button endPoll;

    Label pollTitle;

    Datastore ds = Datastore.getInstance();

    private Boolean ended = false;
    private Boolean inLecturerMode = false;
    private boolean voteSort = false;
    private boolean openPoll = false;
    private Set<Integer> votedQuestions = new HashSet<Integer>();

    ScheduledExecutorService refreshThread;




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
            this.lectureSettingsPane.setVisible(false);
        }

        // student
        if (this.ds.getPrivilegeId().equals(3)) {
            //TODO: GUI elements for the student
            this.endLectureButton.setVisible(false);
            this.lecturerModeButton.setVisible(false);
            this.exportQuestionsButton.setVisible(false);
            this.lectureSettingsPane.setVisible(false);
            this.setupPollVotingMenu();
        }

        System.out.println(this.ds.getPrivilegeId());

        updatePollingGridPane();
        updateOnQuestions(false);
        updateOnMetadata();

        questionField.setOnKeyPressed(event -> {
            keyPressed(event);
        });

        //new Thread(new RefreshThread(this)).start();

        refreshThread = Executors.newSingleThreadScheduledExecutor();


        Runnable runnableNest1 = new Runnable() {
            @Override
            @FXML
            public void run() {
                // do stuff
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        refreshButton.fire();
                    }
                });
            }
        };

        refreshThread.scheduleAtFixedRate(runnableNest1, 0, 5, TimeUnit.SECONDS);

        optionCountChoiceBox.setOnAction(event -> {
            updateCorrectChoiceBox();
        });
    }

    @Override
    public void utilityToolbarBackButtonPressed() {
        refreshThread.shutdown();
        MainView.goBackOnceInHistory();
    }


    /**
     * Dynamically update the ChoiceBox.
     */
    private void updateCorrectChoiceBox() {

        // populate alphabet list
        List<Character> alphabet = new ArrayList<>();
        for (Character i = 'A'; i <= 'Z'; i++) {
            alphabet.add(i);
        }

        if (optionCountChoiceBox.getValue().equals("Option Count")) {
            return;
        }

        Integer numberOfChoices = Integer.parseInt(optionCountChoiceBox.getValue());

        // Set the default values
        correctAnswerChoiceBox.getItems().removeAll(correctAnswerChoiceBox.getItems());

        // populate the Choice Box
        for (int i = 0; i < numberOfChoices; i++) {
            correctAnswerChoiceBox.getItems().add(i, String.valueOf(alphabet.get(i)));
        }

        correctAnswerChoiceBox.setValue("Correct Answer");
        correctAnswerChoiceBox.getItems().add(0, "Correct Answer");
        correctAnswerChoiceBox.getItems().add(1, "No Answer");

    }


    /**
     * Submit poll button Clicked functionality.
     */
    public void submitPollButtonClicked() {
        if (this.ds.getPrivilegeId() == 3) {
            submitPollVote();
        } else {
            submitPoll();
        }
    }

    /**
     * ends the current poll.
     */
    public void endPollButtonClicked() {
        EndPollResponse response = null;

        try {
            response = ServerCommunication.endCurrentPoll();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
            return;
        }

        if (response.getSuccess()) {
            refreshButtonClickedAfter();
        } else {
            this.displayStatusMessage(response.getMessage());
        }


    }

    private void togglePollView(boolean open, Integer optionCount, String pollTitle) {
        if  (this.ds.getPrivilegeId() == 3) {
            if (open) {
                this.pollTitle.setText(pollTitle + ":");
                this.voteBox.setVisible(true);
                this.voteBox.getItems().clear();

                // populate alphabet list
                List<Character> alphabet = new ArrayList<>();
                for (Character i = 'A'; i <= 'Z'; i++) {
                    alphabet.add(i);
                }

                // populate the vote Box
                for (int i = 0; i < optionCount; i++) {
                    voteBox.getItems().add(i, String.valueOf(alphabet.get(i)));
                }

                voteBox.setValue("A");
                submitPoll.setVisible(true);

            } else {
                this.pollTitle.setText("No current polls/quizzes");
                this.voteBox.setVisible(false);
                this.submitPoll.setVisible(false);
            }
        } else {
            if (open) {
                this.titleTextField.setVisible(false);
                this.correctAnswerChoiceBox.setVisible(false);
                this.optionCountChoiceBox.setVisible(false);
                this.submitPoll.setVisible(false);
                this.pollGridPane.add(endPoll, 0, 0);
            } else {
                this.pollGridPane.getChildren().remove(endPoll);
                this.titleTextField.setVisible(true);
                this.correctAnswerChoiceBox.setVisible(true);
                this.optionCountChoiceBox.setVisible(true);
                this.submitPoll.setVisible(true);
            }
        }
    }

    private void submitPollVote() {
        String value = (String) voteBox.getValue();
        PostPollVoteResponse response = null;

        try {

            response = ServerCommunication.postPollVote(value);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
            return;
        }

        if (!response.getSuccess()) {
            this.displayStatusMessage(response.getMessage());
            return;
        }

        this.pollTitle.setText("You have voted!");
        this.voteBox.setVisible(false);
        this.submitPoll.setVisible(false);
    }

    private void submitPoll() {
        if (missingPollInfo()) {
            return;
        }

        PostPollResponse response = null;

        try {
            response = ServerCommunication.postPoll(correctAnswerChoiceBox.getValue(),
                    Integer.parseInt(optionCountChoiceBox.getValue()), titleTextField.getText());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
            return;
        }

        if (!response.getSuccess()) {
            this.displayStatusMessage(response.getMessage());
        } else {
            this.titleTextField.clear();
            this.correctAnswerChoiceBox.setValue("Correct Answer");
            this.optionCountChoiceBox.setValue("Option Count");
            refreshButtonClickedAfter();
        }


    }

    /**
     * Checks whether information of poll is missing.
     */
    public Boolean missingPollInfo() {
        CharSequence pollingTitle = titleTextField.getCharacters();
        Boolean titleTextFieldMissing = pollingTitle.length() == 0;

        if (titleTextFieldMissing) {
            this.shakeWidget(this.titleTextField);
            this.displayStatusMessage("Please enter the title of the poll/quiz");
            return true;
        }

        if (optionCountChoiceBox.getValue().equals("Option Count")) {
            this.shakeWidget(optionCountChoiceBox);
            this.displayStatusMessage("Please enter option count");
            return true;
        }

        if (correctAnswerChoiceBox.getValue().equals("Correct Answer")) {
            this.shakeWidget(correctAnswerChoiceBox);
            this.displayStatusMessage("Please choose the correct answer or No Answer");
            return true;
        }

        return false;
    }


    /**
     * Setup the Choice Boxes of the polling.
     */
    private void updatePollingGridPane() {
        System.out.println(correctAnswerChoiceBox.getItems());
        System.out.println(optionCountChoiceBox.getItems());
        correctAnswerChoiceBox.getItems().removeAll(correctAnswerChoiceBox.getItems());
        optionCountChoiceBox.getItems().removeAll(optionCountChoiceBox.getItems());
        System.out.println(correctAnswerChoiceBox.getItems());
        System.out.println(optionCountChoiceBox.getItems());
        optionCountChoiceBox.getItems().addAll("Option Count", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        correctAnswerChoiceBox.getItems().addAll("Correct Answer", "No Answer","A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        optionCountChoiceBox.setValue("Option Count");
        correctAnswerChoiceBox.setValue("Correct Answer");
        endPoll = new Button("End current");
        endPoll.setOnAction(event -> {
            endPollButtonClicked();
        });
    }

    private void pollAndQuizTabChanged() {
        this.tabPane.setId("nonFlashingTab");
    }

    private void setupPollVotingMenu() {
        this.titleTextField.setVisible(false);
        this.pollTitle = new Label("No current polls/quizzes");
        this.pollGridPane.add(pollTitle, 0, 0);
        this.optionCountChoiceBox.setVisible(false);
        this.correctAnswerChoiceBox.setVisible(false);
        this.voteBox = new ChoiceBox<String>();
        this.voteBox.setVisible(false);
        this.pollGridPane.add(voteBox, 0, 1);
        this.pollGridPane.getChildren().remove(submitPoll);
        this.pollGridPane.add(submitPoll, 0, 2);
        this.submitPoll.setVisible(false);
    }
    
    /**
     * updates the polls.
     */
    public void updatePoll() {
        GetCurrentPollResponse response = null;
    
        try {
            response = ServerCommunication.getCurrentPoll();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
            return;
        }
        
        if (!response.getSuccess()) {
            this.displayStatusMessage(response.getMessage());
            return;
        }
        
        if (openPoll && response.getClosed()) {
            this.displayStatusMessage("Poll/Quiz has closed!");
            this.pollAndQuizTab.setStyle("");
            this.togglePollView(false, null, null);
            this.updateStats();
            this.openPoll = false;
        }
        
        if (!openPoll && !response.getClosed()) {
            this.displayStatusMessage("Poll/Quiz has opened!");
            this.pollAndQuizTab.setStyle("-fx-background-color: derive(green, 50%);");
            this.togglePollView(true, response.getOptionCount(), response.getTitle());
            this.statsView.getItems().clear();
            this.openPoll = true;
        }
        
        if (this.ds.getPrivilegeId() != 3 && openPoll) {
            updateStats();
        }
    }
    
    private void updateStats() {
        GetPollStatsResponse response = null;
    
        try {
            response = ServerCommunication.getPollStats();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            this.displayStatusMessage(e.getMessage());
            return;
        }
        
        if (!response.getSuccess()) {
            this.displayStatusMessage(response.getMessage());
            return;
        }
        
        displayStats(response.getStatsMap(), response.getOptionCount(), response.getVoteCount(), response.getCorrectAnswer());
    }
    
    private void displayStats(Map<String, Integer> votes, Integer optionCount, Integer voteCount, String correctAnswer) {
        statsView.getItems().clear();
        
        GridPane voteCountPane = new GridPane();
        voteCountPane.add(new Label(voteCount + " votes"), 0, 0);
        statsView.getItems().add(voteCountPane);
        
        for (int i = 0; i < optionCount; i++) {
            GridPane pane = new GridPane();
        
            String currentOption = Character.toString('A' + i);
            Label option = new Label(currentOption + ": ");
            if (currentOption.equals(correctAnswer)) {
                option.setTextFill(Color.LIME);
            }
            
            
            pane.add(option, 0, 0);
            
            Integer percentage = 0;
            if (voteCount != 0) {
                percentage = votes.get(currentOption) * 100 / voteCount;
            }
            
            Label value = new Label(percentage + "%");
            if (currentOption.equals(correctAnswer)) {
                option.setTextFill(Color.LIME);
            }
            pane.add(value, 1, 0);
            
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(20);
            pane.getColumnConstraints().add(cc);
            
            statsView.getItems().add(pane);
        }
        
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
        this.exportQuestionsButton.setVisible(false);
        this.sortingPane.setVisible(false);
        this.pollPane.setVisible(false);
        this.lectureSettingsPane.setVisible(false);
        this.lectureFeedbackPane.setExpanded(true);
        this.tabPane.getSelectionModel().select(0);
        this.tabPane.getTabs().remove(answerTab);
        this.voteSort = true;
        refreshButtonClickedAfter();
    }

    private void exitLecturerMode() {
        inLecturerMode = false;
        this.lecturerModeButton.setStyle("");
        for (QuestionView q : unansweredQuestionView.getItems()) {
            q.toggleLecturerMode(false);
        }
        this.exportQuestionsButton.setVisible(true);
        this.sortingPane.setVisible(true);
        this.pollPane.setVisible(true);
        this.lectureSettingsPane.setVisible(true);
        this.tabPane.getTabs().add(answerTab);
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
     * Polls will be updated
     */
    public void refreshButtonClicked() {
        this.spinWidget(this.refreshButton);
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
            // The questions are already sorted by time so only sorting by score is required.
            List<QuestionModel> sortedUnanswered = response.getUnanswered();
            List<QuestionModel> sortedAnswered = response.getAnswered();

            GetAllQuestionsResponse finalResponse = response;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    displayQuestions(sortedUnanswered, sortedAnswered);
                    updatePoll();
                }
            });
        }
    }

    /**
     * Updates the list of question that the User sees and all changes to them.
     * @param unanswered list of new unanswered questions retrieved from the server
     * @param answered list of new answered questions retrieved from the server
     */
    public void displayQuestions(List<QuestionModel> unanswered, List<QuestionModel> answered) {
        updateNewQuestions(unanswered, answered);
        updateDeletedQuestions(unanswered);
        updateSortOrder();
    }

    /**
     * Updates the sort order of the question views.
     */
    public void updateSortOrder() {
        if (voteSort) {
            this.ds.getCurrentUnansweredQuestionViews().sort(new Comparator<QuestionView>() {
                @Override
                public int compare(QuestionView o1, QuestionView o2) {
                    return Integer.compare(o2.getVoteCount(), o1.getVoteCount());
                }
            });
            this.ds.getCurrentAnsweredQuestionViews().sort(new Comparator<QuestionView>() {
                @Override
                public int compare(QuestionView o1, QuestionView o2) {
                    return Integer.compare(o2.getVoteCount(), o1.getVoteCount());
                }
            });
        }
    }

    /**
     * Updates the listview with new questions from the database.
     * @param unanswered list of new unanswered questions
     * @param answered list of new answered questions
     */
    public void updateNewQuestions(List<QuestionModel> unanswered, List<QuestionModel> answered) {
        if (unanswered == null) {
            return;
        }

        if (answered == null) {
            return;
        }

        // Checks for new questions that do not exist in the old list
        // of answered questions
        for (QuestionModel question : answered) {
            if (!this.ds.containsAnsweredQuestion(question.getQuestionId())) {
                this.ds.addAnsweredQuestion(question, this);
            }
        }

        for (QuestionModel question : unanswered) {
            if (!this.ds.containsUnansweredQuestion(question.getQuestionId())) {
                this.ds.addUnansweredQuestion(question, this);
            } else if (question.getScore() != this.ds.getVoteOnQuestion(question.getQuestionId())) {
                this.ds.updateQuestion(question);
            }
        }
    }

    /**
     * Removes all deleted questions from the current listview.
     * Implemented in linear time.
     * @param unanswered list of new unanswered questions
     */
    public void updateDeletedQuestions(List<QuestionModel> unanswered) {
        if (unanswered == null) {
            return;
        }

        Set<String> bufferedQuestionIDs = new HashSet<>();

        for (QuestionModel question : unanswered) {
            bufferedQuestionIDs.add(question.getQuestionId());
        }

        for (QuestionView questionView : this.ds.getCurrentUnansweredQuestionViews()) {
            if (!bufferedQuestionIDs.contains(questionView.getQuestionId())) {
                this.ds.deleteUnansweredQuestionView(questionView);
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
                        // BART
                        refreshThread.shutdown();
                        this.displayStatusMessage("The Lecture has been ended.");
                    }
                    if (ds.getPrivilegeId().equals(3)) {
                        MainView.changeScene(ApplicationScene.MAINVIEW, false);
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

    public Boolean isLectureEnded() {
        return this.ended;
    }

    public Boolean isInLecturerMode() {
        return inLecturerMode;
    }
    
    public Set<Integer> getVotedQuestions() {
        return votedQuestions;
    }

}
