package nl.tudelft.oopp.group54.controllers.moderator;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service
public class MockModeratorServiceImplementation implements ModeratorService {

    @Override
    public Map<String, Object> banStudentIP(Long lectureId, String ip) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", "true");

        return toBeReturned;
    }
}
