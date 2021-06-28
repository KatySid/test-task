package com.haulmont.testtask.utils;

import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditOfferForm {
        private Client client;
        private Credit credit;
        private BigDecimal amount;
        private Integer duration;
        private List<Payment> paymentSchedule;


    @PostConstruct
    public void init(){
        paymentSchedule = new ArrayList<>();
        this.amount = BigDecimal.ZERO;
        this.credit = new Credit();
        this.client = new Client();

    }

    public CreditOfferForm(Client client, Credit credit,Integer duration) {
        this.client = client;
        this.credit = credit;
        this.duration = duration;
        this.paymentSchedule = new ArrayList<>(duration);
    }

//    public  CreditOfferForm(){
//        this.paymentSchedule = new ArrayList<>();
//        this.amount = BigDecimal.ZERO;
//        this.duration = null;
//
//    }

    public void clearForm () {
            this.client = null;
            this.credit = null;
            this.amount = null;
            this.duration = null;
            paymentSchedule.clear();
        }

    public void recalculatePaymentSchedule(LocalDateTime dateTime){
        BigDecimal remainder = this.credit.getLimitation();
        BigDecimal bodyCreditPayment = remainder.divide(new BigDecimal(this.duration));
                for (int i = 0; i < this.duration.intValue(); i++) {
                    Payment payment = new Payment();
                    payment.setBodyCreditPayment(bodyCreditPayment);
                    payment.setDate(dateTime.plusMonths(i));
                    BigDecimal bodyPercentPayment = remainder.multiply(calculatePercentRate());
                    payment.setPercentPayment(bodyPercentPayment);
                    paymentSchedule.add(payment);
                    remainder = remainder.subtract(bodyCreditPayment);
                    amount.add(payment.getAmountPayment());
                };

        }

        public BigDecimal calculatePercentRate(){
            BigDecimal percentRate = this.credit.getPercent().divide(new BigDecimal(100/12));
            return percentRate;
        }

        public BigDecimal getSumPercentOfCredit(){
        BigDecimal sumPercentOfCredit = BigDecimal.ZERO;
        recalculatePaymentSchedule(LocalDateTime.now());
            for (int i = 0; i < paymentSchedule.size(); i++) {
                sumPercentOfCredit.add(paymentSchedule.get(i).getPercentPayment());
            }
            return sumPercentOfCredit;
        }

    public BigDecimal getAmount() {
        amount = BigDecimal.ZERO;
        recalculatePaymentSchedule(LocalDateTime.now());
        return amount;
    }
    public List<Payment> getPaymentSchedule(){
        recalculatePaymentSchedule(LocalDateTime.now());
        return this.paymentSchedule;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
