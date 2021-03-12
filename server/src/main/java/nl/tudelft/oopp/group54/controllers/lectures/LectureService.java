package nl.tudelft.oopp.group54.controllers.lectures;

import java.util.Date;
import java.util.Map;

public interface LectureService {
    Map<String, Object> createNewLecture(Date startTime, String lectureName);

    Map<String, Object> joinOngoingLecture(Long lectureId, Long joinId, String userName);

    Map<String, Object> getLectureMetadata(Long lectureId);
}
