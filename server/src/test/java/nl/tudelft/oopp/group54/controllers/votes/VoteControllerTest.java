package nl.tudelft.oopp.group54.controllers.votes;

import nl.tudelft.oopp.group54.controllers.lectures.LectureController;
import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.controllers.questions.QuestionController;
import nl.tudelft.oopp.group54.controllers.questions.QuestionServiceImplementation;
import nl.tudelft.oopp.group54.entities.*;
import nl.tudelft.oopp.group54.repositories.LectureRepository;
import nl.tudelft.oopp.group54.repositories.QuestionRepository;
import nl.tudelft.oopp.group54.repositories.UserRepository;
import nl.tudelft.oopp.group54.repositories.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(MockitoExtension.class)
class VoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LectureServiceImpl lectureService;

    @Mock
    private VoteServiceImpl voteService;

    @Mock
    private QuestionServiceImplementation questionService;

    @InjectMocks
    private QuestionController questionController;

    @InjectMocks
    private LectureController lectureController;

    @InjectMocks
    private VoteController voteController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(lectureController, questionController, voteController).build();
        status = new TreeMap<>();
    }

    @Test
    void voteOnQuestion_withoutUserId() throws Exception {
        status.put("success", "false");
        status.put("message", "Expected isUpvote," + " userId to be provided");

        Map<String, Object> voted = voteController.voteOnQuestion(1, 1, new TreeMap<>());

        assertEquals(status, voted);
    }

    @Test
    void voteOnQuestion() {
        status.put("success", "true");

        Map<String, Object> requestBody = new TreeMap<>();
        requestBody.put("userId", "1");

        when(voteService.voteOnQuestion(anyInt(), anyString(), anyInt(), anyBoolean())).thenReturn(status);
        Map<String, Object> voted = voteController.voteOnQuestion(1, 1, requestBody);

        assertEquals(new TreeMap<>(), voted);
    }
}