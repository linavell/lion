package com.arin.togetherlion.copurchasing.repository;

import com.arin.togetherlion.copurchasing.domain.Copurchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopurchasingRepository extends JpaRepository<Copurchasing, Long> {
}
