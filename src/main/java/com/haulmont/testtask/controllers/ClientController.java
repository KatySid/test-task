package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.ClientCreateDto;
import com.haulmont.testtask.dtos.ClientDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.repositories.ClientSpecifications;
import com.haulmont.testtask.services.BankService;
import com.haulmont.testtask.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final BankService bankService;

    @GetMapping
    public Page<ClientDto> getAllClients(@RequestParam MultiValueMap<String, String> params,
                                         @RequestParam (name = "p", defaultValue = "1") int page,
                                         @RequestParam (name = "pageSize", defaultValue = "10") int pageSize) {
        if (page < 1) {
            page = 1;
        }
        return clientService.findPage(ClientSpecifications.build(params), page, pageSize).map(ClientDto::new);
    }


    @GetMapping ("/{id}")
    public ClientDto getClientById(@PathVariable Long id) {

        return new ClientDto(clientService.findById(id).get());
    }

    @PostMapping
    public ClientDto createNewClientOfBank(@RequestBody ClientCreateDto clientDto) {
        Client client = new Client(clientDto.getLastName(), clientDto.getName(), clientDto.getPatronymic(), clientDto.getEmail(), clientDto.getPassport());
        if (clientDto.getBankId() != null) {
            Optional<Bank> optionalBank = bankService.findById(clientDto.getBankId());

            if (optionalBank.isPresent()) {
                client.addToBankList(optionalBank.get());
            }
        }

        return new ClientDto(clientService.save(client));
    }

//    @PostMapping
//    public ClientDto createNewClient(@RequestBody ClientCreateDto clientCreateDto) {
//        Client client = new Client(clientCreateDto.getLastName(),clientCreateDto.getName(), clientCreateDto.getPatronymic(), clientCreateDto.getEmail(), clientCreateDto.getPassport());
//        return new ClientDto(clientService.save(client));
//    }

//    @PostMapping
//    public ClientDto createClient( @RequestParam (name = "bankId", defaultValue = "null") Long bankId,
//                               @RequestParam String lastName,
//                               @RequestParam String name,
//                               @RequestParam String patronymic,
//                               @RequestParam String passport,
//                               @RequestParam String email) {
//        Client client = new Client(lastName,name, patronymic, email, passport);
//        if(bankService.findById(bankId).isPresent()){
//            client.addToBankList(bankService.findById(bankId).get());
//        }
//        return new ClientDto(clientService.save(client));
//    }

    @PutMapping ("/{id}")
    public ClientDto updateClient(@RequestBody ClientDto clientDto, @PathVariable Long id) {
        clientDto.setId(id);
        return new ClientDto(clientService.updateClient(clientDto));
    }

    @DeleteMapping
    public Client deleteClientById(@RequestParam Long id) {
        return clientService.deleteById(id);
    }

}
