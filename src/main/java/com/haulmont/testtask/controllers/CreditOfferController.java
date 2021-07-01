package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.dtos.CreditOfferDto;
import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.repositories.CreditSpecifications;
import com.haulmont.testtask.services.CreditOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit_offers")
@Slf4j

public class CreditOfferController {
    private CreditOfferService creditOfferService;

    @Autowired
    public CreditOfferController(CreditOfferService creditOfferService){
        this.creditOfferService = creditOfferService;
    }

    @GetMapping
    public Page<CreditOfferDto> getPage(@RequestParam (name = "p", defaultValue = "1") int page) {
        if (page < 1) {
            page = 1;
        }
        return creditOfferService.findPage( page, 10).map(CreditOfferDto::new);
    }

    @GetMapping("/id")
    public CreditOfferDto findById(@PathVariable Long id){
        CreditOfferDto creditOfferDto = new CreditOfferDto(creditOfferService.findById(id));
        return creditOfferDto;
    }

    @PostMapping
    public CreditOfferDto save(){
        CreditOffer creditOffer = creditOfferService.save();
        return new CreditOfferDto(creditOffer);
        }
    }

