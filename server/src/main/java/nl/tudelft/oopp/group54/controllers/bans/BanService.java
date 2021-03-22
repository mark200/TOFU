package nl.tudelft.oopp.group54.controllers.bans;

import java.util.Map;

public interface BanService {
    Map<String, Object> banIp(Integer lectureId, Integer questionId, String userIp);
}