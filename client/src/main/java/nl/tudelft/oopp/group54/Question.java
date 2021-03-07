package nl.tudelft.oopp.group54;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Question extends AnchorPane{
	private String question;
	
	public Question(String question) {
		this.question = question;
		this.setPrefSize(100, 100);
		Text t = new Text (10, 20, question);
		this.getChildren().add(t);
		this.setStyle("-fx-border-color: black");
	}
}

