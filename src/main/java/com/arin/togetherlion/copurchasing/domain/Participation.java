package com.arin.togetherlion.copurchasing.domain;

import com.arin.togetherlion.common.BaseTimeEntity;
import com.arin.togetherlion.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_number", nullable = false)
    @NotNull(message = "구매 개수는 필수 입력 값입니다.")
    private int purchaseNumber;

    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "참여자는 필수 입력 값입니다.")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copurchasing_id")
    @NotNull(message = "공동구매 게시물은 필수 입력 값입니다.")
    private Copurchasing copurchasing;

    public boolean isConfirm() {
        if (confirmDate == null)
            return false;
        return true;
    }

    @Builder
    public Participation(int purchaseNumber, User user, Copurchasing copurchasing) {
        this.purchaseNumber = purchaseNumber;
        this.user = user;
        this.copurchasing = copurchasing;
    }
}
