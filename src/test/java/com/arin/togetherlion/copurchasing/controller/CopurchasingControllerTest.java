package com.arin.togetherlion.copurchasing.controller;

import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.service.CopurchasingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CopurchasingController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CopurchasingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CopurchasingService copurchasingService;

    @InjectMocks
    private CopurchasingController copurchasingController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("유효한 요청 시 201 응답을 반환한다.")
    void postSuccess() throws Exception {
        Long copurchasingId = 1L;
        CopurchasingCreateRequest validRequest = CopurchasingCreateRequest.builder()
                .title("title")
                .productTotalCost(10000)
                .shippingCost(5000)
                .productUrl("url")
                .productMinNumber(1)
                .productMaxNumber(10)
                .deadlineDate(LocalDateTime.now().plusDays(3))
                .tradeDate(LocalDateTime.now().plusDays(7))
                .writerId(1L)
                .build();

        // 모킹된 서비스 동작 정의
        when(copurchasingService.create(any(CopurchasingCreateRequest.class))).thenReturn(copurchasingId);

        // 요청 및 응답 검증
        mockMvc.perform(post("/copurchasings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/copurchasings/" + copurchasingId));
    }

    @Test
    @DisplayName("유효한 요청이 아닐 시 400 응답을 반환한다.")
    void postFail() throws Exception {
        Long copurchasingId = 1L;
        CopurchasingCreateRequest invalidRequest = CopurchasingCreateRequest.builder()
                .productTotalCost(10000)
                .shippingCost(5000)
                .productMinNumber(1)
                .deadlineDate(LocalDateTime.now().plusDays(3))
                .tradeDate(LocalDateTime.now().plusDays(7))
                .writerId(1L)
                .build();

        // 모킹된 서비스 동작 정의
        when(copurchasingService.create(any(CopurchasingCreateRequest.class))).thenReturn(null);

        // 요청 및 응답 검증
        mockMvc.perform(post("/copurchasings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 요청 시 201 응답을 반환한다.")
    void deleteSuccess() throws Exception {
        Long copurchasingId = 1L;
        Long userId = 2L;

        mockMvc.perform(delete("/copurchasings/{copurchasingId}", copurchasingId)
                        .contentType("application/json")
                        .content(userId.toString()))
                .andExpect(status().isOk());

        verify(copurchasingService).delete(userId, copurchasingId);
    }

    @Test
    @DisplayName("접근이 거부된 경우 403 응답을 반환한다.")
    void deleteAccessDenied() throws Exception {
        Long copurchasingId = 1L;
        Long userId = 2L;

        doThrow(new AccessDeniedException("Access is denied")).when(copurchasingService).delete(anyLong(), anyLong());

        mockMvc.perform(delete("/copurchasings/{copurchasingId}", copurchasingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userId.toString()))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access is denied"));

        verify(copurchasingService).delete(userId, copurchasingId);
    }
}