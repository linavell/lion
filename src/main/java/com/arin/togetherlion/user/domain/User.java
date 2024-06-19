package com.arin.togetherlion.user.domain;

import com.arin.togetherlion.common.BaseTimeEntity;
import com.arin.togetherlion.copurchasing.domain.Copurchasing;
import com.arin.togetherlion.copurchasing.domain.Participation;
import com.arin.togetherlion.point.domain.Point;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Embedded
    private Point point;

    @OneToMany(mappedBy = "user")
    private List<Copurchasing> copurchasingsList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Participation> participationList = new ArrayList<>();

    public void addCopurchasing(Copurchasing copurchasing) {
        this.copurchasingsList.add(copurchasing);
    }

    public void addParticipation(Participation participation) {
        this.participationList.add(participation);
    }
}
