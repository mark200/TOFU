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

	public QuestionView(String s) {
		this.outerGridPane = new GridPane();
		this.innerGridPane = new GridPane();
		this.innerVBox = new VBox();
		this.menuBar = new MenuBar();
		this.questionTextArea = new TextArea(s);

    this.questionTextArea.setWrapText(true);
    this.questionTextArea.setEditable(false);
    this.questionTextArea.setPrefRowCount(6);


		this.upvoteButton = new Button("↑");
		this.currentScore = new Label("0");
		this.downvoteButton = new Button("↓" );

		this.innerGridPane.add(this.upvoteButton, 0, 0);
		this.innerGridPane.add(this.currentScore, 0, 1);
		this.innerGridPane.add(this.downvoteButton, 0, 2);

		GridPane.setHgrow(this.questionTextArea, Priority.ALWAYS);

		this.questionTextArea.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		this.outerGridPane.add(this.innerGridPane, 0, 0);
		this.outerGridPane.add(this.questionTextArea, 1, 0);
		/**
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(10);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc.setPercentWidth(90);

		this.outerGridPane.getColumnConstraints().addAll(cc2, cc);
**/

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

//	public void setQuestionModel() {
//		this.questionModel = questionModel;
//	}

}

