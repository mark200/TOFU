package nl.tudelft.oopp.group54.controllers.moderator;

import java.util.Map;

public interface ModeratorService {
    Map<String, Object> banStudentIP(Long lectureId, String ip);   // FIXME: Find a better datatype
}
