package com.arin.togetherlion.copurchasing.service;

import com.arin.togetherlion.copurchasing.domain.ProductTotalCost;
import com.arin.togetherlion.copurchasing.domain.ShippingCost;
import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.repository.CopurchasingRepository;
import com.arin.togetherlion.user.domain.User;
import com.arin.togetherlion.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
class CopurchasingServiceTest {

    @Autowired
    private CopurchasingRepository copurchasingRepository;
    @Autowired
    private UserRepository userRepository;
    private CopurchasingService copurchasingService;

    @BeforeEach
    void setUp() {
        copurchasingService = new CopurchasingService(copurchasingRepository, userRepository);
    }

    @Test
    @DisplayName("존재하지 않는 사용자가 게시물을 작성할 시 예외가 발생한다.")
    void notExistedUserCreate() throws Exception {
        //given
        Long notExistedUserId = 0L;
        final CopurchasingCreateRequest request = CopurchasingCreateRequest.builder()
                .title("title")
                .productMinNumber(1)
                .productTotalCost(new ProductTotalCost(10000))
                .purchasePhotoUrl("url")
                .tradeDate(LocalDateTime.of(2024, 6, 25, 0, 0, 0))
                .deadlineDate(LocalDateTime.of(2024, 6, 23, 0, 0, 0))
                .productMaxNumber(5)
                .content("content")
                .productUrl("url")
                .shippingCost(new ShippingCost(3000))
                .writerId(notExistedUserId)
                .build();

        //when
        //then
        Assertions.assertThatThrownBy(() -> copurchasingService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("사용자는 공동구매 게시물을 작성할 수 있다.")
    void create() throws Exception {
        //given
        final User user = User.builder()
                .email("email")
                .password("password")
                .nickname("nickname")
                .build();

        Long userId = userRepository.save(user).getId();

        final CopurchasingCreateRequest request = CopurchasingCreateRequest.builder()
                .title("title")
                .productMinNumber(1)
                .productTotalCost(new ProductTotalCost(10000))
                .purchasePhotoUrl("url")
                .tradeDate(LocalDateTime.of(2024, 6, 25, 0, 0, 0))
                .deadlineDate(LocalDateTime.of(2024, 6, 23, 0, 0, 0))
                .productMaxNumber(5)
                .content("content")
                .productUrl("url")
                .shippingCost(new ShippingCost(3000))
                .writerId(userId)
                .build();
        
        //when
        final Long copurchasingId = copurchasingService.create(request);

        //then
        Assertions.assertThat(copurchasingRepository.existsById(copurchasingId)).isTrue();

    }

}