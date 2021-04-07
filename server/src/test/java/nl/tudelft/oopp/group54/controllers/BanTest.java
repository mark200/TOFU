package nl.tudelft.oopp.group54.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.TreeMap;

import nl.tudelft.oopp.group54.controllers.bans.BanController;
import nl.tudelft.oopp.group54.controllers.bans.BanServiceImpl;
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
public class BanTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    BanServiceImpl banServiceImpl;

    @InjectMocks
    BanController banController;

    Map<String, Object> status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(banController).build();
        status = new TreeMap<>();
    }

    @Test
    public void banIp_NoUserIp() throws Exception {
        status.put("success", "true");
        status.put("message", "Expected userIp" + " to be provided");

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/ban")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"message\":\"Expected userIp to be provided\",\"success\":\"false\"}",
                result.getContentAsString());
    }

    @Test
    public void banIp() throws Exception {
        status.put("success", "true");

        when(banServiceImpl.banIp(anyInt(), anyInt(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/questions/1/ban")
                .content("{ \"userIp\": \"192.158.1.38\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);

        Assertions.assertEquals("{\"success\":\"true\"}", result.getContentAsString());
    }
}
