package com.arin.togetherlion.copurchasing.domain;

import com.arin.togetherlion.common.BaseTimeEntity;
import com.arin.togetherlion.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Participation extends BaseTimeEntity {
    @Id
    private Long id;

    private int number; // 구매 개수

    private Date confirmDate; // 구매확정 일자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "copurchasing_id")
    private Copurchasing copurchasing;

    public boolean isConfirm() {
        if (confirmDate == null)
            return false;
        return true;
    }
}
