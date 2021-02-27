package nl.tudelft.oopp.group54.controllers.moderator;

import java.util.Map;

public class MockModeratorServiceImplementation implements ModeratorService {

  @Override
  public Map<String, Object> getLectureMetadata(String lectureId, String userId) {
    return null;
  }

  @Override
  public Map<String, Object> banStudentIP(String lectureId, String ip) {
    return null;
  }
}
