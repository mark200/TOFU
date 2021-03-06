package nl.tudelft.oopp.group54;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Question extends Pane{
	private String question;
	
	public Question(String question) {
		this.question = question;
		setPrefSize(100, 100);
		Text t = new Text (10, 20, question);
		getChildren().add(t);
		setStyle("-fx-border-color: black");
	}
}
