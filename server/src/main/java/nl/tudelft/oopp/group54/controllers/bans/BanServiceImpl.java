package nl.tudelft.oopp.group54.controllers.bans;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.repositories.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BanServiceImpl implements BanService {
    @Autowired
    private BanRepository banRepository;

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