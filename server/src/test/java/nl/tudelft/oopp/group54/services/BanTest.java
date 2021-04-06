package nl.tudelft.oopp.group54.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.bans.BanServiceImpl;
import nl.tudelft.oopp.group54.entities.Ban;
import nl.tudelft.oopp.group54.entities.BanKey;
import nl.tudelft.oopp.group54.repositories.BanRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;




//import org.junit.Before;

@ExtendWith(MockitoExtension.class)
public class BanTest {

    @Mock
    private BanRepository banRepositoryMock;

    @Mock
    private QuestionRepository questionRepositoryMock;

    @InjectMocks
    private BanServiceImpl banService;

    static Ban ban1;
    static Ban ban2;
    static BanKey banKey1;
    static BanKey banKey2;

    @BeforeEach
    public void init() {
        banKey1 = new BanKey("192.158.1.38", 321);
        banKey2 = new BanKey("", 123);
        ban1 = new Ban(banKey1);
        ban2 = new Ban(banKey2);
    }

    @Test
    public void banIpTest_LectureIdNull() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "LectureID cannot be null!");

        Map<String, Object> created = banService.banIp(null, 123, "192.158.1.38" );
        assertEquals(status, created);
    }

    @Test
    public void banIpTest_QuestionIdNull() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "QuestionID cannot be null!");

        Map<String, Object> created = banService.banIp(123, null, "192.158.1.38" );
        assertEquals(status, created);
    }

    @Test
    public void banIpTest_UserIpNull() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "UserIP cannot be null!");

        Map<String, Object> created = banService.banIp(123, 123, null);
        assertEquals(status, created);
    }

    @Test
    public void banIpTest() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", true);
        toBeReturned.put("banIP", "192.158.1.38");

        when(banRepositoryMock.save(any(Ban.class))).thenReturn(new Ban());

        Map<String, Object> created = banService.banIp(123, 123, "192.158.1.38");
        assertTrue(toBeReturned.get("banIP").equals(created.get("banIP")));
        assertEquals(toBeReturned.get("success"), created.get("success"));
    }

    @Test
    public void banIpTest_exception() {
        Map<String, Object> status = new TreeMap<>();
        status.put("success", false);
        status.put("message", "java.lang.IllegalArgumentException: this is not good");

        when(banRepositoryMock.save(any(Ban.class))).thenThrow(new IllegalArgumentException("this is not good"));

        Map<String, Object> created = banService.banIp(123, 123, "192.158.1.38");
        assertEquals(status, created);
    }

}
