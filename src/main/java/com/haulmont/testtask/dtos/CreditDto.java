package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.Credit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreditDto {
    private Long bankId;
    private Long id;
    private BigDecimal limitation;
    private BigDecimal percent;

public CreditDto(Credit credit){
    this.id = credit.getId();
    this.limitation = credit.getLimitation();
    this.percent = credit.getPercent();
}

public void clear(){
    this.bankId = null;
    this.id = null;
    this.limitation = null;
    this.percent = null;
}
}