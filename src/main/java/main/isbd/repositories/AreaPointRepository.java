package main.isbd.repositories;

import main.isbd.data.AreaPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AreaPointRepository extends JpaRepository<AreaPoint, Long> {
    @Query("SELECT p FROM AreaPoint p WHERE p.userLogin = ?1")
    Collection<AreaPoint> getAreaPointsByUserLogin(String login);

    @Transactional
    @Modifying
    @Query("DELETE FROM AreaPoint p WHERE p.userLogin = ?1")
    void removeAreaPointsByUserLogin(String login);
}
