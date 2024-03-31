package main.isbd.controllers;

import main.isbd.data.Client;
import main.isbd.data.ClientLogin;
import main.isbd.data.ClientRegister;
import main.isbd.services.ClientRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
public class ClientController {
    @Autowired
    private ClientRepositoryService clientRepositoryService;

    public boolean clientIsAuthorized(String name, String password) {
        return clientRepositoryService.clientIsAuthorized(name, password);
    }

    @GetMapping("/client/get_organizations")
    public @ResponseBody ResponseEntity<?> getOrganizations() {
        System.out.println("Запрос опций названий организаций.");
        return new ResponseEntity<>(clientRepositoryService.getAllOrganizations(), HttpStatus.OK);
    }

    @PostMapping("/client/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody ClientLogin client) {
        if (client == null || !client.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("Клиент (%s) пытается зайти\n", client.getName());
        Client db_client;

        try {
            db_client = clientRepositoryService.getClientByNameAndPassword(client.getName(), client.getPassword());
            if (db_client == null) {
                throw new RuntimeException(String.format("А нет такого клиента (%s)\n", client.getName()));
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        if (!db_client.getPassword().equals(client.getPassword())) {
            System.out.print("Неверный пароль\n");

            return new ResponseEntity<>("Неверный пароль\n", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PostMapping("/client/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody ClientRegister client) {
        if (client == null || !client.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        try {
            Client db_client = clientRepositoryService.getClientByNameAndPassword(client.getName(), client.getPassword());

            if (db_client == null) {
                throw new RuntimeException("Успех!");
            }

            System.out.printf("Такой клиент (%s) уже есть\n", client.getName());
            return new ResponseEntity<>(String.format("Такой клиент (%s) уже есть\n", client.getName()), HttpStatus.CONFLICT);
        } catch (RuntimeException ignored) {
        }

        return new ResponseEntity<>(clientRepositoryService.registerClient(client), HttpStatus.CREATED);
    }
}
