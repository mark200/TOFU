package nl.tudelft.oopp.group54.controllers.bans;

import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MockBanServiceImpl implements BanService {
    private static Map<BanKey, Ban> banMap = new TreeMap<>();


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
        BanKey mockBanKey = new BanKey("192.158.1.38", 123);

        Ban mockBan = new Ban(mockBanKey);
        banMap.put(mockBanKey, mockBan);

        status.put("success", true);
        status.put("userIP", "192.158.1.38");


        System.out.println(banMap.toString());
        return status;
    }
}