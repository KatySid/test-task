package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {

    Optional<Bank> findById(Long id);

    Optional<Bank> findByTitle(String title);




}
