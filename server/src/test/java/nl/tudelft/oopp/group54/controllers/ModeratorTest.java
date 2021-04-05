package nl.tudelft.oopp.group54.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.TreeMap;
import nl.tudelft.oopp.group54.controllers.moderator.MockModeratorServiceImplementation;
import nl.tudelft.oopp.group54.controllers.moderator.ModeratorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModeratorTest {

    @Mock
    MockModeratorServiceImplementation moderatorServiceImplementation;

    @InjectMocks
    ModeratorController moderatorController;

    @Test
    public void moderatorControllerDoesNotContainInfo() {
        Map<String, Object> toBeReturned = new TreeMap<>();
        toBeReturned.put("success", "false");
        toBeReturned.put("message", "Here's why you couldn't ban this person");

        //when(moderatorServiceImplementation.banStudentIP(1L, "1")).thenReturn(new TreeMap<>());
        Map<String, Object> created = moderatorController.banStudentIP(1L, new TreeMap<>());

        assertEquals(toBeReturned, created);
    }

    @Test
    public void moderatorControllerDoesContainInfo() {
        Map<String, Object> toBeReturned = new TreeMap<>();

        Map<String, Object> requestBody = new TreeMap<>();
        requestBody.put("userID", 1L);

        when(moderatorServiceImplementation.banStudentIP(1L, "123456")).thenReturn(new TreeMap<>());
        Map<String, Object> created = moderatorController.banStudentIP(1L, requestBody);

        assertEquals(toBeReturned, created);
    }
}
