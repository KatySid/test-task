package com.haulmont.testtask.utils;

import com.haulmont.testtask.dtos.ClientShortDto;
import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.dtos.PaymentDto;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditOfferForm {
        private ClientShortDto clientShortDto;
        private CreditDto creditDto;
        private BigDecimal amount;
        private BigDecimal sumPercent;
        private Integer duration;
        private List<PaymentDto> paymentSchedule;


    @PostConstruct
    public void init(){
        paymentSchedule = new ArrayList<>();
        this.amount = BigDecimal.ZERO;
        this.sumPercent = BigDecimal.ZERO;
        this.creditDto = new CreditDto();
        this.clientShortDto = new ClientShortDto();
        this.duration = 1;

    }

    public CreditOfferForm(Client client, Credit credit,Integer duration) {
        this.clientShortDto = new ClientShortDto(client);
        this.creditDto = new CreditDto(credit);
        this.duration = duration;
        this.paymentSchedule = new ArrayList<>(duration);
    }

    public void clearForm () {
            this.clientShortDto = null;
            this.creditDto = null;
            this.amount = null;
            this.duration = null;
            paymentSchedule.clear();
        }

    private void recalculatePaymentSchedule(LocalDate dateTime){
        paymentSchedule.removeAll(paymentSchedule);
        BigDecimal remainder = this.creditDto.getLimitation();
        BigDecimal bodyCreditPayment = remainder.divide(new BigDecimal(this.duration), 2, RoundingMode.HALF_UP);
                for (int i = 0; i < this.duration.intValue(); i++) {
                    PaymentDto paymentDto = new PaymentDto();
                    paymentDto.setBodyCreditPayment(bodyCreditPayment);
                    paymentDto.setDate(dateTime.plusMonths(i));
                    BigDecimal bodyPercentPayment = (remainder.multiply(calculatePercentRate())).setScale(2,RoundingMode.HALF_UP);
                    paymentDto.setPercentPayment(bodyPercentPayment);
                    BigDecimal amountPayment = paymentDto.getAmountPayment();
                    paymentSchedule.add(paymentDto);
                    remainder = remainder.subtract(bodyCreditPayment);
                };

        }

        public BigDecimal calculatePercentRate(){
            BigDecimal percentRate = this.creditDto.getPercent().divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
            percentRate = percentRate.divide(new BigDecimal(12),4,RoundingMode.HALF_UP);
            return percentRate;
        }

        public BigDecimal getSumPercent(){
            sumPercent = BigDecimal.ZERO;
            for (int i = 0; i < paymentSchedule.size(); i++) {
                sumPercent=sumPercent.add(paymentSchedule.get(i).getPercentPayment());
            }
            return sumPercent;
        }


    public BigDecimal getAmount() {
        amount=BigDecimal.ZERO;
        for (int i = 0; i < paymentSchedule.size(); i++) {
            amount = amount.add(paymentSchedule.get(i).getAmountPayment());
        }
        return amount;
    }

    public List<PaymentDto> getPaymentSchedule(LocalDate dateOffer){
        if(!duration.equals(null)&&!creditDto.getLimitation().equals(null)) {
            recalculatePaymentSchedule(dateOffer);
            return this.paymentSchedule;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<PaymentDto> getPaymentSchedule(){
        if(!duration.equals(null)&&!creditDto.getLimitation().equals(null)) {
            recalculatePaymentSchedule(LocalDate.now());
            return this.paymentSchedule;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void setClientShortDto(Client client) {
        this.clientShortDto = new ClientShortDto(client);
    }

    public void setCreditDto(Credit credit) {
        this.creditDto = new CreditDto(credit);
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public CreditDto getCreditDto() {
        return this.creditDto;
    }

    public ClientShortDto getClientShortDto() {
        return this.clientShortDto;
    }

    public void clearPaymentSchedule(){
        paymentSchedule.clear();
    }
}
