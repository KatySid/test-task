package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.models.Payment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentDto {

    private Long id;
    private String date; //дата платежа
    private BigDecimal amountPayment; // сумма платежа
    private BigDecimal percentPayment; // сумма гашения процента
    private BigDecimal bodyCreditPayment; //тело гашения кредита

    public PaymentDto() {
    }

    public PaymentDto(Payment payment){
        this.id = payment.getId();
        this.date = payment.getLocalDate().format(DateTimeFormatter.ofPattern( "dd-MM-uuuu" )).replace("T", " ");
        this.amountPayment = payment.getAmountPayment();
        this.percentPayment = payment.getPercentPayment();
        this.bodyCreditPayment = payment.getBodyCreditPayment();
    }

    public void setDate(LocalDateTime date) {
        this.date = date.format(DateTimeFormatter.ofPattern( "dd-MM-uuuu" )).replace("T", " ");
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

    public String getDate() {
        return date;
    }

    public BigDecimal getPercentPayment() {
        return percentPayment;
    }

    public BigDecimal getBodyCreditPayment() {
        return bodyCreditPayment;
    }

}
