package com.arin.togetherlion.copurchasing.service;

import com.arin.togetherlion.copurchasing.domain.Copurchasing;
import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.repository.CopurchasingRepository;
import com.arin.togetherlion.user.domain.User;
import com.arin.togetherlion.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CopurchasingService {

    private final CopurchasingRepository copurchasingRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(CopurchasingCreateRequest request) {
        final User writer = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        final Copurchasing copurchasing = Copurchasing.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .productTotalCost(request.getProductTotalCost())
                .shippingCost(request.getShippingCost())
                .productUrl(request.getProductUrl())
                .expirationDate(request.getExpirationDate())
                .productMinNumber(request.getProductMinNumber())
                .productMaxNumber(request.getProductMaxNumber())
                .deadlineDate(request.getDeadlineDate())
                .tradeDate(request.getTradeDate())
                .purchasePhotoUrl(request.getPurchasePhotoUrl())
                .writer(writer)
                .build();

        return copurchasingRepository.save(copurchasing).getId();
    }

}
