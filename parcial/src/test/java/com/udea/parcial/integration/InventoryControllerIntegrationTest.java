package com.udea.parcial.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.parcial.dto.InventoryRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
public class InventoryControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetInventoryWithoutVersionHeader_ShouldFail() throws Exception {
        mockMvc.perform(get("/api/v1/inventory")
                        .param("warehouseId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetInventoryWithVersionHeader_ShouldSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/inventory")
                        .param("warehouseId", "1")
                        .header("X-API-VERSION", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    public void testPostInventory_ShouldCreateInventoryRecord() throws Exception {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setWarehouseId(1L);
        request.setProductId(1L);
        request.setCantidad(100);

        mockMvc.perform(post("/api/v1/inventory")
                        .header("X-API-VERSION", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(100))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.ver-inventario").exists());
    }
}
