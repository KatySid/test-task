package com.haulmont.testtask.dtos;

import com.haulmont.testtask.models.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
    private Long id;
    private String title;

    public BankDto(Bank bank){
        this.id = bank.getId();
        this.title = bank.getTitle();
    }

}