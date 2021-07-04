package com.haulmont.testtask.services;

import com.haulmont.testtask.dtos.ClientDto;
import com.haulmont.testtask.models.Bank;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.CreditOffer;
import com.haulmont.testtask.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientService  {
    private final ClientRepository clientRepository;

    @Transactional
    public Optional<Client> findById(Long id) {
        Optional<Client> client= clientRepository.findById(id);
        List<Bank> bankList = client.get().getBankList();
        return client;
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Page<Client> findPage(Specification<Client> spec, int page, int pageSize){

        return clientRepository.findAll(spec, PageRequest.of(page - 1, pageSize));

    }

    @Transactional
    public Client save (Client client){
        if (clientRepository.findByEmail(client.getPassport()).isEmpty()){
            return clientRepository.save(client);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public Client updateClient(ClientDto clientDto){
        if (clientRepository.findById(clientDto.getId()).isPresent()&&clientRepository.findByPassport(clientDto.getPassport()).isEmpty()){
            Client client = clientRepository.findById(clientDto.getId()).get();
            client.setLastName(clientDto.getLastName());
            client.setName(clientDto.getName());
            client.setPatronymic(clientDto.getPatronymic());
            client.setEmail(clientDto.getEmail());
            client.setEmail(clientDto.getPhone());
            client.setPassport(clientDto.getPassport());
            client.getBankList();
            return clientRepository.save(client);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public void deleteById(Long id) {
            if(clientRepository.findById(id).isPresent()) {
                Client client = clientRepository.findById(id).get();
                List<Bank> banks = client.getBankList();
                banks.removeAll(banks);
                clientRepository.save(client);
                clientRepository.delete(client);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        }

    public List<CreditOffer> findPageOffers(Long id) {
        Client client = clientRepository.findById(id).get();
        List<CreditOffer> creditOffers = client.getCreditOffers();
        return creditOffers;
    }
 }