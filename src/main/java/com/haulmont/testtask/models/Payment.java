package com.haulmont.testtask.models;

import com.haulmont.testtask.dtos.PaymentDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "localdate")
    private LocalDate localdate; //дата платежа

    @Column (name = "amount_payment")
    @Min(0)
    private BigDecimal amountPayment; // сумма платежа

    @Column(name = "percent_payment")
    @Min(0)
    private BigDecimal percentPayment; // сумма гашения процента

    @Column(name = "body_credit_payment")
    @Min(0)
    private BigDecimal bodyCreditPayment; //тело гашения кредита

    @ManyToOne
    @JoinColumn(name = "credit_offer_id")
    private CreditOffer creditOffer;

    @Column (name ="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column (name ="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Payment() {
    }

    public Payment(PaymentDto paymentDto) {
        this.localdate = paymentDto.getDate();
        this.amountPayment = paymentDto.getAmountPayment();
        this.percentPayment = paymentDto.getPercentPayment();
        this.bodyCreditPayment =paymentDto.getBodyCreditPayment();
    }

    public Long getId(){
        return this.id;
    };

    public void setDate(LocalDate date) {
        this.localdate = date;
    }

    public void setPercentPayment(BigDecimal percentPayment) {
        this.percentPayment = percentPayment;
    }

    public void setBodyCreditPayment(BigDecimal bodyCreditPayment) {
        this.bodyCreditPayment = bodyCreditPayment;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    public BigDecimal getAmountPayment(){
        amountPayment=percentPayment.add(bodyCreditPayment);
        return amountPayment;
    }

    public LocalDate getLocalDate() {
        return this.localdate;
    }

    public BigDecimal getPercentPayment() {
        return percentPayment;
    }

    public BigDecimal getBodyCreditPayment() {
        return bodyCreditPayment;
    }


}
