package nl.tudelft.oopp.group54.controllers.lectures;

import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
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

        // if start time is null
        if (startTime == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Null is not acceptable as a start time");
            return toBeReturned;
        }

        //"create instant" functionality
        if (startTime.equals(new Date(0))) {
            startTime = currentTime;
        }

        // if start time is before the current time
        if (startTime.before(currentTime)) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture start time was unacceptable");
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


        String lecturerJoinId = UUID.nameUUIDFromBytes((currentTime.toString() + "s").getBytes())
                .toString().replaceAll("-", "");
        String studentJoinId = UUID.nameUUIDFromBytes((currentTime.toString() + "m").getBytes())
                .toString().replaceAll("-", "");
        String moderatorJoinId = UUID.nameUUIDFromBytes((currentTime.toString() + "l").getBytes())
                .toString().replaceAll("-", "");
        Lecture newLecture = new
                Lecture(null, lectureName, startTime, studentJoinId, moderatorJoinId, lecturerJoinId, true);
        newLecture.setLectureOngoing(true);

        repository.flush();
        repository.save(newLecture);

        toBeReturned.put("success", true);
        toBeReturned.put("lectureId", newLecture.getId());
        toBeReturned.put("lecturerId", lecturerJoinId);
        toBeReturned.put("studentId", studentJoinId);
        toBeReturned.put("moderatorId", moderatorJoinId);

        return toBeReturned;
    }

    /**
     * Creates a new User when joining the specified lecture
     * and assigns them a role
     *
     * @param lectureId
     * @param roleCode
     * @param userName
     * @return
     */
    @Override
    public Map<String, Object> joinOngoingLecture(Integer lectureId, String roleCode, String userName) {
        Map<String, Object> toBeReturned = new TreeMap<>();

        if (lectureId == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "LectureId cannot be null.");
            return toBeReturned;
        }

        if (roleCode == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "JoinId cannot be null.");
            return toBeReturned;
        }

        if (userName == null) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "UserName cannot be null.");
            return toBeReturned;
        }

        if (userName.length() > 40) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "UserName is unacceptable.");
            return toBeReturned;
        }

        Optional<Lecture> foundLecture = repository.findById(lectureId);
        Date timeNow = new Date();

        // Lecture does not exist
        if (foundLecture.isEmpty()) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Lecture by this ID does not exist.");
            return toBeReturned;
        }

        // Lecture has not started yet
        if (foundLecture.get().getStartTime().compareTo(timeNow) > 0) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "The lecture has not started yet.");
            return toBeReturned;
        }

        int usersWatchingLecture = userRepository.findAll().stream()
                .filter(x -> x.getKey().getLecture_id() == lectureId.intValue())
                .collect(Collectors.toList()).size();

        User newUser = new User(new UserKey(usersWatchingLecture, lectureId), userName, "127.0.0.1", null, 0);

        // Determine role of student
        // 1 - lecturer
        // 2 - moderator
        // 3 - student
        if (foundLecture.get().getStudentJoinId().equals(roleCode)) {
            newUser.setRoleID(3);
        } else if (foundLecture.get().getLecturerJoinId().equals(roleCode)) {
            newUser.setRoleID(1);
        } else if (foundLecture.get().getModeratorJoinId().equals(roleCode)) {
            newUser.setRoleID(2);
        } else {
            toBeReturned.put("success", false);
            toBeReturned.put("message", "Unrecognized roleCode/user ID.");
            return toBeReturned;
        }

        // if the lecture has ended don't let students join it.
        if(newUser.getRoleID().equals(3)){
            if(!foundLecture.get().isLectureOngoing()){
                toBeReturned.put("success", false);
                toBeReturned.put("message", "The lecture has ended.");
                return toBeReturned;
            }
        }

        userRepository.flush();

        try {
            userRepository.save(newUser);
            toBeReturned.put("success", true);
            toBeReturned.put("userID", newUser.getKey().getId());
            toBeReturned.put("userName", newUser.getName());
            toBeReturned.put("role", (newUser.getRoleID() == 1) ? "Student" : ((newUser.getRoleID() == 2) ? "Lecturer" : "Moderator"));
            toBeReturned.put("privilegeId", newUser.getRoleID());
        } catch (Exception e) {
            toBeReturned.put("success", false);
            toBeReturned.put("message", e.toString());
        }

        return toBeReturned;
    }

    /**
     * For now this method only returns the number of people watching the lecture
     * (meaning only this new functionality is implemented)
     *
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
        toBeReturned.put("people", usersWatchingLecture);
        toBeReturned.put("studentJoinID", foundLecture.get().getStudentJoinId());
        toBeReturned.put("moderatorJoinID", foundLecture.get().getModeratorJoinId());
        toBeReturned.put("lecturerJoinID", foundLecture.get().getLecturerJoinId());
        toBeReturned.put("lectureOngoing", foundLecture.get().isLectureOngoing());

        return toBeReturned;
    }

    /**
     * Changes lecture's lectureOngoing value to false.
     * @param userId - Id of the user
     * @param lectureId - Id of the lecture
     * @return status of the request
     */
    public Map<String, Object> endLecture(Integer userId, Integer lectureId) {
        Map<String, Object> status = new LinkedHashMap<>();

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }

        Optional<Lecture> lectureToBeEnded = repository.findById(lectureId);
        Optional<User> requestAuthor = userRepository.findById(new UserKey(userId, lectureId));

        if (lectureToBeEnded.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized lecture. Incorrect combination of lecture and question ids");
            return status;
        }

        if (requestAuthor.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user id or specified lecture does not exist!");
            return status;
        }

        if (!requestAuthor.get().getRoleID().equals(1)) {
            status.put("success", false);
            status.put("message", "You are not authorized to end this lecture!");
            return status;
        }

        if(!lectureToBeEnded.get().isLectureOngoing()){
            status.put("success", false);
            status.put("message", "The lecture has already been ended.");
            return status;
        }

        repository.flush();

        try {
            lectureToBeEnded.get().setLectureOngoing(false);
            repository.save(lectureToBeEnded.get());
            status.put("success", true);
            status.put("message", "The lecture was successfully ended.");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }
}
