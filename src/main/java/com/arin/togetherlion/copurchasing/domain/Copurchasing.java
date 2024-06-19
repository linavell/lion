package com.arin.togetherlion.copurchasing.domain;

import com.arin.togetherlion.common.BaseTimeEntity;
import com.arin.togetherlion.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Copurchasing extends BaseTimeEntity {

    @Id
    private Long id;

    private String title;

    private String content;

    private int totalPrice; // 상품 총 금액 (상품 금액 + 배송비)

    private String productUrl; // 공동구매할 상품 URL 경로

    private Date expirationDate; // 유통기한

    private int minNumber; // 최소 상품 개수

    private int maxNumber; // 최대 상품 개수

    private Date deadlineDate; // 모집마감일자

    private Date tradeDate; // 거래희망일자

    private String buyPhotoUrl; // 상품 구매 인증샷 경로

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "copurchasing")
    private List<Participation> participationList = new ArrayList<>();

    public void addParticipation(Participation participation) {
        this.participationList.add(participation);
    }

}
