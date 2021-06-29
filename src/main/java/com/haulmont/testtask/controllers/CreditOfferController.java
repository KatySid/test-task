package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.services.ClientService;
import com.haulmont.testtask.services.CreditOfferService;
import com.haulmont.testtask.services.CreditService;
import com.haulmont.testtask.utils.CreditOfferForm;
import com.haulmont.testtask.utils.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.toIntExact;


@RestController
@RequestMapping("/api/v1/credit_offers")
@Slf4j

public class CreditOfferController {
    private  CreditOfferService creditOfferService;
    private ClientService clientService;
    private CreditService creditService;

    @Autowired
    public CreditOfferController (CreditOfferService creditOfferService, ClientService clientService, CreditService creditService){
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @GetMapping
    public CreditOfferForm getCreditOffer(){
        return creditOfferService.getCreditOfferForm();
    }

    @GetMapping ("/paymentSchedule")
    public Page<Payment> getCreditOfferSchedule(@RequestParam BigDecimal amount, @RequestParam (name = "p", defaultValue = "1") int page, @RequestParam (name = "pageSize", defaultValue = "10") int pageSize){
        if (page < 1) {
            page = 1;
        }
        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
        List<Payment> paymentSchedule = creditOfferService.getCreditOfferSchedule();
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),paymentSchedule.size());
        Page<Payment> paymentSchedulePage = new PageImpl<Payment>(
                paymentSchedule.subList(start, end),
                pageRequest,
                paymentSchedule.size());
        return  paymentSchedulePage;
    }

    @GetMapping("/addClient")
    public void addClient(@RequestParam Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            creditOfferService.addClient(client.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/percentPayment")
    public BigDecimal getPercentPayment() {
        return creditOfferService.getCreditOfferForm().getSumPercentOfCredit();
    }

    @GetMapping("/addDuration")
    public Integer addDuration(@RequestParam Integer duration) {
        return creditOfferService.addDuration(duration);
    }

    @GetMapping("/addCredit")
    public CreditDto addCredit(@RequestParam Long id) {
            Optional <Credit> credit = creditService.findById(id);
        if (credit.isPresent()) {
            creditOfferService.addCredit(credit.get());
        }
        return creditOfferService.getCreditOfferForm().getCreditDto();
    }

    @GetMapping("/clear")
    public void clearForm() {
        creditOfferService.clearForm();
    }

}
