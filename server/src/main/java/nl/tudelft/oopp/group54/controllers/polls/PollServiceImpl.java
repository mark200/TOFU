package nl.tudelft.oopp.group54.controllers.polls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.entities.Lecture;
import nl.tudelft.oopp.group54.entities.Poll;
import nl.tudelft.oopp.group54.entities.PollKey;
import nl.tudelft.oopp.group54.entities.PollVote;
import nl.tudelft.oopp.group54.entities.PollVoteKey;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;
import nl.tudelft.oopp.group54.entities.User;
import nl.tudelft.oopp.group54.entities.UserKey;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.PollRepository;
import nl.tudelft.oopp.group54.repositories.PollVoteRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    PollRepository pollRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;
    
    @Autowired
    PollVoteRepository pollVoteRepository;
    
    public PollServiceImpl() {
    
    }

    @Override
    public Map<String, Object> postPoll(Integer lectureId, String userId, Integer optionCount,
                                        String correctAnswer, String title) {
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

        if (optionCount == null || optionCount < 2 || optionCount > 10) {
            status.put("success", false);
            status.put("message", "optionCount cannot be null and must be between 2 and 10!");
            return status;
        }

        if (correctAnswer == null) {
            status.put("success", false);
            System.out.println(correctAnswer);
            status.put("message", "Question cannot be null and must be between 1 and 420 characters.");
            return status;
        }

        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }

        // don't let students make polls
        if (findUserRow.get().getRoleID().equals(3)) {
            if (!foundLecture.get().isLectureOngoing()) {
                status.put("success", false);
                status.put("message", "You are unauthorized to create a poll/quiz.");
                return status;
            }
        }
        
        List<Poll> findOpenPoll = pollRepository.findOpenPoll(lectureId);
        
        if (!findOpenPoll.isEmpty()) {
            status.put("success", false);
            status.put("message", "There is currently an ongoing poll!");
            return status;
        }
        
        

        Poll newPoll = new Poll(new PollKey(null, lectureId), title, false, optionCount, correctAnswer, new Date());
        pollRepository.flush();

        try {
            pollRepository.save(newPoll);
            status.put("success", true);
            status.put("message", "Poll/quiz has been created");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;

    }
    
    @Override
    public Map<String, Object> votePoll(Integer lectureId, String userId, String vote) {
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

        if (vote == null || !Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J").contains(vote)) {
            status.put("success", false);
            status.put("message", "Vote must be A character between A and J!");
            return status;
        }
        
        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }

        List<Poll> findPoll = pollRepository.findOpenPoll(lectureId);
        
        if (findPoll.isEmpty()) {
            status.put("success", false);
            status.put("message", "There is currently no ongoing poll!");
            return status;
        }
        
        Poll poll = findPoll.get(0);
        
        List<String> choices = new ArrayList<String>();
        
        for (int i = 0; i < poll.getOptionCount(); i++) {
            choices.add(i, Character.toString('A' + i));
        }
        
        if (!choices.contains(vote)) {
            status.put("success", false);
            status.put("message", "Vote must be between A and " +  choices.get(choices.size() - 1) + "!");
            return status;
        }
        
        PollVoteKey key = new PollVoteKey(poll.getPrimaryKey().getId(), lectureId, Integer.parseInt(userId));
        
        Optional<PollVote> findVote = pollVoteRepository.findById(key);
        
        if (findVote.isPresent()) {
            status.put("success", false);
            status.put("message", "You can only vote once per poll/quiz!");
            return status;
        }

        PollVote newPollVote = new PollVote(key, vote);
        pollVoteRepository.flush();

        try {
            pollVoteRepository.save(newPollVote);
            status.put("success", true);
            status.put("message", "Vote was placed");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;        
    }
    
    
    @Override
    public Map<String, Object> getCurrentPoll(Integer lectureId, String userId) {
        Map<String, Object> status = new LinkedHashMap<>();

        if  (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }
         
        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }
         
        List<Poll> findPoll = pollRepository.findOpenPoll(lectureId);
        
        if (findPoll.isEmpty()) {
            status.put("success", true);
            status.put("closed", true);
            status.put("message", "There is no poll ongoing!");
            return status;
        }
        
        Poll poll = findPoll.get(0);
        
        status.put("success", true);
        status.put("closed", false);
        status.put("optionCount", poll.getOptionCount());
        status.put("title", poll.getTitle());
    
        return status;
    }
    
    @Override
    public Map<String, Object> endCurrentPoll(Integer lectureId, Integer userId) {
        Map<String, Object> status = new LinkedHashMap<>();
        
        if  (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }
         
        Optional<User> findUserRow = userRepository.findById(new UserKey(userId, lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }
         
        List<Poll> findPoll = pollRepository.findOpenPoll(lectureId);
        
        if (findPoll.isEmpty()) {
            status.put("success", false);
            status.put("message", "There is currently no ongoing poll/quiz!");
            return status;
        }
        
        Poll poll = findPoll.get(0);
        
        poll.setClosed(true);
        
        try {
            pollRepository.save(poll);
            status.put("success", true);
            status.put("message", "Poll/Quiz was closed");
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }
        
        return status;
    }
    
    @Override
    public Map<String, Object> getStatistics(Integer lectureId, String userId) {
        Map<String, Object> status = new LinkedHashMap<>();
        
        if  (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (userId == null) {
            status.put("success", false);
            status.put("message", "UserID cannot be null!");
            return status;
        }
         
        Optional<User> findUserRow = userRepository.findById(new UserKey(Integer.parseInt(userId), lectureId));
        Optional<Lecture> foundLecture = lectureRepository.findById(lectureId);

        if (findUserRow.isEmpty()) {
            status.put("success", false);
            status.put("message", "Unrecognized user ID or specified lecture does not exist!");
            return status;
        }

        if (foundLecture.isEmpty()) {
            status.put("success", false);
            status.put("message", "There does not exist a lecture with this id.");
            return status;
        }
        
        List<Poll> polls = pollRepository.findAllByLectureId(lectureId);
        Poll newest = null;
        
        for (Poll p : polls) {
            if (newest == null || p.getCreatedAt().after(newest.getCreatedAt())) {
                newest = p;
            }
        }
        
        if (newest == null) {
            status.put("success", false);
            status.put("message", "There has not been a poll/quiz");
            return status;
        }
        
        if (!newest.getClosed() && findUserRow.get().getRoleID() == 3) {
            status.put("success", false);
            status.put("message", "This poll is still opem");
            return status;
        }
        
        List<PollVote> votes = pollVoteRepository.findAllByLectureIdAndPollId(lectureId, newest.getPrimaryKey().getId());
        
        Map<String, Integer> voteMap = new TreeMap<String, Integer>();
        
        for (int i = 0; i < newest.getOptionCount(); i++) {
            voteMap.put(Character.toString('A' + i), 0);
        }
        
        for (PollVote v : votes) {
            Integer count = voteMap.get(v.getVote());
            voteMap.put(v.getVote(), count + 1);
        }
        
        status.put("success", true);
        status.put("correctAnswer", newest.getCorrectChoice());
        status.put("statsMap", voteMap);
        status.put("voteCount", votes.size());
        
        return status;
    }

    public PollRepository getPollRepository() {
        return pollRepository;
    }

	public void setPollRepository(PollRepository pollRepository) {
		this.pollRepository = pollRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public LectureRepository getLectureRepository() {
		return lectureRepository;
	}

	public void setLectureRepository(LectureRepository lectureRepository) {
		this.lectureRepository = lectureRepository;
	}

	public PollVoteRepository getPollVoteRepository() {
		return pollVoteRepository;
	}

	public void setPollVoteRepository(PollVoteRepository pollVoteRepository) {
		this.pollVoteRepository = pollVoteRepository;
	}
    
    
}
