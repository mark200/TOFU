package nl.tudelft.oopp.group54.views;

public enum ApplicationScene {

  MAINVIEW("mainScene"),
  LECTUREROOM("lectureRoomScene"),
  JOINLECTURE("joinLectureScene"),
  DATETIME("dateTimeScene"),
  COPYLINK("copyLinkScene");

  private final String text;

  ApplicationScene(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

}
