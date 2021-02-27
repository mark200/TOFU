package nl.tudelft.oopp.group54.controllers.moderator;

import java.util.Map;

public interface ModeratorService {
    Map<String, Object> getLectureMetadata(String lectureId, String userId);

    Map<String, Object> banStudentIP(String lectureId, String ip);   // FIXME: Find a better datatype
}
