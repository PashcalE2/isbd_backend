package main.isbd.repositories;

import main.isbd.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.login = ?1")
    Users getUserByLogin(String login);
}
