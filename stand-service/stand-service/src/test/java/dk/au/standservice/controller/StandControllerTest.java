package dk.au.standservice.controller;

import dk.au.standservice.model.Stand;
import dk.au.standservice.service.StandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StandController.class)
public class StandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StandService standService;

    @Autowired
    private ObjectMapper objectMapper;

    private Stand testStand;

    @BeforeEach
    void setUp() {
        testStand = new Stand();
        testStand.setId(1L);
        testStand.setCustomerNumber("CUST001");
        testStand.setSquareMetres(25.5);
        testStand.setFair("Book Fair 2024");
        testStand.setLocation("Hall A, Section 3");
    }

    @Test
    void testEndpoint() throws Exception {
        mockMvc.perform(get("/stands/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test endpoint is working!"));
    }

    @Test
    void getAllStands_ShouldReturnListOfStands() throws Exception {
        when(standService.getAllStands()).thenReturn(Arrays.asList(testStand));

        mockMvc.perform(get("/stands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testStand.getId()))
                .andExpect(jsonPath("$[0].customerNumber").value(testStand.getCustomerNumber()))
                .andExpect(jsonPath("$[0].squareMetres").value(testStand.getSquareMetres()))
                .andExpect(jsonPath("$[0].fair").value(testStand.getFair()))
                .andExpect(jsonPath("$[0].location").value(testStand.getLocation()));
    }

    @Test
    void getStandById_WhenStandExists_ShouldReturnStand() throws Exception {
        when(standService.getStandById(1L)).thenReturn(Optional.of(testStand));

        mockMvc.perform(get("/stands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStand.getId()))
                .andExpect(jsonPath("$.customerNumber").value(testStand.getCustomerNumber()));
    }

    @Test
    void getStandById_WhenStandDoesNotExist_ShouldReturn404() throws Exception {
        when(standService.getStandById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/stands/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStand_ShouldReturnCreatedStand() throws Exception {
        when(standService.createStand(any(Stand.class))).thenReturn(testStand);

        mockMvc.perform(post("/stands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStand.getId()))
                .andExpect(jsonPath("$.customerNumber").value(testStand.getCustomerNumber()));
    }

    @Test
    void updateStand_WhenStandExists_ShouldReturnUpdatedStand() throws Exception {
        when(standService.updateStand(eq(1L), any(Stand.class))).thenReturn(testStand);

        mockMvc.perform(put("/stands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStand.getId()))
                .andExpect(jsonPath("$.customerNumber").value(testStand.getCustomerNumber()));
    }

    @Test
    void updateStand_WhenStandDoesNotExist_ShouldReturn404() throws Exception {
        when(standService.updateStand(eq(1L), any(Stand.class))).thenReturn(null);

        mockMvc.perform(put("/stands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStand)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStand_ShouldReturn200() throws Exception {
        mockMvc.perform(delete("/stands/1"))
                .andExpect(status().isOk());

        verify(standService).deleteStand(1L);
    }
} 