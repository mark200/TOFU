package nl.tudelft.oopp.group54.controller;

import org.h2.util.json.JSONObject;

import java.util.Date;

public interface LectureService {
    JSONObject createNewLecture(Date startTime, String lectureName);
    JSONObject joinOngoingLecture(String lectureId, String joinId, String userName);
}
