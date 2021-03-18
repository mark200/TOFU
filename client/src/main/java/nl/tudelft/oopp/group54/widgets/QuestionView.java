package nl.tudelft.oopp.group54.widgets;

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
	GridPane innerGridPane;
	MenuBar menuBar;

	TextArea questionTextArea;


	Button upvoteButton;
	Button downvoteButton;

	Label currentScore;
	
	String questionId;

	public QuestionView(String s, String questionId) {
		AnchorPane anchorPane0 = new AnchorPane();
		anchorPane0.setMinHeight(Long.MIN_VALUE);
		anchorPane0.setPrefHeight(400.0);
		anchorPane0.setMaxHeight(Long.MIN_VALUE);
		anchorPane0.setPrefWidth(600.0);
		anchorPane0.setMinWidth(Long.MIN_VALUE);
		anchorPane0.setMaxWidth(Long.MIN_VALUE);
		Text text1 = new Text();
		text1.setStrokeWidth(0.0);
		//text1.setStrokeType(OUTSIDE);
		text1.setLayoutX(139.0);
		text1.setLayoutY(43.0);
		text1.setText(s);

		// Adding child to parent
		anchorPane0.getChildren().add(text1);
		TextArea textArea2 = new TextArea();
		textArea2.setPrefHeight(200.0);
		textArea2.setPrefWidth(200.0);
		textArea2.setLayoutX(246.0);
		textArea2.setLayoutY(173.0);

		// Adding child to parent
		anchorPane0.getChildren().add(textArea2);
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

