package com.haulmont.testtask.services;

import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.utils.CreditOfferForm;
import com.haulmont.testtask.utils.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditOfferService {
    private CreditOfferForm creditOfferForm;

    @Autowired
    public CreditOfferService (CreditOfferForm creditOfferForm){
        this.creditOfferForm = creditOfferForm;
    }

    public CreditOfferForm getCreditOfferForm(){

        return creditOfferForm;
    }

    @Transactional
    public List<Payment> getCreditOfferSchedule() {
        creditOfferForm.recalculatePaymentSchedule(LocalDateTime.now());
        return creditOfferForm.getPaymentSchedule();
    }

    public void addClient(Client client){
        creditOfferForm.setClientShortDto(client);
    }

    public void addCredit(Credit credit){
        creditOfferForm.setCreditDto(credit);
    }

    public void clearForm() {
        creditOfferForm.clearForm();
    }

    @Transactional
    public Integer addDuration(Integer duration) {
        creditOfferForm.setDuration(duration);
        return creditOfferForm.getDuration();
    }

    public void deleteDuration() {
        creditOfferForm.setDuration(null);
    }

    public void deleteClient() {
        creditOfferForm.getClientShortDto().clear();
    }

    public void deleteCredit() {
        creditOfferForm.getCreditDto().clear();
    }
}
