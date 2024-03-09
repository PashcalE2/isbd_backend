package main.isbd.controllers;

import main.isbd.data.AreaPoint;
import main.isbd.data.Checker;
import main.isbd.services.AreaPointRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@ApplicationScope
public class AreaPointController {
    @Autowired
    private UserController userController;
    @Autowired
    private AreaPointRepositoryService areaPointRepositoryService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @CrossOrigin
    @RequestMapping(value = "/points", method = RequestMethod.DELETE)
    void clearPoints(@RequestParam String login) {
        if (!userController.dbHasUserLogin(login)) {
            throw new RuntimeException(String.format("Пользователь (%s) не существует в базе данных", login));
        }

        System.out.printf("Пользователь (%s) получил все свои точки\n", login);
        areaPointRepositoryService.removeAreaPointsByUserLogin(login);
    }


    @CrossOrigin
    @PostMapping("/points")
    public AreaPoint newPoint(@RequestBody AreaPoint point, @RequestParam String login) {
        if (!userController.dbHasUserLogin(login)) {
            throw new RuntimeException(String.format("Пользователь (%s) не существует в базе данных", login));
        }

        if (point.getR() <= 0 || point.getR() > 4) {
            throw new RuntimeException(String.format("Пользователь (%s) скинул плохой радиус (%f) >:(\n", login, point.getR()));
        }

        point.setHit(Checker.isInArea(point));
        point.setUserLogin(login);
        point.setDateTime(LocalDateTime.now().format(formatter));
        System.out.printf("Пользователь (%s) поставил точку\n", login);
        return areaPointRepositoryService.save(point);
    }

    @CrossOrigin
    @GetMapping("/points/{r}")
    public Collection<AreaPoint> allPointsRecalculated(@PathVariable Double r, @RequestParam String login) {
        if (!userController.dbHasUserLogin(login)) {
            throw new RuntimeException(String.format("Пользователь (%s) не существует в базе данных", login));
        }

        if (r <= 0) {
            throw new RuntimeException(String.format("Пользователь (%s) скинул плохой радиус (%f) >:(\n", login, r));
        }

        List<AreaPoint> recalculated = new ArrayList<>();
        Collection<AreaPoint> points = areaPointRepositoryService.getAreaPointsByUserLogin(login);

        for (AreaPoint p : points) {
            AreaPoint point = new AreaPoint();
            point.setId(p.getId());
            point.setX(p.getX());
            point.setY(p.getY());
            point.setR(r);
            point.setHit(Checker.isInArea(point));
            point.setDateTime(p.getDateTime());

            recalculated.add(point);
        }

        System.out.printf("Пользователю (%s) отправились пересчитанные точки\n", login);
        return recalculated;
    }
}
