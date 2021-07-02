package com.haulmont.testtask.services;

import com.haulmont.testtask.models.Payment;
import com.haulmont.testtask.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment save (Payment payment){
            return paymentRepository.save(payment);
    }

}