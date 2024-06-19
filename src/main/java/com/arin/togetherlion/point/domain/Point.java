package com.arin.togetherlion.point.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Point {
    private int amount;

    public Point(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("포인트는 음수가 될 수 없습니다.");
        }
        this.amount = amount;
    }

    public void plusPoint(int amount) {
        this.amount += amount;
    }

    public void minusPoint(int amount) {
        if (this.amount - amount < 0) {
            throw new IllegalArgumentException("포인트는 음수가 될 수 없습니다.");
        }
        this.amount -= amount;
    }
}
