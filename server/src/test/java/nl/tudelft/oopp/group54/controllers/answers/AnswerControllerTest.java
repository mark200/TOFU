package nl.tudelft.oopp.group54.controllers.answers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.votes.VoteController;
import nl.tudelft.oopp.group54.controllers.votes.VoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AnswerServiceImpl answerService;

    @InjectMocks
    private AnswerController answerController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        status = new TreeMap<>();
    }

    @Test
    void answerQuestion_withoutParams() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/answer")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected userId,  and answerText to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void answerQuestion_withUserId() throws Exception {
        status.put("success", "true");

        //when(answerService.answerQuestion(anyInt(), anyString(), anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/answer")
                .content("{ \"userId\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected userId,  and answerText to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void answerQuestion_withAnswerText() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/answer")
                .content("{ \"answerText\": \"answer\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected userId,  and answerText to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void answerQuestion() throws Exception {
        status.put("success", "true");

        when(answerService.answerQuestion(anyInt(), anyString(), anyInt(), anyString())).thenReturn(status);
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/answer")
                .content("{\n"
                        +
                        "    \"userId\": \"1\",\n"
                        +
                        "    \"answerText\": \"answer\"\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }
}