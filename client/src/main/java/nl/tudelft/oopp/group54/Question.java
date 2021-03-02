package nl.tudelft.oopp.group54;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Question extends Pane{
	private String question;
	
	public Question(String question) {
		this.question = question;
		setWidth(100);
		setHeight(100);
		Text text = new Text(question);
		getChildren().add(text);
		setStyle("-fx-background-color: black;");
	}
}
