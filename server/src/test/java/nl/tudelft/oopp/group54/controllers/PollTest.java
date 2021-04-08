package nl.tudelft.oopp.group54.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.polls.PollController;
import nl.tudelft.oopp.group54.controllers.polls.PollServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PollTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    PollServiceImpl pollServiceImpl;

    @InjectMocks
    PollController pollController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pollController).build();
        status = new TreeMap<>();
    }

    @Test
    public void postPoll_NoInfo() throws Exception {
        status.put("success", "true");
        status.put("message", "Expected lectureId,"
                + " userId and question Text to be provided");

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/polls")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"message\":\"Expected lectureId, userId and question Text to be provided\"," +
                        "\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    public void postPoll() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.postPoll(anyInt(), anyString(), anyInt(), anyString(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/polls")
                .content("{\n"
                        +
                        "\"userId\": \"1\",\n"
                        +
                        "\"optionCount\": " + 2 + ",\n"
                        +
                        "\"correctAnswer\": \"A\",\n"
                        +
                        "\"title\": \"OOP\"\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }

    @Test
    public void votePoll_NoInfo() throws Exception {
        status.put("success", "false");
        status.put("message", "Expected lectureId, userID and vote to be provided");

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/polls/vote")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"message\":\"Expected lectureId, userID and vote to be provided\"," +
                        "\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    public void votePoll() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.votePoll(anyInt(), anyString(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/polls/vote")
                .content("{\n"
                        +
                        "\"userId\": \"1\",\n"
                        +
                        "\"vote\": \"A\"\n"
                        +"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}",
                result.getContentAsString());
    }

    @Test
    public void getAllQuestions() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.getCurrentPoll(anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .get("/lectures/1/polls?userId=1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}",
                result.getContentAsString());
    }

    @Test
    public void endCurrentPoll() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.endCurrentPoll(anyInt(), anyInt())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .put("/lectures/1/polls/end?userId=1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}",
                result.getContentAsString());
    }

    @Test
    public void getStatistics() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.getStatistics(anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .get("/lectures/1/polls/stats?userId=1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}",
                result.getContentAsString());
    }

    @Test
    public void reopenPoll() throws Exception {
        status.put("success", "true");

        when(pollServiceImpl.reopenPoll(anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .put("/lectures/1/polls/r?userId=1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}",
                result.getContentAsString());
    }

}