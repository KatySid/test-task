package com.haulmont.testtask.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "credit_offers")
@AllArgsConstructor
@Data
public class CreditOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "amount")
    @Min(0)
    private BigDecimal amount;

    @Column (name = "sumPercent")
    @Min(0)
    private BigDecimal sumPercent;

    @Column (name = "duration")
    @Min(0)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToMany(mappedBy = "creditOffer")
    private List<Payment> paymentSchedule;

    @Column (name ="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column (name ="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CreditOffer() {
        this.paymentSchedule = new ArrayList<>();
    }
}
