package nl.tudelft.oopp.group54.controllers.lectures;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class MockLectureServiceImplementation implements LectureService {
    @Override
    public Map<String, Object> createNewLecture(Date startTime, String lectureName) {

        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("lectureID", 123456);
        toBeReturned.put("studentID", 234567);
        toBeReturned.put("moderatorID", 420420);

        return toBeReturned;
    }

    @Override
    public Map<String, Object> joinOngoingLecture(Long lectureId, Long joinId, String userName) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 123456);
        toBeReturned.put("userName", userName);
        toBeReturned.put("role", "Student");

        return toBeReturned;
    }
}
