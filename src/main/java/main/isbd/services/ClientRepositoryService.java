package main.isbd.services;

import main.isbd.data.order.MessageInterface;
import main.isbd.data.order.OrderInterface;
import main.isbd.data.product.ProductInOrderInfoInterface;
import main.isbd.data.product.ProductInfoInterface;
import main.isbd.data.product.ProductShortInfoInterface;
import main.isbd.data.users.AdminContactsInterface;
import main.isbd.data.users.Client;
import main.isbd.data.users.ClientProfileInterface;
import main.isbd.repositories.ClientRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@ApplicationScope
public class ClientRepositoryService implements CheckRightsInterface {
    private final ClientRepository clientRepository;

    public ClientRepositoryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<String> getAllRegisteredOrganizations() {
        return clientRepository.getAllRegisteredOrganizations();
    }

    public List<String> getAllNotRegisteredOrganizations() {
        return clientRepository.getAllNotRegisteredOrganizations();
    }

    public Client registerClient(String phone_number, String email, String password, String name) {
        return clientRepository.registerClient(phone_number, email, password, name);
    }

    public Boolean checkIfUserIsAuthorized(Integer id, String password) {
        return clientRepository.checkIfClientIsAuthorised(id, password);
    }

    public Client getClientByNameAndPassword(String name, String password) {
        return clientRepository.getClientByNameAndPassword(name, password);
    }

    public ClientProfileInterface getClientProfileById(Integer client_id) {
        return clientRepository.getClientProfileById(client_id);
    }

    public void setClientProfileById(Integer client_id, String phone_number, String email) {
        clientRepository.setClientProfileById(client_id, phone_number, email);
    }

    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return clientRepository.getAllProductsShortInfo();
    }

    public OrderInterface getCurrentOrderInfoByClientId(Integer client_id) {
        return clientRepository.getCurrentOrderInfoByClientId(client_id);
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return clientRepository.getProductInfoById(product_id);
    }

    public void addProductToOrder(Integer order_id, Integer product_id) {
        clientRepository.addProductToOrder(order_id, product_id);
    }

    public void removeProductFromOrder(Integer order_id, Integer product_id) {
        clientRepository.removeProductFromOrder(order_id, product_id);
    }

    public List<OrderInterface> getAllOrdersInfoByClientId(Integer client_id) {
        return clientRepository.getAllOrdersInfoByClientId(client_id);
    }

    public OrderInterface getOrderInfoByOrderId(Integer order_id) {
        return clientRepository.getOrderInfoByOrderId(order_id);
    }

    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id) {
        return clientRepository.getAllProductsInOrder(order_id);
    }

    public void setProductCountInOrder(Integer order_id, Integer product_id, Integer count) {
        clientRepository.setProductCountInOrder(order_id, product_id, count);
    }

    public void acceptOrder(Integer order_id) {
        clientRepository.acceptOrder(order_id);
    }

    public void payForOrder(Integer order_id) {
        clientRepository.payForOrder(order_id);
    }

    public void cancelOrder(Integer order_id, Timestamp done_at) {
        clientRepository.cancelOrder(order_id, done_at);
    }

    public AdminContactsInterface getAdminContactsInChat(Integer order_id) {
        return clientRepository.getAdminContactsInChat(order_id);
    }

    public ArrayList<MessageInterface> getMessagesInChat(Integer order_id) {
        return new ArrayList<>(clientRepository.getMessagesInChat(order_id));
    }

    public void postMessageInChat(Integer order_id, String content, Timestamp datetime) {
        clientRepository.postMessageInChat(order_id, content, datetime);
    }
}
