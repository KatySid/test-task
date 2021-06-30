package com.haulmont.testtask.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private String date; //дата платежа
    private BigDecimal amountPayment; // сумма платежа
    private BigDecimal percentPayment; // сумма гашения процента
    private BigDecimal bodyCreditPayment; //тело гашения кредита

    public void setDate(LocalDateTime date) {
        this.date = date.format(DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" )).replace("T", " ");
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
