package com.haulmont.testtask.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private LocalDateTime localdate; //дата платежа
    @Column
    private BigDecimal amountPayment; // сумма платежа
    @Column
    private BigDecimal percentPayment; // сумма гашения процента
    @Column
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

    public Long getId(){
        return this.id;
    };

    public void setDate(LocalDateTime date) {
        this.localdate = date;
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

    public LocalDateTime getLocalDate() {
        return this.localdate;
    }

    public BigDecimal getPercentPayment() {
        return percentPayment;
    }

    public BigDecimal getBodyCreditPayment() {
        return bodyCreditPayment;
    }


}
