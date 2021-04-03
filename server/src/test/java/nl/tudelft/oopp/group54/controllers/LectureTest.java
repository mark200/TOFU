//package nl.tudelft.oopp.group54.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jayway.jsonpath.JsonPath;
//import nl.tudelft.oopp.group54.controllers.lectures.LectureController;
//import nl.tudelft.oopp.group54.controllers.lectures.LectureService;
//import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
//import nl.tudelft.oopp.group54.entities.Lecture;
//import nl.tudelft.oopp.group54.models.requestentities.CreateLectureRequest;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.client.match.ContentRequestMatchers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//
//import java.util.Date;
//import java.util.Map;
//import java.util.TreeMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(Lecture.class)
//public class LectureTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private LectureServiceImpl lectureService;
//
//
//    @Before
//    public void init() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new LectureController()).build();
//    }
//
//    @Test
//    public void createLectureTest() throws Exception {
//
//        Map<String, Object> status = new TreeMap<>();
//        status.put("success", true);
//
//        when(lectureService.createNewLecture(any(Date.class), anyString())).thenReturn(status);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
//        .post("/lectures")
//        .content("asdas")
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
//        .andReturn();
//
//        System.out.println(result.getResponse().getContentAsString());
//        //System.out.println(result.getRequest().getContentAsString());
//
//        assertEquals(status.toString(), result.getResponse().getContentAsString());
//    }
//
//
//    public static String asJsonString(final Object obj) {
//        try {
//            final ObjectMapper mapper = new ObjectMapper();
//            final String jsonContent = mapper.writeValueAsString(obj);
//            return jsonContent;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
