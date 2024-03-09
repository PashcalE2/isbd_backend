package main.isbd.controllers;

import main.isbd.data.Users;
import main.isbd.services.UsersRepositoryService;
import main.isbd.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@ApplicationScope
public class UserController {
    @Autowired
    private UsersRepositoryService usersRepositoryService;

    public boolean dbHasUserLogin(String login) {
        return usersRepositoryService.findByUserLogin(login) != null;
    }

    @CrossOrigin
    @RequestMapping(value = "/test")
    public @ResponseBody ResponseEntity<?> test(@RequestBody Users user) {
        System.out.printf("Amogus\n");
        return new ResponseEntity<>(String.format("Login = %s, password = %s\n", user.getLogin(), user.getPassword()), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody Users user) {
        if (user == null || !user.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("Юзверь (%s) пытается зайти\n", user.getLogin());
        Users db_user;

        try {
            db_user = usersRepositoryService.findByUserLogin(user.getLogin());
            if (db_user == null) {
                throw new RuntimeException(String.format("А нет такого пользователя (%s)\n", user.getLogin()));
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        if (!db_user.getPassword().equals(SHA1.generate(user.getPassword()))) {
            System.out.printf("Неверный пароль\n");

            return new ResponseEntity<>("Неверный пароль\n", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping(value = "/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody Users user) {
        if (user == null || !user.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        try {
            Users db_user = usersRepositoryService.findByUserLogin(user.getLogin());

            if (db_user == null) {
                throw new RuntimeException("Успех!");
            }

            System.out.printf("Такой юзверь (%s) уже есть\n", user.getLogin());
            return new ResponseEntity<>(String.format("Такой юзверь (%s) уже есть\n", user.getLogin()), HttpStatus.CONFLICT);
        } catch (RuntimeException ignored) {
        }

        return new ResponseEntity<>(usersRepositoryService.save(user), HttpStatus.CREATED);
    }

}
