package com.haulmont.testtask.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "limitation")
    @Min(0)
    private BigDecimal limitation;

    @Column(name = "percent")
    @Min(0)
    @Max(100)
    private BigDecimal percent;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "credit")
    private List<CreditOffer> creditOffers;

    @Column (name ="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column (name ="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Override
    public String toString() {
        return String.format("Credit [id = %d, limitation = %s, percent = %d]", id, limitation, percent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return id.equals(credit.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Credit(BigDecimal limitation, @Min(0) @Max(100) BigDecimal percent) {
        this.limitation = limitation;
        this.percent = percent;
    }

    public Credit(Long id, BigDecimal limitation, @Min(0) @Max(100) BigDecimal percent) {
        this.id = id;
        this.limitation = limitation;
        this.percent = percent;
    }
}