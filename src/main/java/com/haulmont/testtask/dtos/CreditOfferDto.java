package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.utils.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CreditOfferDto {
    private Long id;
    private BigDecimal amount;
    private Integer duration;
    private Client client;
    private Credit credit;
    private List<Payment> schedule;

    public CreditOfferDto(){
        this.schedule = new ArrayList<>();

    }

    public List<Payment> shapeSchedule() {
        LocalDate date = LocalDate.now();
        return schedule;
    }
}
