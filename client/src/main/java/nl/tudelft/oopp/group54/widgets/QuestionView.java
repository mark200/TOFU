package nl.tudelft.oopp.group54.widgets;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import nl.tudelft.oopp.group54.models.QuestionModel;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
	Button delete;


	Button upvoteButton;

	Label currentScore;

	String questionId;

	public QuestionView(String s, String questionId) {
		this.outerGridPane = new GridPane();
		this.voteGridPane = new GridPane();
		this.verticalGridPane = new GridPane();
		this.horizontalGridPane = new GridPane();
		this.innerVBox = new VBox();
		this.menuBar = new MenuBar();
		this.delete = new Button("delete");
		this.questionTextArea = new TextArea(s);
		this.userName = new Text("bob");
		this.questionId = questionId;

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

		this.outerGridPane.setStyle("-fx-border-color: red;" + "-fx-border-radius: 3px");

		//outerGridPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		this.verticalGridPane.add(this.horizontalGridPane, 0, 0);
		this.verticalGridPane.add(this.questionTextArea, 0, 1);



		this.horizontalGridPane.add(this.userName, 0, 0);
		this.horizontalGridPane.add(this.delete, 1, 0);


		this.horizontalGridPane.setStyle("-fx-border-color: aqua;" + "-fx-border-radius: 3px");


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
			voteButtonPressed(true);
		});


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

	private void voteButtonPressed(boolean upvote) {

	}

//	public void setQuestionModel() {
//		this.questionModel = questionModel;
//	}

}

