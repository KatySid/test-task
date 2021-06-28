package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    Optional<Client> findByEmail(String email);
    Optional<Client> findById(Long id);
    Optional<Client> findByPassport(String passport);
//    Page<Client> findAllByBankInBanksList(Bank bank, Pageable pageable);



}
