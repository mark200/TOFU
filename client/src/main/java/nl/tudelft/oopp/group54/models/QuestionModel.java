package nl.tudelft.oopp.group54.models;

public class QuestionModel {

  private String text;
  private int score;

  QuestionModel(String text, int score) {
    this.text = text;
    this.score = score;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }



}
