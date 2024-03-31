package main.isbd.services;

import main.isbd.data.Client;
import main.isbd.data.ClientRegister;
import main.isbd.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;

@Service
@Transactional
@ApplicationScope
public class ClientRepositoryService {
    private final ClientRepository clientRepository;

    public ClientRepositoryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ArrayList<String> getAllOrganizations() {
        return new ArrayList<>(clientRepository.getAllOrganizations());
    }

    public Integer registerClient(ClientRegister client) {
        return clientRepository.registerClient(client.getPhoneNumber(), client.getEmail(), client.getPassword(), client.getName());
    }

    public Boolean clientIsAuthorized(String name, String password) {
        return clientRepository.clientIsAuthorised(name, password);
    }

    public Client getClientByNameAndPassword(String name, String password) {
        return clientRepository.getClientByNameAndPassword(name, password);
    }


}
