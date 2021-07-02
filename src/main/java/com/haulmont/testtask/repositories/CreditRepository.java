package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, JpaSpecificationExecutor<Credit> {

    Optional<Credit> findByLimitation(BigDecimal limitation);
    Optional<Credit> findById(Long id);
    Optional<Credit> findByPercent(BigDecimal percent);
    Optional<Credit> findByLimitationAndPercent(BigDecimal limitation, BigDecimal percent);



}
