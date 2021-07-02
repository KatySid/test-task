package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long>, JpaSpecificationExecutor<CreditOffer> {

    Optional<CreditOffer> findById(Long id);


}
