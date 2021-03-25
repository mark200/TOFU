package nl.tudelft.oopp.group54.controllers.bans;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.entities.Question;
import nl.tudelft.oopp.group54.entities.QuestionKey;

import nl.tudelft.oopp.group54.repositories.BanRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BanServiceImpl implements BanService {
    @Autowired
    private BanRepository banRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * ban an IP.
     *
     * @param lectureId lecture ID
     * @param questionId question ID
     * @param userIp user IP
     * @return status of the request
     */
    @Override
    public Map<String, Object> banIp(Integer lectureId, Integer questionId, String userIp) {
        Map<String, Object> status = new TreeMap<>();

        if (lectureId == null) {
            status.put("success", false);
            status.put("message", "LectureID cannot be null!");
            return status;
        }

        if (questionId == null) {
            status.put("success", false);
            status.put("message", "QuestionID cannot be null!");
            return status;
        }

        if (userIp == null) {
            status.put("success", false);
            status.put("message", "UserIP cannot be null!");
            return status;
        }


        Optional<Question> findQuestionRow = questionRepository.findById(new QuestionKey(questionId, lectureId));

        if (findQuestionRow.isEmpty()) {
            status.put("code", "404 NOT FOUND");
            status.put("success", false);
            status.put("message", "Specified question does not exist!");
            return status;
        }

        BanKey newBanKey = new BanKey(userIp, lectureId);

        Ban newBan = new Ban();
        newBan.setPrimaryKey(newBanKey);

        banRepository.flush();

        try {
            banRepository.save(newBan);
            status.put("success", true);
            status.put("banIP", newBan.getPrimaryKey().getBannedIP());
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }
}