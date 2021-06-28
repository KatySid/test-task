package com.haulmont.testtask.dtos;


import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientShortDto {
    private Long id;
    private String lastName;
    private String name;
    private String patronymic;
    private String email;
    private String passport;


public ClientShortDto(Client client) {
    this.id = client.getId();
    this.lastName = client.getLastName();
    this.name = client.getName();
    this.patronymic = client.getPatronymic();
    this.email = client.getEmail();
    this.passport = client.getPassport();

}
}