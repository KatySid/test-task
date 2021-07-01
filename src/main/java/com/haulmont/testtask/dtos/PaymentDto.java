package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.Payment;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDto {

    private Long id;
    private LocalDate date; //дата платежа
    private BigDecimal amountPayment; // сумма платежа
    private BigDecimal percentPayment; // сумма гашения процента
    private BigDecimal bodyCreditPayment; //тело гашения кредита

    public PaymentDto() {
    }

    public PaymentDto(Payment payment){
        this.id = payment.getId();
        this.date = payment.getLocalDate();
        this.amountPayment = payment.getAmountPayment();
        this.percentPayment = payment.getPercentPayment();
        this.bodyCreditPayment = payment.getBodyCreditPayment();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setPercentPayment(BigDecimal percentPayment) {
        this.percentPayment = percentPayment;
    }

    public void setBodyCreditPayment(BigDecimal bodyCreditPayment) {
        this.bodyCreditPayment = bodyCreditPayment;
    }

    public BigDecimal getAmountPayment(){
        amountPayment=percentPayment.add(bodyCreditPayment);
        return amountPayment;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getPercentPayment() {
        return percentPayment;
    }

    public BigDecimal getBodyCreditPayment() {
        return bodyCreditPayment;
    }

}
