package nl.tudelft.oopp.group54.controller;

import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MockLectureServiceImplementation implements LectureService{
    @Override
    public JSONObject createNewLecture(Date startTime, String lectureName) {
        return null;
    }

    @Override
    public JSONObject joinOngoingLecture(String lectureId, String joinId, String userName) {
        return null;
    }
}
