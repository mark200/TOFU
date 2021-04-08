package nl.tudelft.oopp.group54.controllers.votes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.TreeMap;

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
class VoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VoteServiceImpl voteService;

    @InjectMocks
    private VoteController voteController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(voteController).build();
        status = new TreeMap<>();
    }

    @Test
    void voteOnQuestion_withoutUserId() throws Exception {
        status.put("success", "false");
        status.put("message", "Expected isUpvote," + " userId to be provided");

        //when(voteService.voteOnQuestion(anyInt(), anyString(), anyInt(), anyBoolean())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/votes")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        assertEquals("{\"message\":\"Expected isUpvote, userId to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void voteOnQuestion() throws Exception {
        status.put("success", "true");

        when(voteService.voteOnQuestion(anyInt(), anyString(), anyInt(), anyBoolean())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/votes")
                .content("{ \"userId\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }
}