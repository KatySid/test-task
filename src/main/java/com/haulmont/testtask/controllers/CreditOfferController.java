package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.CreditOfferDto;
import com.haulmont.testtask.dtos.PaymentDto;
import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.models.Payment;
import com.haulmont.testtask.services.CreditOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("/api/v1/credit_offers")
@Slf4j

public class CreditOfferController {
    private CreditOfferService creditOfferService;

    @Autowired
    public CreditOfferController(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
    }

    @GetMapping
    public Page<CreditOfferDto> getPage(@RequestParam(name = "p", defaultValue = "1") int page) {
        if (page < 1) {
            page = 1;
        }
        return creditOfferService.findPage(page, 10).map(CreditOfferDto::new);
    }

    @GetMapping("/{id}")
    public CreditOfferDto findById(@PathVariable Long id) {
        CreditOfferDto creditOfferDto = new CreditOfferDto(creditOfferService.findById(id));
        return creditOfferDto;
    }

    @PostMapping
    public CreditOfferDto save() {
        CreditOffer creditOffer = creditOfferService.save();
        return new CreditOfferDto(creditOffer);
    }


    @DeleteMapping
    public void deleteCreditOfferById(@RequestParam Long id) {
        creditOfferService.deleteById(id);
    }


    @GetMapping("/paymentSchedule")
    public Page<PaymentDto> getCreditOfferSchedule(@RequestParam Long creditOfferId, @RequestParam(name = "p", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        if (page < 1) {
            page = 1;
        }
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        List<PaymentDto> paymentSchedule = creditOfferService.getCreditOfferSchedule(creditOfferId).stream().map(new Function<Payment, PaymentDto>() {
            @Override
            public PaymentDto apply(Payment payment) {
                return new PaymentDto(payment);
            }
        }).collect(Collectors.toList());
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start + pageRequest.getPageSize(), paymentSchedule.size());
        Page<PaymentDto> paymentSchedulePage = new PageImpl<PaymentDto>(
                paymentSchedule.subList(start, end),
                pageRequest,
                paymentSchedule.size());
        return paymentSchedulePage;
    }
}

