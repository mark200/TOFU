package nl.tudelft.oopp.group54.widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostAnswerResponse;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class QuestionView extends AnchorPane {

	VBox innerVBox;
	GridPane outerGridPane;
	GridPane voteGridPane;
	GridPane verticalGridPane;
	GridPane horizontalGridPane;
	MenuBar menuBar;

	TextArea questionTextArea;
	Text userName;

	MenuButton dropDown;
	
	MenuItem delete;
	MenuItem markAnswer;
	MenuItem answerText;
	MenuItem ban;

	Button upvoteButton;

	Label currentScore;

	String questionId;

	public QuestionView(String s, String questionId, String userName) {
		this.outerGridPane = new GridPane();
		this.voteGridPane = new GridPane();
		this.verticalGridPane = new GridPane();
		this.horizontalGridPane = new GridPane();
		this.innerVBox = new VBox();
		this.questionTextArea = new TextArea(s);
		this.userName = new Text(userName);
		this.questionId = questionId;

		this.menuBar = new MenuBar();
		this.dropDown = new MenuButton("Options");
		this.delete = new MenuItem("Delete");
		this.markAnswer = new MenuItem("Mark answered");
		this.answerText = new MenuItem("Answer with text");
		this.ban = new MenuItem("Ban author");

//		this.dropDown.setOpacity(0.0);

		this.questionTextArea.setWrapText(true);
		this.questionTextArea.setEditable(false);
		this.questionTextArea.setPrefRowCount(3);


		this.upvoteButton = new Button("^");
		this.currentScore = new Label("0");

		this.voteGridPane.add(this.upvoteButton, 0, 0);
		this.voteGridPane.add(this.currentScore, 0, 1);

		GridPane.setHgrow(this.questionTextArea, Priority.ALWAYS);

		this.questionTextArea.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		this.outerGridPane.add(this.voteGridPane, 0, 0);
		this.outerGridPane.add(this.verticalGridPane, 1, 0);

//		this.innerVBox.fillWidthProperty();
//
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setMinWidth(30);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setFillWidth(true);
		col2.setHgrow(Priority.ALWAYS);

		this.outerGridPane.getColumnConstraints().addAll(col1, col2);

		//this.outerGridPane.setStyle("-fx-border-color: red;" + "-fx-border-radius: 3px");

		dropDown.getItems().addAll(delete, markAnswer, answerText, ban);

		//outerGridPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		this.verticalGridPane.add(this.horizontalGridPane, 0, 0);
		this.verticalGridPane.add(this.questionTextArea, 0, 1);

		this.horizontalGridPane.add(this.userName, 0, 0);
		this.horizontalGridPane.add(this.dropDown, 1, 0);

		this.horizontalGridPane.setStyle(
						"-fx-border-color: aqua;" + "-fx-border-radius: 3px"
		);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		column2.setFillWidth(true);
		column2.setPercentWidth(50);

		this.horizontalGridPane.getColumnConstraints().addAll(column1, column2);
		//this.delete.setAlignment(Pos.CENTER_RIGHT);



		/**
		 ColumnConstraints cc = new ColumnConstraints();
		 cc.setPercentWidth(10);
		 ColumnConstraints cc2 = new ColumnConstraints();
		 cc.setPercentWidth(90);

		 this.outerGridPane.getColumnConstraints().addAll(cc2, cc);
		 **/



		upvoteButton.setOnAction(event -> {
			vote();
		});
		
		delete.setOnAction(event -> {
			delete();
		});
		
		markAnswer.setOnAction(event -> {
			markAnswered();
		});
		
		answerText.setOnAction(event -> {
			answerWithText();
		});
		
		ban.setOnAction(event -> {
			banAuthor();
		});

//		this.setOnMouseClicked(event -> {
//			this.dropDown.setOpacity(1.0);
//		});
//
//		this.setOnMouseReleased(event -> {
//			this.dropDown.setOpacity(0.0);
//		});

		this.getChildren().addAll(this.outerGridPane, this.menuBar);
	}

	private void childConfiguration() {
		setBottomAnchor(outerGridPane, 0.0);
		setTopAnchor(outerGridPane, 0.0);
		setLeftAnchor(outerGridPane, 0.0);
		setRightAnchor(outerGridPane, 0.0);

		setLeftAnchor(menuBar, 0.0);
		setRightAnchor(menuBar, 0.0);
	}

	private void vote() {
		System.out.println("upvote question " + questionId);
	}
	
	private void delete() {
		System.out.println("delete question " + questionId);
	}
	
	private void markAnswered() {
		 PostAnswerResponse response = null;

	      try {
	          response = ServerCommunication.postAnswer(this.questionId, "");
	      } catch (IOException e) {
	          e.printStackTrace();
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	      }

	      if (!response.getSuccess()) {
	    	  System.out.println(response.getMessage());
	      }
	}
	
	private void answerWithText() {
		System.out.println("answer question " + questionId + " with text");
	}
	
	private void banAuthor() {
		System.out.println("ban author of question " + questionId);
	}


//	public void setQuestionModel() {
//		this.questionModel = questionModel;
//	}

}

