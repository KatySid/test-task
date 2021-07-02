package com.haulmont.testtask.services;

import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public Optional<Credit> findById(Long id) {
        return creditRepository.findById(id);
    }

    public Optional<Credit> findByLimitation(BigDecimal limitation) {
        return creditRepository.findByLimitation(limitation);
    }

    public Page<Credit> findPage(Specification<Credit> spec, int page, int pageSize){

        return creditRepository.findAll(spec, PageRequest.of(page - 1, pageSize));

    }

    @Transactional
    public Credit save (Credit credit){
            return creditRepository.save(credit);
    }

    @Transactional
    public Credit updateCredit(Credit credit){
        if (creditRepository.findById(credit.getId()).isPresent()){
            return creditRepository.save(credit);
        } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteById(Long id) {
            if(creditRepository.findById(id).isPresent()) {
                creditRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        }

}