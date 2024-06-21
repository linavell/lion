package com.arin.togetherlion.user.domain;

import com.arin.togetherlion.common.BaseTimeEntity;
import com.arin.togetherlion.copurchasing.domain.Copurchasing;
import com.arin.togetherlion.copurchasing.domain.Participation;
import com.arin.togetherlion.point.domain.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @Column(nullable = false)
    @Embedded
    private Point point;

    @OneToMany(mappedBy = "user")
    private List<Copurchasing> copurchasings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Participation> participations = new ArrayList<>();

    public void addCopurchasing(Copurchasing copurchasing) {
        this.copurchasings.add(copurchasing);
    }

    public void addParticipation(Participation participation) {
        this.participations.add(participation);
    }

    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.point = new Point();
    }
}
