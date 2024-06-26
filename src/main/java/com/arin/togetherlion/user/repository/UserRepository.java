package com.arin.togetherlion.user.repository;

import com.arin.togetherlion.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
