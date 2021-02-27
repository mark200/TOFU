package nl.tudelft.oopp.group54.controller;

import org.h2.util.json.JSONObject;

public interface ModeratorService {
    JSONObject getLectureMetadata(String lectureId, String userId);

    JSONObject banStudentIP(String lectureId, String ip);   // FIXME: Find a better datatype
}
