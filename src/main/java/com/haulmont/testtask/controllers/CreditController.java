package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.repositories.CreditSpecifications;
import com.haulmont.testtask.services.BankService;
import com.haulmont.testtask.services.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;
    private final BankService bankService;

    @GetMapping
    public Page<CreditDto> getAllCredits(@RequestParam MultiValueMap<String, String> params, @RequestParam (name = "p", defaultValue = "1") int page) {
        if (page < 1) {
            page = 1;
        }
        return creditService.findPage(CreditSpecifications.build(params), page, 10).map(CreditDto::new);
    }


    @GetMapping ("/{id}")
    public CreditDto getCreditById(@PathVariable Long id) {
        Credit credit = creditService.findById(id).orElseThrow();
        return new CreditDto(credit);
    }

    @PostMapping
    public CreditDto createNewCredit(@RequestBody CreditDto creditDto) {
        Credit credit = new Credit(creditDto.getLimitation(), creditDto.getPercent());
        if(creditDto.getBankId() != null){
            Optional<Bank> optionalBank = bankService.findById(creditDto.getBankId());
            if (optionalBank.isPresent()) {
            credit.setBank(optionalBank.get());
            }
        }
        return new CreditDto(creditService.save(credit));
    }

    @PutMapping
    public CreditDto updateCredit(@RequestBody CreditDto creditDto) {
        Credit credit= new Credit(creditDto.getId(), creditDto.getLimitation(), creditDto.getPercent());

        return new CreditDto(creditService.updateCredit(credit));
    }

    @DeleteMapping
    public void deleteCreditById(@RequestParam Long id) {
        creditService.deleteById(id);
    }


}
