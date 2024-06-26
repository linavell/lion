package com.arin.togetherlion.copurchasing.domain.dto;

import com.arin.togetherlion.copurchasing.domain.ProductTotalCost;
import com.arin.togetherlion.copurchasing.domain.ShippingCost;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class CopurchasingCreateRequest {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Size(max = 500)
    private String content;

    @NotNull(message = "상품 총 금액은 필수 입력 값입니다.")
    private ProductTotalCost productTotalCost;

    @NotNull(message = "배송비는 필수 입력 값입니다.")
    private ShippingCost shippingCost;

    @NotBlank(message = "상품 페이지 url은 필수 입력 값입니다.")
    private String productUrl;

    private LocalDateTime expirationDate;

    @NotNull(message = "상품 최소 개수는 필수 입력 값입니다.")
    private int productMinNumber;

    @NotNull(message = "상품 최대 개수는 필수 입력 값입니다.")
    private int productMaxNumber;

    @NotNull(message = "모집 마감 일자는 필수 입력 값입니다.")
    private LocalDateTime deadlineDate;

    @NotNull(message = "거래 희망 일자는 필수 입력 값입니다.")
    private LocalDateTime tradeDate;

    private String purchasePhotoUrl;

    @NotNull(message = "작성자 Id는 필수 입력 값입니다.")
    private Long writerId;
}
