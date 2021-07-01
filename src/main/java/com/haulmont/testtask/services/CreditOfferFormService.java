package com.haulmont.testtask.services;

import com.haulmont.testtask.dtos.PaymentDto;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.utils.CreditOfferForm;
import com.haulmont.testtask.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditOfferFormService {
    private CreditOfferForm creditOfferForm;

    @Autowired
    public CreditOfferFormService(CreditOfferForm creditOfferForm){
        this.creditOfferForm = creditOfferForm;
    }

    public CreditOfferForm getCreditOfferForm(){

        return creditOfferForm;
    }

    @Transactional
    public List<PaymentDto> getCreditOfferSchedule() {
        return creditOfferForm.getPaymentSchedule(LocalDate.now());
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

    @Transactional
    public void deleteDuration() {
        creditOfferForm.setDuration(null);
        creditOfferForm.clearPaymentSchedule();
    }

    public void deleteClient() {
        creditOfferForm.getClientShortDto().clear();
    }

    @Transactional
    public void deleteCredit() {
        creditOfferForm.getCreditDto().clear();
        creditOfferForm.clearPaymentSchedule();

    }

    public BigDecimal getAmount() {
        return creditOfferForm.getAmount();
    }

    public BigDecimal getSumPercent() {
        return creditOfferForm.getSumPercentOfCredit();
    }
}
