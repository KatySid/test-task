package com.haulmont.testtask.controllers;

import com.haulmont.testtask.dtos.BankDto;
import com.haulmont.testtask.dtos.ClientDto;
import com.haulmont.testtask.dtos.ClientShortDto;
import com.haulmont.testtask.dtos.CreditDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.repositories.BankSpecifications;
import com.haulmont.testtask.repositories.ClientRepository;
import com.haulmont.testtask.repositories.ClientSpecifications;
import com.haulmont.testtask.services.BankService;
import com.haulmont.testtask.services.ClientService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    private final ClientService clientService;

    @GetMapping
    public Page<BankDto> getAllBanks(@RequestParam MultiValueMap<String, String> params, @RequestParam (name = "p", defaultValue = "1") int page) {
        if (page < 1) {
            page = 1;
        }
        return bankService.findPage(BankSpecifications.build(params), page, 10).map(BankDto::new);
    }


    @GetMapping ("/{id}")
    public BankDto getBankById(@PathVariable Long id) {
        return new BankDto(bankService.findById(id).get());
    }

    @GetMapping ("/clientsOfBank/{bankId}")
    public Page<ClientShortDto> getClientsOfBankById(@PathVariable Long bankId,
                                                     @RequestParam MultiValueMap<String, String> params,
                                                     @RequestParam (name = "p", defaultValue = "1") int page,
                                                     @RequestParam (name = "pageSize", defaultValue = "5") int pageSize) {
        if (page < 1) {
            page = 1;
        }

        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
        List<Client> clients= bankService.getClientsOfBankById(bankId);
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),clients.size());
        Page<ClientShortDto> clientsShortDtoPage = new PageImpl<ClientShortDto>(
                                            clients.stream().map(ClientShortDto::new).collect(Collectors.toList()).subList(start, end),
                                            pageRequest,
                                            clients.size());

        return clientsShortDtoPage;
    }

    @GetMapping ("/creditsOfBank/{id}")
    public Page<CreditDto> getCreditsOfBankById(@PathVariable Long id, @RequestParam MultiValueMap<String, String> params, @RequestParam (name = "p", defaultValue = "1") int page, @RequestParam (name = "pageSize", defaultValue = "5") int pageSize) {
        if (page < 1) {
            page = 1;
        }
        PageRequest pageRequest=PageRequest.of(page-1, pageSize);
        List<Credit> credits= bankService.getCreditsOfBankById(id);
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min(start+pageRequest.getPageSize(),credits.size());
        Page<CreditDto> creditsDtoPage = new PageImpl<CreditDto>(
                credits.stream().map(CreditDto::new).collect(Collectors.toList()).subList(start, end),
                pageRequest,
                credits.size());

        return creditsDtoPage;
    }

    @PostMapping
    public BankDto createNewBank(@RequestBody BankDto bankDto) {
        Bank bank = new Bank(bankDto.getTitle());
        return new BankDto(bankService.save(bank));

    }

    @PutMapping
    public BankDto updateBank(@RequestBody BankDto bankDto) {
        Bank bank = new Bank(bankDto);
        return new BankDto(bankService.updateBank(bank));
    }

    @DeleteMapping
    public void deleteBankById(@RequestParam Long id) {
        bankService.deleteById(id);
    }

    @GetMapping ("/deleteClient")
    public void deleteClient(@RequestParam Long bankId, @RequestParam Long clientId) {
        bankService.deleteClient(bankId, clientId);
    }

}
