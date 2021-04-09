package nl.tudelft.oopp.group54.views;

public enum ApplicationScene {

  MAINVIEW("mainScene"),
  LECTUREROOM("lectureRoomScene"),
  JOINLECTURE("joinLectureScene"),
  DATETIME("dateTimeScene"),
  COPYLINK("copyLinkScene"),
  SETTINGSVIEW("settingsView");

    private final String text;

    ApplicationScene(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

}
