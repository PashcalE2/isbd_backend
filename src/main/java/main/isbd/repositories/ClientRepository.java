package main.isbd.repositories;

import main.isbd.data.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select \"f_g_все_названия_организаций\"()", nativeQuery = true)
    List<String> getAllOrganizations();

    @Query(value = "select \"f_i_Клиент\"(:phone_number, :email, :password, :name)", nativeQuery = true)
    Integer registerClient(String phone_number, String email, String password, String name);

    @Query(value = "select \"f_аутентифицировать\"(:name, :password)", nativeQuery = true)
    Boolean clientIsAuthorised(String name, String password);

    @Query(value = "select \"f_g_Клиент\"(:name, :password)", nativeQuery = true)
    Client getClientByNameAndPassword(String name, String password);
}
