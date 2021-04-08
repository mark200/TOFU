package nl.tudelft.oopp.group54.controllers.questions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.answers.AnswerController;
import nl.tudelft.oopp.group54.controllers.answers.AnswerServiceImpl;
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
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private QuestionServiceImplementation questionService;

    @InjectMocks
    private QuestionController questionController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
        status = new TreeMap<>();
    }

    @Test
    void postQuestion_withoutParams() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId, userId and question Text to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void postQuestion_withUserId() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions")
                .content("{ \"userId\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId, userId and question Text to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void postQuestion_withQuestionText() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions")
                .content("{ \"questionText\": \"question\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId, userId and question Text to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void postQuestion() throws Exception {
        status.put("success", "true");

        when(questionService.postQuestion(anyInt(), anyString(), anyString(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions")
                .content("{\n"
                        +
                        "    \"userId\": \"1\",\n"
                        +
                        "    \"questionText\": \"question\"\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }

    @Test
    void getAllQuestions() throws Exception {
        status.put("success", "true");

        when(questionService.getAllQuestions(anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .get("/lectures/1/questions")
                .param("userId", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }

    @Test
    void deleteQuestion() throws Exception {
        status.put("success", "true");

        when(questionService.deleteQuestion(anyInt(),anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/lectures/1/questions/1")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }

    @Test
    void editQuestion_withoutParams() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/edit")
                .param("userId", "1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId and new Content to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void editQuestion_withQuestionId() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/edit")
                .param("userId", "1")
                .content("{ \"questionId\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId and new Content to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void editQuestion_withNewContent() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/edit")
                .param("userId", "1")
                .content("{ \"newContent\": \"edited question\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId and new Content to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void editQuestion_noStrings() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/edit")
                .param("userId", "1")
                .content("{\n"
                        +
                        "    \"questionId\": 1,\n"
                        +
                        "    \"newContent\": 1\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"message\":\"Expected lectureId and new Content to be strings\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    void editQuestion() throws Exception {
        status.put("success", "true");

        when(questionService.editQuestion(anyInt(),anyInt(), anyString(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/edit")
                .param("userId", "1")
                .content("{\n"
                        +
                        "    \"questionId\": \"1\",\n"
                        +
                        "    \"newContent\": \"new question\"\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }
}