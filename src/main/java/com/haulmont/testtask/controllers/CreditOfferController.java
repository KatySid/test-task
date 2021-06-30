package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.ClientShortDto;
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
    public Page<Payment> getCreditOfferSchedule(@RequestParam (name = "p", defaultValue = "1") int page){
        if (page < 1) {
            page = 1;
        }
        PageRequest pageRequest=PageRequest.of(page-1, 10);
        List<Payment> paymentSchedule = creditOfferService.getCreditOfferSchedule();
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),paymentSchedule.size());
        Page<Payment> paymentSchedulePage = new PageImpl<Payment>(
                paymentSchedule.subList(start, end),
                pageRequest,
                paymentSchedule.size());
        return paymentSchedulePage;
    }

//    @GetMapping ("/paymentSchedule")
//    public Page<Payment> getCreditOfferSchedule(@RequestParam (name = "p", defaultValue = "1") int page, @RequestParam (name = "pageSize", defaultValue = "10") int pageSize){
//        if (page < 1) {
//            page = 1;
//        }
//        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
//        List<Payment> paymentSchedule = creditOfferService.getCreditOfferSchedule();
//        int start = toIntExact(pageRequest.getOffset());
//        int end = Math.min(start+pageRequest.getPageSize(),paymentSchedule.size());
//        Page<Payment> paymentSchedulePage = new PageImpl<Payment>(
//                paymentSchedule.subList(start, end),
//                pageRequest,
//                paymentSchedule.size());
//        return  paymentSchedulePage;
//    }

    @GetMapping("/addClient")
    public ClientShortDto addClient(@RequestParam Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            creditOfferService.addClient(client.get());
            return creditOfferService.getCreditOfferForm().getClientShortDto();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteClient")
    public void deleteClient() {
           creditOfferService.deleteClient();
    }

    @GetMapping("/percentPayment")
    public BigDecimal getPercentPayment() {
        return creditOfferService.getCreditOfferForm().getSumPercentOfCredit();
    }

    @GetMapping("/addDuration/{duration}")
    public Integer addDuration(@PathVariable(name = "duration") Integer duration) {
        creditOfferService.addDuration(duration);
        return creditOfferService.getCreditOfferForm().getDuration();
    }

    @GetMapping("/deleteDuration")
    public void deleteDuration() {
        creditOfferService.deleteDuration();
    }

    @GetMapping("/addCredit")
    public CreditDto addCredit(@RequestParam Long id) {
            Optional <Credit> credit = creditService.findById(id);
        if (credit.isPresent()) {
            creditOfferService.addCredit(credit.get());
        }
        return creditOfferService.getCreditOfferForm().getCreditDto();
    }

    @GetMapping("/deleteCredit")
    public void deleteCredit() {
        creditOfferService.deleteCredit();
    }

    @GetMapping("/clear")
    public void clearForm() {
        creditOfferService.clearForm();
    }

}
