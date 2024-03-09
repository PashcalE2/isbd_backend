package main.isbd.services;

import main.isbd.data.Users;
import main.isbd.repositories.UsersRepository;
import main.isbd.utils.SHA1;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class UsersRepositoryService {
    private final UsersRepository usersRepository;

    public UsersRepositoryService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users save(Users user) {
        user.setPassword(SHA1.generate(user.getPassword()));
        System.out.printf("BBBBBB %s BBBBBBBBB %s", user.getLogin(), user.getPassword());
        user = usersRepository.save(user);
        user.setPassword("самый лучший пароль на свете, не сомневайся");

        return user;
    }

    public Users findByUserLogin(String login) {
        return usersRepository.getUserByLogin(login);
    }
}
