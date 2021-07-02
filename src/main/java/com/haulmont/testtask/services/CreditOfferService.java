package com.haulmont.testtask.services;

import com.haulmont.testtask.dtos.PaymentDto;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.models.Payment;
import com.haulmont.testtask.repositories.CreditOfferRepository;
import com.haulmont.testtask.utils.CreditOfferForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditOfferService {
    private CreditOfferForm creditOfferForm;
    private ClientService clientService;
    private CreditService creditService;
    private PaymentService paymentService;
    private CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferService(CreditOfferForm creditOfferForm,
                              ClientService clientService,
                              CreditService creditService,
                              PaymentService paymentService,
                              CreditOfferRepository creditOfferRepository){
        this.creditOfferForm = creditOfferForm;
        this.clientService = clientService;
        this.creditService = creditService;
        this.paymentService = paymentService;
        this.creditOfferRepository = creditOfferRepository;
    }

    @Transactional
    public CreditOffer save() {
        CreditOffer creditOffer=new CreditOffer();
        Client client = clientService.findById(creditOfferForm.getClientShortDto().getId()).get();
        creditOffer.setClient(client);
        Credit credit = creditService.findById(creditOfferForm.getCreditDto().getId()).get();
        creditOffer.setCredit(credit);
        creditOffer.setAmount(creditOfferForm.getAmount());
        creditOffer.setDuration(creditOfferForm.getDuration());
        creditOffer.setSumPercent(creditOfferForm.getSumPercent());
        creditOffer = creditOfferRepository.save(creditOffer);
        for (PaymentDto paymentDto : creditOfferForm.getPaymentSchedule()) {
            Payment payment = new Payment(paymentDto);
            payment.setCreditOffer(creditOffer);
            paymentService.save(payment);
            creditOffer.getPaymentSchedule().add(payment);
        }
        creditOfferForm.clearForm();
        return creditOffer;

    }

    public CreditOffer findById(Long id) {
        return creditOfferRepository.findById(id).get();
    }

    public Page<CreditOffer> findPage(int page, int pageSize){
            return creditOfferRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public void deleteById(Long id) {
        if(creditOfferRepository.findById(id).isPresent()) {
            creditOfferRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    }

    public List<Payment> getCreditOfferSchedule(Long creditOfferId) {
        Optional<CreditOffer> creditOffer=creditOfferRepository.findById(creditOfferId);
        if(creditOffer.isPresent()) {
           return creditOffer.get().getPaymentSchedule();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    }
}
