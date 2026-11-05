package com.example.hospital.controller;

import com.example.hospital.dto.DoctorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        doctorController.resetStorage();
    }

    @Test
    @DisplayName("1. GET /api/v1/doctors - Lấy danh sách toàn bộ bác sĩ (200 OK)")
    void testGetAllDoctors() throws Exception {
        mockMvc.perform(get("/api/v1/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("2. GET /api/v1/doctors/1 - Lấy thông tin chi tiết bác sĩ theo ID (200 OK)")
    void testGetDoctorById() throws Exception {
        mockMvc.perform(get("/api/v1/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("BS. Nguyễn Văn A"));
    }

    @Test
    @DisplayName("3. POST /api/v1/doctors - Thêm mới một bác sĩ (201 Created)")
    void testCreateDoctor() throws Exception {
        DoctorDTO dto = DoctorDTO.builder()
                .name("BS. Lê Văn C")
                .specialty("Ngoại khoa")
                .email("levanc@hospital.com")
                .phone("0934567890")
                .build();

        mockMvc.perform(post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("BS. Lê Văn C"))
                .andExpect(jsonPath("$.specialty").value("Ngoại khoa"));
    }

    @Test
    @DisplayName("4. PUT /api/v1/doctors/1 - Cập nhật thông tin bác sĩ (200 OK)")
    void testUpdateDoctor() throws Exception {
        DoctorDTO dto = DoctorDTO.builder()
                .name("BS. Nguyễn Văn A (Đã cập nhật)")
                .specialty("Tim mạch can thiệp")
                .email("nguyenvana_updated@hospital.com")
                .phone("0987654321")
                .build();

        mockMvc.perform(put("/api/v1/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BS. Nguyễn Văn A (Đã cập nhật)"))
                .andExpect(jsonPath("$.specialty").value("Tim mạch can thiệp"));
    }

    @Test
    @DisplayName("5. DELETE /api/v1/doctors/1 - Xóa bác sĩ khỏi hệ thống (204 No Content)")
    void testDeleteDoctor() throws Exception {
        mockMvc.perform(delete("/api/v1/doctors/1"))
                .andExpect(status().isNoContent());
    }
}
