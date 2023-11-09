package com.ruslan.ibapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationsRepository extends JpaRepository<Operations,Long> {
    List<Operations> findAllByAccountIdAndOperDateBetween(Long id, LocalDateTime startDate, LocalDateTime endDate);

    List<Operations> findAllByAccountId(Long id);
}
