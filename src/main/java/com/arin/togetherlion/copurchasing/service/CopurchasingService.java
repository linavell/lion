package com.arin.togetherlion.copurchasing.service;

import com.arin.togetherlion.copurchasing.domain.Copurchasing;
import com.arin.togetherlion.copurchasing.domain.Participation;
import com.arin.togetherlion.copurchasing.domain.ProductTotalCost;
import com.arin.togetherlion.copurchasing.domain.ShippingCost;
import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.repository.CopurchasingRepository;
import com.arin.togetherlion.user.domain.User;
import com.arin.togetherlion.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

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
                .productTotalCost(new ProductTotalCost(request.getProductTotalCost()))
                .shippingCost(new ShippingCost(request.getShippingCost()))
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

    @Transactional
    public void delete(Long userId, Long copurchasingId) throws AccessDeniedException {
        final Copurchasing copurchasing = copurchasingRepository.findById(copurchasingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공동구매 게시물입니다."));

        if (!userId.equals(copurchasing.getWriter().getId()))
            throw new AccessDeniedException("게시물 작성자만 삭제할 수 있습니다.");

        if (isStarted(copurchasing))
            throw new IllegalArgumentException("이미 시작된 공동구매 게시물은 삭제할 수 없습니다.");
        else
            copurchasingRepository.delete(copurchasing);
    }

    private boolean isStarted(Copurchasing copurchasing) {
        List<Participation> participations = copurchasing.getParticipations();
        if (copurchasing.getProductMaxNumber() <= participations.size())
            return true;

        if (copurchasing.getDeadlineDate().isBefore(LocalDateTime.now()) && copurchasing.getProductMinNumber() <= participations.size())
            return true;

        return false;
    }

}
