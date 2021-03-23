package nl.tudelft.oopp.group54.controllers.bans;

import nl.tudelft.oopp.group54.entities.*;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BanServiceImpl implements BanService {
    @Autowired
    private BanRepository banRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * ban an IP
     *
     * @param lectureId
     * @param questionId
     * @param userIp
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
            status.put("banIP", newBan.getPrimaryKey().getBanned_ip());
        } catch (Exception e) {
            status.put("success", false);
            status.put("message", e.toString());
        }

        return status;
    }
}