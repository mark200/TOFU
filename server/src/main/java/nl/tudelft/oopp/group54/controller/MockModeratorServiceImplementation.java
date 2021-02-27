package nl.tudelft.oopp.group54.controller;

import org.h2.util.json.JSONObject;

public class MockModeratorServiceImplementation implements ModeratorService {
    @Override
    public JSONObject getLectureMetadata(String lectureId, String userId) {
        return null;
    }

    @Override
    public JSONObject banStudentIP(String lectureId, String ip) {
        return null;
    }
}
