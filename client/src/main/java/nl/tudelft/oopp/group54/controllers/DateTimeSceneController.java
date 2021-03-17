package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.util.TextFormatterFactory;
import nl.tudelft.oopp.group54.util.TextFormatterType;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DateTimeSceneController extends AbstractApplicationController {

  @FXML
  DatePicker datePicker;
  DateTimeFormatter datePickerFormatter = DateTimeFormatter.ofPattern(
          "yyyy-MM-dd HH:mm"
  );

  @FXML
  TextField timePrompt;

  @FXML
  TextField lectureNamePrompt;

  @FXML
  Button confirmationButton;

  Datastore ds = Datastore.getInstance();

  public static LocalDate NOW_LOCAL_DATE (){
    String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    return LocalDate.parse(date , formatter);
  }

  public void performControllerSpecificSetup() {
    System.out.println("DateTime controller config");

    timePrompt.setTextFormatter(
            TextFormatterFactory.createFormatter(TextFormatterType.TIME)
    );

    lectureNamePrompt.setTextFormatter(
            TextFormatterFactory.createFormatter(TextFormatterType.NAME)
    );
  }

  public void autocompleteTime() {
    CharSequence timeText = timePrompt.getCharacters();
    String timeString = timeText.toString();
    Integer[] integerSplit = new Integer[2];

    if(!timeString.contains(":")) {
      if(timeString.length() <= 2) {
        integerSplit[0] = Integer.valueOf(timeString);
        integerSplit[1] = 0;
      }
      if(timeText.length() == 3) {
        integerSplit[0] = Integer.valueOf(timeString.substring(0, 1));
        integerSplit[1] = Integer.valueOf(timeString.substring(1));
      }
      if(timeText.length() == 4) {
        integerSplit[0] = Integer.valueOf(timeString.substring(0, 2));
        integerSplit[1] = Integer.valueOf(timeString.substring(2));
      }
    } else {
      String[] sp = timeString.split(":");
      if(sp[0].length() == 0) {
        integerSplit[0] = 0;
      } else {
        integerSplit[0] = Integer.valueOf(sp[0]);
      }

      if(sp[1].length() == 0) {
        integerSplit[1] = 0;
      } else {
        integerSplit[1] = Integer.valueOf(sp[1]);
      }
    }

    String toBeReturned = "";

    if (integerSplit[0] <= 9 && integerSplit[0] >= 0) {
      toBeReturned = "0" + integerSplit[0];
    } else {
      toBeReturned = toBeReturned + Integer.min(23, integerSplit[0]);
    }

    toBeReturned += ":";

    if (integerSplit[1] <= 9 && integerSplit[1] >= 0) {
      toBeReturned += "0" + integerSplit[1];
    } else {
      toBeReturned = toBeReturned + Integer.min(59, integerSplit[1]);
    }
    System.out.println(toBeReturned);

    this.timePrompt.setText(toBeReturned);

  }

  public void confirmationButtonClicked() {
    CharSequence timeText = timePrompt.getCharacters();
    CharSequence lectureName = lectureNamePrompt.getCharacters();

    boolean timePromptMissing = timeText.length() <= 0;
    boolean lectureNameMissing = lectureName.length() <= 0;
    boolean dateMissing = datePicker.getValue() == null;

    if (timePromptMissing || lectureNameMissing || dateMissing) {

      if(timePromptMissing) {
        this.shakeWidget(this.timePrompt);
      }

      if (lectureNameMissing) {
        this.shakeWidget(this.lectureNamePrompt);
      }

      if (dateMissing) {
        this.shakeWidget(this.datePicker);
      }

      this.displayStatusMessage("Please fill in the missing necessary information.");

      return;
    }

    this.autocompleteTime();
    timeText = timePrompt.getCharacters();
    String timeString = timeText.toString();

    LocalDateTime localDateTime = LocalDateTime.from(
            datePickerFormatter.parse(this.datePicker.getValue() + " " + timeString)
    );

    Timestamp timestamp = Timestamp.valueOf(localDateTime);
    CreateLectureResponse response = null;
    try {
      response = ServerCommunication.postLecture(timestamp.getTime(), lectureName.toString());
    } catch (Exception e) {
      e.printStackTrace();
      this.displayStatusMessage(e.getMessage());
    }

    if(response.getSuccess()) {
      this.ds.setCreateLectureResponse(response);
      MainView.changeScene(ApplicationScene.COPYLINK, true);
    } else {
      this.displayStatusMessage(response.getMessage());
    }


  }

}
