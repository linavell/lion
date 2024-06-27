package com.arin.togetherlion.copurchasing.controller;

import com.arin.togetherlion.copurchasing.domain.ProductTotalCost;
import com.arin.togetherlion.copurchasing.domain.ShippingCost;
import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.service.CopurchasingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void whenValidRequest_thenReturns201() throws Exception {
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
}