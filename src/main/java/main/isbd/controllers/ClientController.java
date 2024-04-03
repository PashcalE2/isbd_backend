package main.isbd.controllers;

import main.isbd.data.users.Client;
import main.isbd.data.users.ClientLogin;
import main.isbd.data.users.ClientRegister;
import main.isbd.services.ClientRepositoryService;
import main.isbd.utils.CheckRightsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
@CrossOrigin
@ApplicationScope
public class ClientController {
    @Autowired
    private ClientRepositoryService clientRepositoryService;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @GetMapping("/client/profile/get_all_registered_organizations")
    public @ResponseBody ResponseEntity<?> getAllRegisteredOrganizations() {
        System.out.println("Запрос опций названий зарегистрированных организаций");
        try {
            return new ResponseEntity<>(clientRepositoryService.getAllRegisteredOrganizations(), HttpStatus.OK);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/client/profile/get_all_not_registered_organizations")
    public @ResponseBody ResponseEntity<?> getAllNotRegisteredOrganizations() {
        System.out.println("Запрос опций названий незарегистрированных организаций");
        try {
            return new ResponseEntity<>(clientRepositoryService.getAllNotRegisteredOrganizations(), HttpStatus.OK);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/client/profile/check_rights")
    public @ResponseBody ResponseEntity<?> checkClientRights(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос проверки прав доступа для клиента (%d)\n", client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/profile/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody ClientLogin client) {
        if (client == null || !client.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("Клиент (%s) пытается зайти\n", client.getName());
        Client db_client;

        try {
            db_client = clientRepositoryService.getClientByNameAndPassword(client.getName(), client.getPassword());
            if (db_client == null) {
                throw new RuntimeException("Неверный логин или пароль\n");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(db_client, HttpStatus.OK);
    }

    @PostMapping("/client/profile/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody ClientRegister client) {
        if (client == null || !client.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        Client db_client;

        try {
            db_client = clientRepositoryService.registerClient(client.getPhoneNumber(), client.getEmail(), client.getPassword(), client.getName());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(db_client, HttpStatus.CREATED);
    }

    @GetMapping("/client/profile/get")
    public @ResponseBody ResponseEntity<?> getProfile(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос на получение данных профиля клиента (%d)\n", client_id);

        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getClientProfileById(client_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/profile/set")
    public @ResponseBody ResponseEntity<?> setProfile(
            @RequestParam(defaultValue = "1") Integer client_id,
            @RequestParam String password,
            @RequestParam String phone_number,
            @RequestParam String email
    ) {
        System.out.printf("Запрос на изменение данных профиля клиента (%d)\n", client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.setClientProfileById(client_id, phone_number, email);
                return new ResponseEntity<>(clientRepositoryService.getClientProfileById(client_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/product/get_all_short")
    public @ResponseBody ResponseEntity<?> getProductsShortInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) {
        System.out.println("Запрос на получение данных о всей продукции\n");

        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getAllProductsShortInfo(), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/product/get")
    public @ResponseBody ResponseEntity<?> getProductInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) {
        System.out.printf("Запрос на получение данных о продукции (%d)\n", product_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getProductInfoById(product_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/product/add_to_order")
    public @ResponseBody ResponseEntity<?> addProductToOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam(defaultValue = "0") Integer product_id
    ) {
        System.out.printf("Запрос на добавление продукции (%d) в заказ (%d)\n", product_id, order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.addProductToOrder(order_id, product_id);
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/product/remove_from_order")
    public @ResponseBody ResponseEntity<?> removeProductFromOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam(defaultValue = "0") Integer product_id
    ) {
        System.out.printf("Запрос на удаление продукции (%d) из заказа (%d)\n", product_id, order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.removeProductFromOrder(order_id, product_id);
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/order/get_all_info")
    public @ResponseBody ResponseEntity<?> getAllOrdersInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос на получение информации о заказах клиента (%d)\n", client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getAllOrdersInfoByClientId(client_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/order/get")
    public @ResponseBody ResponseEntity<?> getOrderInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о заказе (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getOrderInfoByOrderId(order_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/order/get_current")
    public @ResponseBody ResponseEntity<?> getCurrentOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос на получение информации о формирующемся заказе клиента (%d)\n", client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getCurrentOrderInfoByClientId(client_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/order/get_products")
    public @ResponseBody ResponseEntity<?> getAllProductsInOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о заказе (%d) клиента (%d)\n", order_id, client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getAllProductsInOrder(order_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/order/set_product_count")
    public @ResponseBody ResponseEntity<?> setProductCountInOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam(defaultValue = "0") Integer product_id,
            @RequestParam(defaultValue = "0") Integer count
    ) {
        System.out.printf("Запрос на изменение количества продукции (%d) в заказе (%d) клиента (%d)\n", count, order_id, client_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.setProductCountInOrder(order_id, product_id, count);
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/order/accept")
    public @ResponseBody ResponseEntity<?> acceptOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на принятие заказа (%d) в обработку\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.acceptOrder(order_id);
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/order/pay")
    public @ResponseBody ResponseEntity<?> payForOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на оплату заказа (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.payForOrder(order_id);
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/order/cancel")
    public @ResponseBody ResponseEntity<?> cancelOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на отмену заказа (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.cancelOrder(order_id, new Timestamp(System.currentTimeMillis()));
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/chat/get_admin")
    public @ResponseBody ResponseEntity<?> getChatAdminInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о консультанте заказа (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getAdminContactsInChat(order_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @GetMapping("/client/chat/get_messages")
    public @ResponseBody ResponseEntity<?> getChatMessages(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение сообщений заказа (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(clientRepositoryService.getMessagesInChat(order_id), HttpStatus.OK);
            }
        }.execute(client_id, password);
    }

    @PostMapping("/client/chat/post_message")
    public @ResponseBody ResponseEntity<?> postChatMessage(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam String content
    ) {
        System.out.printf("Запрос на отправку сообщения в заказе (%d)\n", order_id);
        return new CheckRightsWrapper(clientRepositoryService) {
            @Override
            public ResponseEntity<?> outer() {
                clientRepositoryService.postMessageInChat(order_id, content, new Timestamp(System.currentTimeMillis()));
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(client_id, password);
    }
}
