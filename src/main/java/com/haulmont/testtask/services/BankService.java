package com.haulmont.testtask.services;

import com.haulmont.testtask.dtos.BankDto;
import com.haulmont.testtask.dtos.ClientDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Credit;
import com.haulmont.testtask.repositories.BankRepository;
import com.haulmont.testtask.repositories.ClientRepository;
import com.haulmont.testtask.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;

    public Optional<Bank> findById(Long id) {
        return bankRepository.findById(id);
    }

    public Optional<Bank> findByTitle(String title) {
        return bankRepository.findByTitle(title);
    }

    public Page<Bank> findPage(Specification<Bank> spec, int page, int pageSize){

        return bankRepository.findAll(spec, PageRequest.of(page - 1, pageSize));

    }

    @Transactional
    public Bank save (Bank bank){
        if (bankRepository.findByTitle(bank.getTitle()).isEmpty()){
            return bankRepository.save(bank);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Bank updateBank(Bank bank){
        if (bankRepository.findById(bank.getId()).isPresent()){
            Bank bankUpdated= bankRepository.findById(bank.getId()).get();
            bankUpdated.getClients();
            return bankRepository.save(bank);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public void deleteById(Long id) {
            if(bankRepository.findById(id).isPresent()) {
                bankRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        }

   @Transactional
    public List<Client> getClientsOfBankById(Long id){
       if(bankRepository.findById(id).isPresent()) {
           Bank bank = bankRepository.findById(id).get();
           return bank.getClients();
       }else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
   }
    @Transactional
    public List<Credit> getCreditsOfBankById(Long id){
        if(bankRepository.findById(id).isPresent()) {
            Bank bank = bankRepository.findById(id).get();
            return bank.getCredits();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteClient(Long bankId, Long clientId) {
        Client client = clientRepository.findById(clientId).get();
        List<Bank> banks = client.getBanksList();
        Bank bank = bankRepository.findById(bankId).get();
        banks.remove(bank);
    }

    @Transactional
    public void deleteCredit(Long creditId) {
        creditRepository.deleteById(creditId);
    }
}