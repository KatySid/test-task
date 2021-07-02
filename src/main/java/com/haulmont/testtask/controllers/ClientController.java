package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.ClientCreateDto;
import com.haulmont.testtask.dtos.ClientDto;
import com.haulmont.testtask.dtos.CreditOfferDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.repositories.ClientSpecifications;
import com.haulmont.testtask.services.BankService;
import com.haulmont.testtask.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

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

    @GetMapping("/credit_offers/{id}")
    public Page<CreditOfferDto> findPageOffers(@PathVariable Long id,
                                               @RequestParam (name = "p", defaultValue = "1") int page,
                                               @RequestParam (name = "pageSize", defaultValue = "10") int pageSize){
        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
        List<CreditOffer> creditOffers= clientService.findPageOffers(id);
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),creditOffers.size());
        Page<CreditOfferDto> creditOfferDtos = new PageImpl<CreditOfferDto>(
                creditOffers.stream().map(CreditOfferDto::new).collect(Collectors.toList()).subList(start, end),
                pageRequest,
                creditOffers.size());
        return creditOfferDtos;
    }
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
