package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.utils.CreditOfferForm;
import com.haulmont.testtask.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class CreditOfferDto {
    private Long id;
    private BigDecimal amount;
    private Integer duration;
    private ClientShortDto clientShortDto;
    private CreditDto creditDto;
    private List<PaymentDto> schedule;

    public CreditOfferDto(CreditOfferForm creditOfferForm){
        this.clientShortDto = creditOfferForm.getClientShortDto();
        this.creditDto = creditOfferForm.getCreditDto();
        this.duration = creditOfferForm.getDuration();
        this.amount = creditOfferForm.getAmount();
        this.schedule = creditOfferForm.getPaymentSchedule();

    }

    public CreditOfferDto(CreditOffer creditOffer){
        this.id = creditOffer.getId();
        this.clientShortDto = new ClientShortDto(creditOffer.getClient());
        this.creditDto = new CreditDto(creditOffer.getCredit());
        this.duration = creditOffer.getDuration();
        this.amount = creditOffer.getAmount();
        this.schedule = creditOffer.getPaymentSchedule().stream().map(new Function<Payment, PaymentDto>() {
            @Override
            public PaymentDto apply(Payment payment) {
                return new PaymentDto(payment);
            }
        }).collect(Collectors.toList());

    }


}
