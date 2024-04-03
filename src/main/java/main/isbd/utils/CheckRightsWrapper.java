package main.isbd.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class CheckRightsWrapper {
    private CheckRightsInterface repositoryService;

    public CheckRightsWrapper(CheckRightsInterface repositoryService) {
        this.repositoryService = repositoryService;
    }

    public abstract ResponseEntity<?> outer();

    public ResponseEntity<?> execute(Integer id, String password) {
        try {
            Boolean client_has_rights = repositoryService.checkIfUserIsAuthorized(id, password);

            if (client_has_rights == null) {
                throw new RuntimeException(String.format("Неверный логин (%s)\n", id));
            }
            else if (!client_has_rights) {
                throw new RuntimeException(String.format("Неверный пароль (%s)\n", password));
            }

            return outer();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
