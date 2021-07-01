package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.ClientShortDto;
import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.dtos.PaymentDto;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.services.ClientService;
import com.haulmont.testtask.services.CreditOfferFormService;
import com.haulmont.testtask.services.CreditService;
import com.haulmont.testtask.utils.CreditOfferForm;
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
@RequestMapping("/api/v1/credit_offers_form")
@Slf4j

public class CreditOfferFormController {
    private CreditOfferFormService creditOfferFormService;
    private ClientService clientService;
    private CreditService creditService;

    @Autowired
    public CreditOfferFormController(CreditOfferFormService creditOfferFormService, ClientService clientService, CreditService creditService){
        this.creditOfferFormService = creditOfferFormService;
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @GetMapping
    public CreditOfferForm getCreditOffer(){
        return creditOfferFormService.getCreditOfferForm();
    }

    @GetMapping ("/paymentSchedule")
    public Page<PaymentDto> getCreditOfferSchedule(@RequestParam (name = "p", defaultValue = "1") int page, @RequestParam (name = "pageSize", defaultValue = "10") int pageSize){
        if (page < 1) {
            page = 1;
        }
        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
        List<PaymentDto> paymentSchedule = creditOfferFormService.getCreditOfferSchedule();
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),paymentSchedule.size());
        Page<PaymentDto> paymentSchedulePage = new PageImpl<PaymentDto>(
                paymentSchedule.subList(start, end),
                pageRequest,
                paymentSchedule.size());
        return  paymentSchedulePage;
    }

    @GetMapping("/addClient")
    public ClientShortDto addClient(@RequestParam Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            creditOfferFormService.addClient(client.get());
            return creditOfferFormService.getCreditOfferForm().getClientShortDto();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteClient")
    public void deleteClient() {
           creditOfferFormService.deleteClient();
    }

    @GetMapping("/percentPayment")
    public BigDecimal getPercentPayment() {
        return creditOfferFormService.getCreditOfferForm().getSumPercentOfCredit();
    }

    @GetMapping("/addDuration/{duration}")
    public Integer addDuration(@PathVariable(name = "duration") Integer duration) {
        creditOfferFormService.addDuration(duration);
        return creditOfferFormService.getCreditOfferForm().getDuration();
    }

    @GetMapping("/deleteDuration")
    public void deleteDuration() {
        creditOfferFormService.deleteDuration();
    }

    @GetMapping("/addCredit")
    public CreditDto addCredit(@RequestParam Long id) {
            Optional <Credit> credit = creditService.findById(id);
        if (credit.isPresent()) {
            creditOfferFormService.addCredit(credit.get());
        }
        return creditOfferFormService.getCreditOfferForm().getCreditDto();
    }

    @GetMapping("/deleteCredit")
    public void deleteCredit() {
        creditOfferFormService.deleteCredit();
    }

    @GetMapping("/clear")
    public void clearForm() {
        creditOfferFormService.clearForm();
    }

    @GetMapping ("/amount")
    public BigDecimal getAmount(){
        return creditOfferFormService.getAmount();
    }

    @GetMapping ("/sumPercent")
    public BigDecimal getSumPercent(){
        return creditOfferFormService.getSumPercent();
    }
}
