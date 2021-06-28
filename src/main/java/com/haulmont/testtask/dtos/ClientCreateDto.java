package com.haulmont.testtask.dtos;


import com.haulmont.testtask.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDto {
    private Long bankId;
    private String lastName;
    private String name;
    private String patronymic;
    private String email;
    private String passport;



}