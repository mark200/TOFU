package nl.tudelft.oopp.group54.controllers.lectures;

import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository repository;

    @Autowired
    private UserRepository userRepository;

    public void setRepository(LectureRepository repository) {
        this.repository = repository;
    }

    /**
     * create a new lecture by adding it to the database
     *
     * @param startTime   - Date of the planned start time of the lecture
     * @param lectureName - lecture name given as string
     * @return - Map, which returns the status of the request to create
     * a new lecture, the message of the execution result and if it was successful
     * the UUIDs of the join ids for student, lecturer and moderator.
     */
    @Override
    public Map<String, Object> createNewLecture(Date startTime, String lectureName) {

        //FIXME: There might be a bug if lecture needs to be created instantly
        Map<String, Object> toBeReturned = new TreeMap<>();
        Date currentTime = new Date();

        // if start time is before the current time
        if (startTime.before(currentTime)) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture start time was unacceptable");
            return toBeReturned;
        }

        // if start time is null
        if (startTime == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Null is not acceptable as a start time");
            return toBeReturned;
        }

        // if lecture name is null
        if (lectureName == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Null is not acceptable as lecture name");
            return toBeReturned;
        }

        // if lectureName is longer than 30 chars
        if (lectureName.length() > 30) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture name should be shorter");
            return toBeReturned;
        }

        // if lectureName is smaller than  0 chars
        if (lectureName.length() <= 0) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture name should be longer");
            return toBeReturned;
        }


        String studentId = UUID.nameUUIDFromBytes((currentTime.toString() + "s").getBytes())
                .toString().replaceAll("-", "");
        String moderatorId = UUID.nameUUIDFromBytes((currentTime.toString() + "m").getBytes())
                .toString().replaceAll("-", "");
        String lecturerId = UUID.nameUUIDFromBytes((currentTime.toString() + "l").getBytes())
                .toString().replaceAll("-", "");
        Lecture newLecture = new
                Lecture(null, lectureName, startTime, studentId, moderatorId, lecturerId);
        repository.save(newLecture);

        toBeReturned.put("success", true);
        toBeReturned.put("lectureID", studentId);
        toBeReturned.put("studentID", moderatorId);
        toBeReturned.put("moderatorID", lecturerId);

        return toBeReturned;
    }

    @Override
    public Map<String, Object> joinOngoingLecture(Integer lectureId, Long joinId, String userName) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        toBeReturned.put("success", true);
        toBeReturned.put("userID", 123456);
        toBeReturned.put("userName", userName);
        toBeReturned.put("role", "Student");

        return toBeReturned;
    }

    /**
     * For now this method only returns the number of people watching the lecture
     * @param lectureId
     * @return
     */
    @Override
    public Map<String, Object> getLectureMetadata(Integer lectureId) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        if (lectureId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "LectureID cannot be null.");
            return toBeReturned;
        }

        Optional<Lecture> foundLecture = repository.findById(lectureId);

        if (foundLecture.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "The lecture does not exist or could not be found by ID.");
            return toBeReturned;
        }

        int usersWatchingLecture = userRepository.findAll().stream()
                                        .filter(x -> x.getKey().getLecture_id() == lectureId.intValue())
                                        .collect(Collectors.toList()).size();

        toBeReturned.put("success", true);
        toBeReturned.put("lectureID", lectureId);
        toBeReturned.put("People", usersWatchingLecture);
        toBeReturned.put("studentJoinID", foundLecture.get().getStudentJoinId());
        toBeReturned.put("moderatorJoinID", foundLecture.get().getModeratorJoinId());
        toBeReturned.put("lecturerJoinID", foundLecture.get().getLecturerJoinId());

        return toBeReturned;
    }
}
